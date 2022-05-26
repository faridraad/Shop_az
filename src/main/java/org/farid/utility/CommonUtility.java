package org.farid.utility;

import org.apache.tomcat.util.codec.binary.Base64;
import org.farid.configuration.resources.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class CommonUtility {
    @Autowired
    ApplicationProperties applicationProperties;
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        final Set<Object> seen = new HashSet<>();
        return t -> seen.add(keyExtractor.apply(t));
    }


    public static String encryptBase64 (String unencryptedString , SecretKey key) throws Exception {
        Cipher encryptCipher = Cipher.getInstance("DES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] unencryptedByteArray = unencryptedString.getBytes("UTF8");
        byte[] encryptedBytes = encryptCipher.doFinal(unencryptedByteArray);
        byte [] encodedBytes = Base64.encodeBase64(encryptedBytes);
        return new String(encodedBytes);
    }

    public static String decryptBase64 (String encryptedString, SecretKey key) throws Exception {
        Cipher decryptCipher = Cipher.getInstance("DES");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
        byte [] decodedBytes = Base64.decodeBase64(encryptedString.getBytes());
        byte[] unencryptedByteArray = decryptCipher.doFinal(decodedBytes);
        return new String(unencryptedByteArray, "UTF8");
    }
}
