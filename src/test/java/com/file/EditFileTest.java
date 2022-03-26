package com.file;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

class EditFileTest {
    public static final String TEST_FILE = "test.txt";
    public static final String TEST_CONTENT = "EditFile Test~!";
    private static final String TEST_INPUT = "Next Level";

    @BeforeEach
    void setFile() throws IOException {
        File testFile = new File(TEST_FILE);
        OutputStream os = new FileOutputStream(testFile);
        os.write(TEST_CONTENT.getBytes(StandardCharsets.UTF_8));
        os.close();
    }

    @Test
    void testCommitWithWriteMode() throws IOException {
        // given
        EditFile editFile = new EditFile();
        editFile.setCurrentFile(new File(TEST_FILE));
        editFile.setOutputStream(new FileOutputStream(TEST_FILE));

        ArrayList<String> inputText = new ArrayList<>();
        inputText.add(TEST_INPUT);

        String expect = TEST_INPUT;

        // when
        editFile.write(inputText);
        editFile.commit();
        editFile.exit();

        // then
        String actual = readTestFile();
        Assertions.assertThat(actual).isEqualTo(expect);
    }

    @Test
    void testCommitWithAppendMode() throws IOException {
        // given
        EditFile editFile = new EditFile();
        editFile.setCurrentFile(new File(TEST_FILE));
        editFile.setOutputStream(new FileOutputStream(TEST_FILE,true));
        editFile.setIsAppend(true);

        ArrayList<String> inputText = new ArrayList<>();
        inputText.add(TEST_INPUT);

        String expect = TEST_CONTENT+"\n"+TEST_INPUT;

        // when
        editFile.write(inputText);
        editFile.commit();
        editFile.exit();

        // then
        String actual = readTestFile();
        Assertions.assertThat(actual).isEqualTo(expect);
    }

    @Test
    void testRollback() throws IOException {
        // given
        EditFile editFile = new EditFile();
        editFile.setCurrentFile(new File(TEST_FILE));
        editFile.setOutputStream(new FileOutputStream(TEST_FILE,true));
        editFile.setIsAppend(true);

        ArrayList<String> inputText = new ArrayList<>();
        inputText.add(TEST_INPUT);

        String expect = TEST_CONTENT;

        // when
        editFile.write(inputText);
        editFile.commit();
        editFile.rollback();
        editFile.exit();

        // then
        String actual = readTestFile();
        Assertions.assertThat(actual).isEqualTo(expect);
    }

    private String readTestFile() throws IOException {
        InputStream is = new FileInputStream(TEST_FILE);
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
        Files.delete(Paths.get(TEST_FILE));
    }
}