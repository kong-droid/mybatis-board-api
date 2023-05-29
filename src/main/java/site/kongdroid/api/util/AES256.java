package site.kongdroid.api.util;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

@Component
public class AES256 {

    private static String aesKey;

    @Value("${properties.aesKey}")
    private void setAesKey(String aesKey) {
        AES256.aesKey = aesKey;
    }

    private static Key keySpec;
    private static byte[] iv;

    public void init() {
        byte[] keyBytes = new byte[16];
        int len = 0;
        byte[] b = null;
        b = aesKey.getBytes(StandardCharsets.UTF_8);

        len = b.length;
        if (len > keyBytes.length) {
            len = keyBytes.length;
        }
        System.arraycopy(b, 0, keyBytes, 0, len);
        keySpec = new SecretKeySpec(keyBytes, "AES");
        this.iv = aesKey.substring(0, 16).getBytes();
    }

    public String builder (String str) {
        StringBuilder builder = new StringBuilder();
        builder.append("," + str);
        return builder.toString();
    }

    // encryption
    public String aesEncode(String str) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

        if("".equals(str) ) {
            return str;
        }

        init();
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv));

        byte[] encrypted = c.doFinal(str.getBytes(StandardCharsets.UTF_8));

        return Base64.encodeBase64String(encrypted);
    }

    // decryption
    public String aesDecode(String str) {

        if(str == null || "".equals(str)) {
            return str;
        }
        init();
        String encodedText = str.replace(" ", "+");
        Cipher c;
        String decrypt = "";
        try {
            c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));
            byte[] byteStr = Base64.decodeBase64(encodedText);
            decrypt = new String(c.doFinal(byteStr), StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
            throw new ResourceAccessException("encryption or decryption failed.");
        } catch (NoSuchPaddingException e) {
            throw new ResourceAccessException("encryption or decryption failed.");
        } catch (InvalidKeyException e) {
            throw new ResourceAccessException("encryption or decryption failed.");
        } catch (IllegalBlockSizeException e) {
            throw new ResourceAccessException("encryption or decryption failed.");
        } catch (BadPaddingException e) {
            throw new ResourceAccessException("encryption or decryption failed.");
        } catch (InvalidAlgorithmParameterException e) {
            throw new ResourceAccessException("encryption or decryption failed.");
        }

        return decrypt;
    }
}
