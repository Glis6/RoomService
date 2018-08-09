package com.glis.security;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author Glis
 */
public interface EncryptionStandard {
    /**
     * @return The generated {@link KeyPair}.
     */
    KeyPair generateKeyPair();

    /**
     * @param privateKey The private key used.
     * @param message The message that is being decrypted.
     * @return The decrypted message, encoded in UTF-8.
     */
    String decrypt(PrivateKey privateKey, byte[] message) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException;

    /**
     * @param publicKey The public key to use.
     * @param message The message that is being encrypted.
     * @return The encrypted message, encoded in UTF-8.
     */
    byte[] encrypt(PublicKey publicKey, String message) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException;
}
