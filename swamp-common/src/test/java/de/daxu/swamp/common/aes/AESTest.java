package de.daxu.swamp.common.aes;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AESTest {

    private static final String MESSAGE = "this is a message";
    private static final String ENCRYPTED_MESSAGE = "txujzu5u9uVHEYGeVLEawN+DcylihE5Jqt+ecEyG4zM=";

    private AES aes;

    @Before
    public void setup() {
        aes = new AES("test key");
    }

    @Test
    public void encrypt_decrypt() throws Exception {
        String encryptedMessage = aes.encrypt(MESSAGE);
        String decryptedMessage = aes.decrypt(encryptedMessage);

        assertThat(decryptedMessage).isEqualTo(MESSAGE);
    }

    @Test
    public void encrypt_outputIsDifferentThanInput() throws Exception {
        String encryptedMessage = aes.encrypt(MESSAGE);

        assertThat(encryptedMessage).isNotEqualTo(MESSAGE);
    }

    @Test
    public void decrypt_outputIsDifferentThanInput() throws Exception {
        String decryptedMessage = aes.decrypt(ENCRYPTED_MESSAGE);

        assertThat(decryptedMessage).isNotEqualTo(ENCRYPTED_MESSAGE);
    }
}