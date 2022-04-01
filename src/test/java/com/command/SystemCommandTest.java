package com.command;


import com.banner.BootBanner;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

class SystemCommandTest {
    private static BootBanner bootBanner = new MockBootBanner();
    private static final ByteArrayOutputStream OUTPUT_STREAM = new ByteArrayOutputStream();
    private static final PrintStream PRINT_STREAM = System.out;
    public static final String TEST_FILE = "test.txt";
    public static final String TEST_CONTENT = "Hello SystemCommand Test";

    @BeforeEach
    public void setStream() {
        System.setOut(new PrintStream(OUTPUT_STREAM));
    }

    @BeforeEach
    void setUpFile() throws IOException {
        File bannerFile = new File(TEST_FILE);
        OutputStream os = new FileOutputStream(bannerFile);
        os.write(TEST_CONTENT.getBytes(StandardCharsets.UTF_8));
        os.close();
    }

    @AfterEach
    void outStream() {
        System.setOut(PRINT_STREAM);
    }

    @AfterEach
    void deleteFile() throws IOException {
        Files.delete(Paths.get(TEST_FILE));
    }

    @Test
    void testIsSupport(){
        // given
        SystemCommand systemCommand = new SystemCommand(bootBanner);
        String command = "/system";
        // when
        boolean actual = systemCommand.isSupport(command);

        // then
        Assertions.assertThat(actual).isEqualTo(true);
    }

    @Test
    void testActionDefaultBanner() throws IOException {
        //given
        SystemCommand systemCommand = new SystemCommand(bootBanner);
        ArrayList<String> command = new ArrayList<>(Arrays.asList("update","hi,banner"));
        String expect = "hi,banner";

        //when
        systemCommand.action(command);

        //then
        String actual = bootBanner.display();

        Assertions.assertThat(actual).isEqualTo(expect);
    }

    @Test
    void testActionWrongMeta() throws IOException {
        //given
        SystemCommand systemCommand = new SystemCommand(bootBanner);
        ArrayList<String> command = new ArrayList<>(Arrays.asList("q"));
        String expect = "error: `q`는 올바르지 않은 메타데이터 입니다.\n"+
                "다음의 메타 데이터중 하나를 선택해주세요.\n" +
                "[e|exit]\n" +
                "[f|file]\n" +
                "[u|update] [b|banner|] [txt:banner]\n" +
                "[u|update] [e|exit] [txt:bye] \n" +
                "[u|update] [f|file] [txt:relativeFilePath]";

        //when
        systemCommand.action(command);

        //then
        String actual = OUTPUT_STREAM.toString().trim();

        Assertions.assertThat(actual).isEqualTo(expect);
    }

    private static class MockBootBanner extends BootBanner{
        @Override
        public String display() throws IOException {
            return "hi,banner";
        }
    }
}