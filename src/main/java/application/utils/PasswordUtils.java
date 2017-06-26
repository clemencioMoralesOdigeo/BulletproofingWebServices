package application.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by Clemencio Morales Lucas.
 */
public class PasswordUtils {

    private static final Random RANDOM = new SecureRandom();
    private static final String SHA_512 = "SHA-512";
    private static final String UTF_8 = "UTF-8";

    public static String getSalt() {
        return getNextSalt().toString();
    }

    public static String getEncryptedPassword(final String cleanPassword, final String salt) throws NoSuchAlgorithmException,
            UnsupportedEncodingException {
        String generatedPassword;
        MessageDigest messageDigest = MessageDigest.getInstance(SHA_512);
        messageDigest.update(salt.getBytes(UTF_8));
        byte[] bytes = messageDigest.digest(cleanPassword.getBytes(UTF_8));
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++){
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        generatedPassword = sb.toString();
        return generatedPassword;
    }

    private static byte[] getNextSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return salt;
    }
}
