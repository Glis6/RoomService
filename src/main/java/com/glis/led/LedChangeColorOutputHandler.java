package com.glis.led;

import com.glis.domain.DomainController;
import com.glis.io.network.output.MessageSender;
import com.glis.io.network.output.handlers.OutputHandler;
import com.glis.message.LedStripColorChangeMessage;
import io.reactivex.disposables.Disposable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

import static com.glis.led.LedConstants.*;

/**
 * @author Glis
 */
@Component
public class LedChangeColorOutputHandler implements OutputHandler {
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
    public LedChangeColorOutputHandler(DomainController domainController) {
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
                        rgbSettingsOptional.ifPresent(rgbSettings ->
                                messageSender.send(new LedStripColorChangeMessage(rgbSettings.getRed(), rgbSettings.getGreen(), rgbSettings.getBlue()))));
        messageSender.onClose(disposable::dispose);
        logger.info("Current led settings linked.");
    }
}
