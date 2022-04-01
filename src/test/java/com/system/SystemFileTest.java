package com.system;


import com.file.BaseFile;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class SystemFileTest {
    private static final BaseFile baseFile = new BaseFile();
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
        SystemFile systemFile = new SystemFile(baseFile);
        List expect = List.of("f","file");

        // when
        List actual = systemFile.getSupportCommand();

        // then
        Assertions.assertThat(actual).isEqualTo(expect);
    }

    @Test
    void testIsSupportWithF(){
        // given
        SystemFile systemFile = new SystemFile(baseFile);
        String subCommand = "f";

        // when
        boolean actual = systemFile.isSupport(subCommand);

        // then
        Assertions.assertThat(actual).isEqualTo(true);
    }

    @Test
    void testIsSupportWithFile(){
        // given
        SystemFile systemFile = new SystemFile(baseFile);
        String subCommand = "file";

        // when
        boolean actual = systemFile.isSupport(subCommand);

        // then
        Assertions.assertThat(actual).isEqualTo(true);
    }

    @Test
    void testExecute(){
        // given
        SystemFile systemFile = new SystemFile(baseFile);
        ArrayList<String> command = new ArrayList<>(Arrays.asList(""));
        String expect = baseFile.getSaveFilePath();

        // when
        systemFile.execute(command);
        String actual = OUTPUT_STREAM.toString().trim();

        // then
        Assertions.assertThat(actual).isEqualTo(expect);
    }
}