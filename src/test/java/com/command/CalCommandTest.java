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
        String expect = "error: 0??? ???????????? ?????? ???????????????.\n" +
                "????????? ?????? ?????? ????????? ???????????????.\n" +
                "`0?????? ?????? ??? ????????????.`";

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
        String expect = "error: `?`??? ???????????? ?????? ??????????????? ?????????.\n" +
                "????????? ?????? ???????????? ????????? ??????????????????.\n" +
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