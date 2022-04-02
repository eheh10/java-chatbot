package com.system;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class SystemErrorTest {
    private static final ByteArrayOutputStream OUTPUT_STREAM = new ByteArrayOutputStream();
    private static final PrintStream PRINT_STREAM = System.out;

    @BeforeEach
    public void setStream() {
        System.setOut(new PrintStream(OUTPUT_STREAM));
    }

    @AfterEach
    void outStream() {
        System.setOut(PRINT_STREAM);
    }

    @Test
    void testGetSupportCommand(){
        // given
        SystemError systemError = new SystemError();
        List expect = List.of();

        // when
        List actual = systemError.getSupportCommand();

        // then
        Assertions.assertThat(actual).isEqualTo(expect);
    }

    @Test
    void testExecute(){
        // given
        SystemError systemError = new SystemError();
        ArrayList<String> command = new ArrayList<>(Arrays.asList("wrong"));
        String subCommand = "wrong";
        String expect = "error: `"+subCommand+"`는 올바르지 않은 메타데이터 입니다.\n" +
                "다음의 메타 데이터중 하나를 선택해주세요.\n"+
                "[e|exit]\n" +
                "[f|file]\n" +
                "[u|update] [b|banner|] [txt:banner]\n" +
                "[u|update] [e|exit] [txt:bye] \n" +
                "[u|update] [f|file] [txt:relativeFilePath]";

        // when
        systemError.isSupport(subCommand);
        systemError.execute(command);
        String actual = OUTPUT_STREAM.toString().trim();

        // then
        Assertions.assertThat(actual).isEqualTo(expect);
    }
}