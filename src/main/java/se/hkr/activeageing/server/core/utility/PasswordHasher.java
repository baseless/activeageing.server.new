package se.hkr.activeageing.server.core.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Andreas on 2015-12-18.
 */
public class PasswordHasher {

    public static String Hash256(String text) {
        String salt = "1234567890";
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String textByteWithSalt = text + salt;
        byte[] digestText = messageDigest.digest(textByteWithSalt.getBytes());
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < digestText.length; i++) {
            stringBuffer.append(Integer.toString((digestText[i] & 0xff) + 0x100, 16).substring(1));
        }
        String generatePassword = stringBuffer.toString();
        return generatePassword;
    }
}
