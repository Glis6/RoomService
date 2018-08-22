package com.glis.security;

import com.glis.security.encryption.EncryptionStandard;
import com.glis.security.hash.HashingResult;
import com.glis.security.hash.HashingStandard;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author Glis
 */
public final class SecurityController {
    /**
     * The {@link HashingStandard} for this class.
     */
    private final HashingStandard hashingStandard;

    /**
     * The {@link EncryptionStandard} for this class.
     */
    private final EncryptionStandard encryptionStandard;

    /**
     * @param hashingStandard The {@link HashingStandard} for this class.
     * @param encryptionStandard The {@link EncryptionStandard} for this class.
     */
    public SecurityController(HashingStandard hashingStandard, EncryptionStandard encryptionStandard) {
        this.hashingStandard = hashingStandard;
        this.encryptionStandard = encryptionStandard;
    }

    /**
     * @param privateKey The private key used.
     * @param message The message that is being decrypted.
     * @return The decrypted message, encoded in UTF-8.
     */
    public String decrypt(PrivateKey privateKey, byte[] message) throws Exception {
        return encryptionStandard.decrypt(privateKey, message);
    }

    /**
     * @param publicKey The public key to use.
     * @param message The message that is being encrypted.
     * @return The encrypted message, encoded in UTF-8.
     */
    public byte[] encrypt(PublicKey publicKey, String message) throws Exception {
        return encryptionStandard.encrypt(publicKey, message);
    }

    /**
     * @param stringToHash The string to be hashed.
     * @return The {@link HashingResult} containing the salt and hashed string.
     */
    public HashingResult hash(String stringToHash) throws Exception {
        return hashingStandard.hash(stringToHash);
    }

    /**
     * @param stringToHash The string to be hashed.
     * @param salt         The salt to use while hashing.
     * @return The  hashed string.
     */
    public String hash(String stringToHash, String salt) throws Exception {
        return hashingStandard.hash(stringToHash, salt);
    }
}
