package tests;

import models.User;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.io.Reader;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonFileTest {

    private final ClassLoader cl = ZipFilesTests.class.getClassLoader();
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void jsonFileParsingTest() throws Exception {
        try (Reader reader = new InputStreamReader(
                cl.getResourceAsStream("user.json"))) {
            User user = objectMapper.readValue(reader, User.class);

            assertThat(user.getTitle()).isEqualTo("user");
            assertThat(user.getId()).isEqualTo(123);
            assertThat(user.getUserInfo().getLogin()).isEqualTo("Login1");
        }
    }
}