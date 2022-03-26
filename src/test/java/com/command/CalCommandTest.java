package com.command;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

class CalCommandTest {
    private static final ByteArrayOutputStream OUTPUT_STREAM = new ByteArrayOutputStream();
    private static final PrintStream PRINT_STREAM = System.out;

    @BeforeEach
    public void setStream() {
        System.setOut(new PrintStream(OUTPUT_STREAM));
    }

    @Test
    void testActionPlus(){
        //given
        ArrayList<String> command = new ArrayList<>(Arrays.asList("5","+","4"));
        String expect = "9";

        //when
        CalCommand.action(command);

        //then
        String actual = OUTPUT_STREAM.toString().trim();

        Assertions.assertThat(actual).isEqualTo(expect);

    }

    @Test
    void testActionMinus(){
        //given
        ArrayList<String> command = new ArrayList<>(Arrays.asList("3","-","5"));
        String expect = "-2";

        //when
        CalCommand.action(command);

        //then
        String actual = OUTPUT_STREAM.toString().trim();

        Assertions.assertThat(actual).isEqualTo(expect);

    }

    @Test
    void testActionMultiple(){
        //given
        ArrayList<String> command = new ArrayList<>(Arrays.asList("3","*","2"));
        String expect = "6";

        //when
        CalCommand.action(command);

        //then
        String actual = OUTPUT_STREAM.toString().trim();

        Assertions.assertThat(actual).isEqualTo(expect);
    }

    @Test
    void testActionDivide(){
        //given
        ArrayList<String> command = new ArrayList<>(Arrays.asList("6","/","4"));
        String expect = "1";

        //when
        CalCommand.action(command);

        //then
        String actual = OUTPUT_STREAM.toString().trim();

        Assertions.assertThat(actual).isEqualTo(expect);
    }

    @Test
    void testActionDivideWithZero(){
        //given
        ArrayList<String> command = new ArrayList<>(Arrays.asList("6","/","0"));
        String expect = "error: 0는 올바르지 않은 포맷입니다.\n" +
                "다음과 같이 포맷 양식을 지켜주세요.\n" +
                "`0으로 나눌 수 없습니다.`";

        //when
        CalCommand.action(command);

        //then
        String actual = OUTPUT_STREAM.toString().trim();

        Assertions.assertThat(actual).isEqualTo(expect);
    }

    @Test
    void testCheckNumberWithNumber(){
        // given
        boolean expect = true;

        // when
        boolean actual = CalCommand.checkNumber("3");

        // then
        Assertions.assertThat(actual).isEqualTo(expect);
    }

    @Test
    void testCheckNumberWithNotNumber(){
        // given
        boolean expect = false;

        // when
        boolean actual = CalCommand.checkNumber("a");

        // then
        Assertions.assertThat(actual).isEqualTo(expect);
    }

    @Test
    void testActionWrongOp(){
        //given
        ArrayList<String> command = new ArrayList<>(Arrays.asList("6","?","4"));
        String expect = "error: `?`는 올바르지 않은 메타데이터 입니다.\n" +
                "다음의 메타 데이터중 하나를 선택해주세요.\n" +
                "[+|-|*|/]";

        //when
        CalCommand.action(command);

        //then
        String actual = OUTPUT_STREAM.toString().trim();

        Assertions.assertThat(actual).isEqualTo(expect);
    }

    @AfterEach
    void outStream() {
        System.setOut(PRINT_STREAM);
    }
}