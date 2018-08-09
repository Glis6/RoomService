package com.glis.security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author Glis
 */
public class RsaEncryption implements EncryptionStandard {
    /**
     * The {@link KeyPairGenerator} that's used for this {@link EncryptionStandard};
     */
    private final KeyPairGenerator keyPairGenerator;

    /**
     * The {@link Cipher} we're using to encrypt and decrypt our message.
     */
    private final Cipher cipher;

    /**
     * @throws NoSuchAlgorithmException Thrown when the algorithm cannot be found.
     */
    public RsaEncryption() throws NoSuchAlgorithmException, NoSuchPaddingException {
        keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048, new SecureRandom());
        cipher = Cipher.getInstance("RSA");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public KeyPair generateKeyPair() {
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String decrypt(PrivateKey privateKey, byte[] message) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(message), UTF_8);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] encrypt(PublicKey publicKey, String message) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(message.getBytes(UTF_8));
    }
}
