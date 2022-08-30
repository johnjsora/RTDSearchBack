package authorityLetter.authorityLetter.security;


import javax.crypto.*;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.stream.Collectors;

public class SecurityUtils {
    private final String SECRET_KEY = "f1j0GH{8DPRd$yfU";

    private Cipher _cipher;
    private IvParameterSpec _IVParamSpec;
    private SecretKey _password;

    public SecurityUtils() {

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");

            _cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            _IVParamSpec = new IvParameterSpec(SECRET_KEY.getBytes());
            _password = new SecretKeySpec(digest.digest(SECRET_KEY.getBytes()), "AES");

        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String text) {

        try {
            _cipher.init(Cipher.ENCRYPT_MODE, _password, _IVParamSpec);
            byte[] encryptedData = _cipher.doFinal(text.getBytes());

            return Base64.getEncoder().encodeToString(encryptedData);

        } catch (InvalidKeyException | InvalidAlgorithmParameterException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();

            return null;
        }
    }

    public String decrypt(String encryptedText) {

        try {
            _cipher.init(Cipher.DECRYPT_MODE, _password, _IVParamSpec);

            byte[] decodedValue = Base64.getDecoder().decode(encryptedText.getBytes());
            byte[] decryptedVal = _cipher.doFinal(decodedValue);

            return new String(decryptedVal);

        } catch (InvalidKeyException | InvalidAlgorithmParameterException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();

            return null;
        }
    }

    public static String getHtmlTemplate(String resource) {

        try {
            StringBuilder htmlTemplateBuilder = new StringBuilder();
            System.out.println(resource);
            String contents ="";
            try (InputStream inputStream = SecurityUtils.class.getClassLoader().getResourceAsStream(resource);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                contents = reader.lines()
                        .collect(Collectors.joining(System.lineSeparator()));
            }
            return  contents;

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
