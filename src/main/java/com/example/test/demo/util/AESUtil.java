package com.example.test.demo.util;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class AESUtil {

    private static final String ALGORITHM = "AES";
    private static final char[] KEY = "Ma1n_AnA_Dev1A_TeR_VerGonhA69".toCharArray();

    private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int ITERATION_COUNT = 65536;
    private static final int KEY_LENGTH = 256;
    private static final SecureRandom RANDOM = new SecureRandom();
    private final Cipher encryptCipher;
    private final Cipher decryptCipher;

    public AESUtil() {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
            KeySpec spec = new PBEKeySpec(KEY, RANDOM.generateSeed(8), ITERATION_COUNT, KEY_LENGTH);
            SecretKey key = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), ALGORITHM);
            this.encryptCipher = Cipher.getInstance(ALGORITHM);
            this.decryptCipher = Cipher.getInstance(ALGORITHM);
            this.encryptCipher.init(Cipher.ENCRYPT_MODE, key);
            this.decryptCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] encrypt(byte[] data) {
        try {
            return encryptCipher.doFinal(data);
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] decrypt(byte[] encryptedData) {
        try {
            return decryptCipher.doFinal(encryptedData);
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }
    }

}
