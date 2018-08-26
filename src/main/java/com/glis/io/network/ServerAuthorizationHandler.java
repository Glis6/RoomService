package com.glis.io.network;

import com.glis.ApplicationContextProvider;
import com.glis.domain.DomainController;
import com.glis.domain.model.ClientIdentity;
import com.glis.exceptions.InvalidTypeException;
import com.glis.io.network.networktype.NetworkType;
import com.glis.io.repository.ClientIdentityRepository;
import com.glis.io.repository.RepositoryManager;
import com.glis.log.ChannelLogController;
import com.glis.message.AuthorizationMessage;
import com.glis.security.SecurityController;
import io.netty.channel.ChannelHandlerContext;
import io.reactivex.disposables.Disposable;

import java.util.Collection;
import java.util.HashSet;
import java.util.logging.Level;

/**
 * @author Glis
 */
public final class ServerAuthorizationHandler extends AuthorizationHandler {
    /**
     * The {@link SecurityController} to encrypt and hash objects.
     */
    private final SecurityController securityController = ApplicationContextProvider.getApplicationContext().getBean(DomainController.class).getSecurityController();

    /**
     * The {@link ClientIdentityRepository} that holds, loads and transfers client identity data.
     */
    private final ClientIdentityRepository clientIdentityRepository = ApplicationContextProvider.getApplicationContext().getBean(RepositoryManager.class).getClientIdentityRepository();

    /**
     * The {@link ChannelLogController} for this instance.
     */
    private final ChannelLogController channelLogController = ApplicationContextProvider.getApplicationContext().getBean(DomainController.class).getChannelLogController();

    /**
     * All disposables to clean up afterwards.
     */
    private final Collection<Disposable> disposables = new HashSet<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        channelLogController.connectionAttempt(ctx.channel().remoteAddress().toString(), ctx.channel().localAddress().toString());
        if (!(msg instanceof AuthorizationMessage)) {
            throw new InvalidTypeException("Got a message of the wrong format. Expected an " + AuthorizationMessage.class.getSimpleName() + " message.");
        }
        final AuthorizationMessage authorizationMessage = (AuthorizationMessage) msg;
        //First we're going to get the data from the repository.
        disposables.add(clientIdentityRepository.get(authorizationMessage.getClientId()).subscribe(clientIdentityOptional -> {
            //if we don't have data then we'll close the connection.
            if(!clientIdentityOptional.isPresent()) {
                ctx.writeAndFlush(AuthorizationResponse.UNKNOWN_CLIENT_ID);
                ctx.close().addListener(future -> channelLogController.authorizationResponse(
                        AuthorizationResponse.UNKNOWN_CLIENT_ID.toString(),
                        authorizationMessage.getClientId(),
                        "Unknown",
                        ctx.channel().remoteAddress().toString(),
                        ctx.channel().localAddress().toString())
                );
                return;
            }
            final ClientIdentity clientIdentity = clientIdentityOptional.get();
            //If we do get data then we'll hash the password to compare.
            final String passwordHash = securityController.hash(authorizationMessage.getClientSecret(), clientIdentity.getSalt());

            //If the passwords are not the same then we'll break the connection again.
            if(!passwordHash.equals(clientIdentity.getClientSecret())) {
                ctx.writeAndFlush(AuthorizationResponse.INVALID_CREDENTIALS);
                ctx.close().addListener(future -> channelLogController.authorizationResponse(
                        AuthorizationResponse.INVALID_CREDENTIALS.toString(),
                        authorizationMessage.getClientId(),
                        passwordHash,
                        ctx.channel().remoteAddress().toString(),
                        ctx.channel().localAddress().toString())
                );
                return;
            }

            //Now we'll check if all data is correct.
            if (!networkTypes.containsKey(clientIdentity.getNetworkType())) {
                ctx.writeAndFlush(AuthorizationResponse.INCOMPLETE_INFORMATION);
                ctx.close().addListener(future -> channelLogController.authorizationResponse(
                        AuthorizationResponse.INCOMPLETE_INFORMATION.toString(),
                        authorizationMessage.getClientId(),
                        passwordHash,
                        ctx.channel().remoteAddress().toString(),
                        ctx.channel().localAddress().toString())
                );
                return;
            }
            logger.info("Found a matching " + NetworkType.class.getSimpleName() + ", linking...");
            //We write that everything was successful.
            ctx.writeAndFlush(AuthorizationResponse.SUCCESS).addListener(future -> channelLogController.authorizationResponse(
                    AuthorizationResponse.SUCCESS.toString(),
                    authorizationMessage.getClientId(),
                    passwordHash,
                    ctx.channel().remoteAddress().toString(),
                    ctx.channel().localAddress().toString())
            );
            ctx.writeAndFlush(ctx.alloc().buffer(1).writeByte(clientIdentity.getNetworkType()));
            networkTypes.get(clientIdentity.getNetworkType()).link(ctx, new ServerLinkData(clientIdentity.getDisplayName()));
            disposables.forEach(Disposable::dispose);
        }));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        disposables.forEach(Disposable::dispose);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        logger.log(Level.WARNING, "Exception attempting to authorize to the server.", cause);
    }
}
