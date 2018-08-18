package com.glis.io.network.input.handlers;

import com.glis.domain.DomainController;

/**
 * @author Glis
 */
public abstract class DomainControllerRequiredInputHandler<T> implements InputHandler<T> {
    /**
     * The {@link DomainController} to use.
     */
    protected final DomainController domainController;

    /**
     * @param domainController The {@link DomainController} to use.
     */
    protected DomainControllerRequiredInputHandler(DomainController domainController) {
        this.domainController = domainController;
    }
}
