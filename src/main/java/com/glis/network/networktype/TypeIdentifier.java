package com.glis.network.networktype;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Glis
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeIdentifier {
    int value();
}
