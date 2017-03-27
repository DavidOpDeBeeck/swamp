package de.daxu.swamp.common.aes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;

import static java.lang.String.format;

@Service
public class AES {

    private static final String CHARSET = "UTF-8";
    private static final String ALGORITHM = "SHA-1";
    private static final String CYPHER = "AES/ECB/PKCS5PADDING";
    private static final String KEY_TYPE = "AES";

    private final Logger logger = LoggerFactory.getLogger(AES.class);
    private final SecretKeySpec secretKeySpec;

    @Autowired
    public AES(@Value("${aes.key}") String aesKey) {
        this.secretKeySpec = createKeySpecification(aesKey);
    }

    private SecretKeySpec createKeySpecification(String aesKey) {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            byte[] keyBytes = aesKey.getBytes(CHARSET);

            keyBytes = digest.digest(keyBytes);
            keyBytes = Arrays.copyOf(keyBytes, 16);

            return new SecretKeySpec(keyBytes, KEY_TYPE);
        } catch (Exception e) {
            logger.error("Failed to create AES key specification for key: {} with exception: {}", aesKey, e.getMessage());
            throw new RuntimeException(format("Failed to create AES key specification for key: %s", aesKey), e);
        }
    }

    public String encrypt(String message) {
        try {
            Cipher cipher = Cipher.getInstance(CYPHER);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            return Base64Utils.encodeToString(cipher.doFinal(message.getBytes(CHARSET)));
        } catch (Exception e) {
            logger.error("Failed to encrypt: {} with exception: {}", message, e.getMessage());
            throw new RuntimeException(format("Failed to encrypt: %s", message), e);
        }
    }

    public String decrypt(String message) {
        try {
            Cipher cipher = Cipher.getInstance(CYPHER);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            return new String(cipher.doFinal(Base64Utils.decodeFromString(message)));
        } catch (Exception e) {
            logger.error("Failed to decrypt: {} with exception: {}", message, e.getMessage());
            throw new RuntimeException(format("Failed to decrypt: %s", message), e);
        }
    }
}
