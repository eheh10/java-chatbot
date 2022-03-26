package com.command;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

class DateCommandTest {
    private static final ByteArrayOutputStream OUTPUT_STREAM = new ByteArrayOutputStream();
    private static final PrintStream PRINT_STREAM = System.out;
    private static final LocalDate TODAY = LocalDate.now();
    private static final DateTimeFormatter OUTPUT_FORM = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter INPUT_FORM = DateTimeFormatter.BASIC_ISO_DATE;

    @BeforeEach
    public void setStream() {
        System.setOut(new PrintStream(OUTPUT_STREAM));
    }

    @AfterEach
    void outStream() {
        System.setOut(PRINT_STREAM);
    }

    @Test
    void testActionDefault(){
        //given
        ArrayList<String> command = new ArrayList<>();
        String expect = TODAY.format(OUTPUT_FORM);

        //when
        DateCommand.action(command);

        //then
        String actual = OUTPUT_STREAM.toString().trim();

        Assertions.assertThat(actual).isEqualTo(expect);
    }

    @Test
    void testActionDefaultplus(){
        //given
        ArrayList<String> command = new ArrayList<>(Arrays.asList("0"));
        String expect = TODAY.format(OUTPUT_FORM);

        //when
        DateCommand.action(command);

        //then
        String actual = OUTPUT_STREAM.toString().trim();

        Assertions.assertThat(actual).isEqualTo(expect);
    }

    @Test
    void testActionDefaultMinus(){
        //given
        ArrayList<String> command = new ArrayList<>(Arrays.asList("-1"));
        String expect = TODAY.plusDays(-1).format(OUTPUT_FORM);

        //when
        DateCommand.action(command);

        //then
        String actual = OUTPUT_STREAM.toString().trim();

        Assertions.assertThat(actual).isEqualTo(expect);
    }

    @Test
    void testActionCalDefaultDayNum(){
        //given
        ArrayList<String> command = new ArrayList<>(Arrays.asList("c","-"));
        String expect = TODAY.format(OUTPUT_FORM);

        //when
        DateCommand.action(command);

        //then
        String actual = OUTPUT_STREAM.toString().trim();

        Assertions.assertThat(actual).isEqualTo(expect);
    }

    @Test
    void testActionCalDefaultDay(){
        //given
        ArrayList<String> command = new ArrayList<>(Arrays.asList("cal","-","10"));
        String expect = TODAY.plusDays(-10).format(OUTPUT_FORM);

        //when
        DateCommand.action(command);

        //then
        String actual = OUTPUT_STREAM.toString().trim();

        Assertions.assertThat(actual).isEqualTo(expect);
    }

    @Test
    void testActionDayCal(){
        //given
        ArrayList<String> command = new ArrayList<>(Arrays.asList("calculate","20220502","+","10"));
        String expect = "2022-05-12";

        //when
        DateCommand.action(command);

        //then
        String actual = OUTPUT_STREAM.toString().trim();

        Assertions.assertThat(actual).isEqualTo(expect);
    }

    @Test
    void testActionWrongDayCal(){
        //given
        ArrayList<String> command = new ArrayList<>(Arrays.asList("calculate","2022-05-02","+","10"));
        String expect = "error: 2022-05-02는 올바르지 않은 포맷입니다.\n" +
                "다음과 같이 포맷 양식을 지켜주세요.\n" +
                "`yyyyMMdd 패턴으로 받습니다.`";

        //when
        DateCommand.action(command);

        //then
        String actual = OUTPUT_STREAM.toString().trim();

        Assertions.assertThat(actual).isEqualTo(expect);
    }

    @Test
    void testIsNumberWithNumber(){
        //given
        boolean expect = true;

        //when
        boolean actual = DateCommand.isNumber("123");

        //then
        Assertions.assertThat(actual).isEqualTo(expect);
    }

    @Test
    void testIsNumberWithMinusNumber(){
        //given
        boolean expect = true;

        //when
        boolean actual = DateCommand.isNumber("-123");

        //then
        Assertions.assertThat(actual).isEqualTo(expect);
    }

    @Test
    void testIsNumberWithNotNumber(){
        //given
        boolean expect = false;

        //when
        boolean actual = DateCommand.isNumber("123a");

        //then
        Assertions.assertThat(actual).isEqualTo(expect);
    }
}