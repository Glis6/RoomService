package com.glis.security.hash;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;

/**
 * @author Glis
 */
public class Sha512Hashing implements HashingStandard {
    /**
     * The amount of bytes used to generate the salt.
     */
    private final static int SALT_LENGTH = 8;

    /**
     * The {@link SecureRandom} used to generate the salt.
     */
    private final static SecureRandom SECURE_RANDOM = new SecureRandom();

    /**
     * @return A random salt string.
     */
    private String generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        SECURE_RANDOM.nextBytes(salt);
        return new String(salt);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String hash(String stringToHash, String salt) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt.getBytes(StandardCharsets.UTF_8));
        byte[] bytes = md.digest(stringToHash.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HashingResult hash(String stringToHash) throws Exception {
        final String salt = generateSalt();
        return new SimpleHashingResult(hash(stringToHash, salt), salt);
    }
}
