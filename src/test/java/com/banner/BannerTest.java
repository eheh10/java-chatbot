package com.banner;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

class BannerTest {
    public static final String TEST_File = "test.txt";
    public static final String TEST_CONTENT = "Hello Banner Test";

    @BeforeEach
    void setUpFile() throws IOException {
        File bannerFile = new File(TEST_File);
        OutputStream os = new FileOutputStream(bannerFile);
        os.write(TEST_CONTENT.getBytes(StandardCharsets.UTF_8));
        os.close();
    }


    @Test
    void testDisplay() throws IOException {
        // given
        Banner banner = new Banner(TEST_File);

        // when
        String actual = banner.display();

        // then
        Assertions.assertThat(actual)
            .hasSize(TEST_CONTENT.length())
            .isEqualTo(TEST_CONTENT);
    }

    @Test
    void testUpdate() throws IOException{
        // given
        Banner banner = new Banner(TEST_File);
        String expect = "hello content";

        // when
        banner.update(expect);

        // then
        String actual = readTestFile();
        Assertions.assertThat(actual).isEqualTo(expect);
    }

    protected String readTestFile() throws IOException {
        InputStream is = new FileInputStream(TEST_File);
        BufferedInputStream bis = new BufferedInputStream(is,8192);
        InputStreamReader isr = new InputStreamReader(bis,StandardCharsets.UTF_8);
        char[] buffer = new char[100];
        StringBuilder actual = new StringBuilder();
        int len;

        while((len=isr.read(buffer))!=-1){
            actual.append(buffer,0,len);
        }

        isr.close();
        return actual.toString();
    }

    @AfterEach
    void deleteFile() throws IOException {
        Files.delete(Paths.get(TEST_File));
    }
}