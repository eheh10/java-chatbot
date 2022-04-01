package com.system;

import com.banner.ExitBanner;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

class SystemExitTest {
    private static final ExitBanner exitBanner = new ExitBanner();
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
        SystemExit systemExit = new SystemExit(exitBanner);
        List expect = List.of("e","exit");

        // when
        List actual = systemExit.getSupportCommand();

        // then
        Assertions.assertThat(actual).isEqualTo(expect);
    }

    @Test
    void testIsSupportWithE(){
        // given
        SystemExit systemExit = new SystemExit(exitBanner);
        String subCommand = "e";

        // when
        boolean actual = systemExit.isSupport(subCommand);

        // then
        Assertions.assertThat(actual).isEqualTo(true);
    }

    @Test
    void testIsSupportWithExit(){
        // given
        SystemExit systemExit = new SystemExit(exitBanner);
        String subCommand = "exit";

        // when
        boolean actual = systemExit.isSupport(subCommand);

        // then
        Assertions.assertThat(actual).isEqualTo(true);
    }

//    @Test
//    void testExecute() throws IOException {
//        // given
//        SystemExit systemExit = new SystemExit(exitBanner);
//        ArrayList<String> command = new ArrayList<>(Arrays.asList(""));
//        String expect = exitBanner.display();
//
//        // when
//        systemExit.execute(command);
//        String actual = OUTPUT_STREAM.toString().trim();
//
//        // then
//        Assertions.assertThat(actual).isEqualTo(expect);
//    }

}