package com.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

class InputStreamUtilTest {
    public static final String TEST_File = "test.txt";
    public static final String TEST_CONTENT = "InputStremaUtil Test~!";

    @BeforeEach
    void setUpFile() throws IOException {
        File bannerFile = new File(TEST_File);
        OutputStream os = new FileOutputStream(bannerFile);
        os.write(TEST_CONTENT.getBytes(StandardCharsets.UTF_8));
        os.close();
    }


    @Test
    void testRead() throws IOException {
        // given
        InputStream is = new FileInputStream(TEST_File);

        // when
        String actual = InputStreamUtil.read(is);

        // then
        Assertions.assertThat(actual)
            .hasSize(TEST_CONTENT.length())
            .isEqualTo(TEST_CONTENT);
    }

    @AfterEach
    void deleteFile() throws IOException {
        Files.delete(Paths.get(TEST_File));
    }
}