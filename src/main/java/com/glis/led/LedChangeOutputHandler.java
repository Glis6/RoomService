package com.glis.led;

import com.glis.ApplicationContextProvider;
import com.glis.domain.DomainController;
import com.glis.exceptions.UnknownDecoderException;
import com.glis.io.network.output.MessageSender;
import com.glis.io.network.output.handlers.OutputHandler;
import com.glis.led.codec.DecodeLedChangeOutputToMessage;
import com.glis.message.LedStripColorChangeMessage;
import io.reactivex.disposables.Disposable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.glis.led.LedConstants.LED_COLOR_CHANGE_REQUEST_STRING;

/**
 * @author Glis
 */
@Component
public class LedChangeOutputHandler implements OutputHandler {
    /**
     * The {@link Logger} for this class.
     */
    private final Logger logger = Logger.getLogger(getClass().getSimpleName());

    /**
     * The {@link LedController} to use.
     */
    private final LedController ledController;

    /**
     * @param domainController The {@link DomainController} for this instance.
     */
    @Autowired
    public LedChangeOutputHandler(DomainController domainController) {
        this.ledController = domainController.getLedController();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canHandle(String identifier) {
        return identifier.equals(LED_COLOR_CHANGE_REQUEST_STRING);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(MessageSender messageSender) throws Exception {
        logger.info("Attempting to link current led settings...");
        final Disposable disposable = ledController.getRgbSettingsObservable()
                .subscribe(rgbSettingsOptional ->
                        rgbSettingsOptional.ifPresent(rgbSettings -> {
                            try {
                                if(rgbSettings.isEmpty()) {
                                    messageSender.send(new LedStripColorChangeMessage(0, 0, 0));
                                    return;
                                }
                                final String[] partsWithType = rgbSettings.split(";");
                                if (partsWithType.length <= 0) {
                                    throw new IllegalArgumentException("The rgb settings have to at least specify the type.");
                                }
                                final String type = partsWithType[0];
                                final Optional<DecodeLedChangeOutputToMessage> decoderOptional = ApplicationContextProvider.getApplicationContext()
                                        .getBeansOfType(DecodeLedChangeOutputToMessage.class)
                                        .values()
                                        .stream()
                                        .filter(decodeEffectToMessage -> decodeEffectToMessage.getType().equals(type))
                                        .findFirst();
                                if (!decoderOptional.isPresent()) {
                                    throw new UnknownDecoderException("Could not find a decoder for type '" + type + "'");
                                }
                                messageSender.send(decoderOptional.get().decode(partsWithType));
                            } catch (Exception e) {
                                logger.log(Level.SEVERE, "Could not change led profile. ", e);
                            }
                        }));
        messageSender.onClose(disposable::dispose);
        logger.info("Current led settings linked.");
    }
}
