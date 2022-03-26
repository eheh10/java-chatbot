package com.file;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

class BaseFileTest {
    private static final String AB_ROUTE = new File("").getAbsolutePath();
    private static final String TEST_PATH = "test_resource";

    @BeforeEach
    void setFile(){
    }

    @Test
    void testSetRelativeFilePath() throws IOException {
        // given
        BaseFile baseFile = new BaseFile();

        //when
        baseFile.setRelativeFilePath(TEST_PATH);
        String actual = baseFile.getSaveFilePath();

        //then
        Assertions.assertThat(actual).isEqualTo(Paths.get(AB_ROUTE,TEST_PATH).toString());
    }

    @Test
    void testCopyFile() throws IOException {
        // given
        BaseFile baseFile = new BaseFile();
        File prevFile = new File(baseFile.getSaveFilePath());
        String[] expect = prevFile.list();

        //when
        baseFile.setRelativeFilePath(TEST_PATH);
        File newFile = new File(baseFile.getSaveFilePath());
        String[] actual = newFile.list();

        //then
        Assertions.assertThat(actual.length).isEqualTo(expect.length);
        for(int i=0; i<actual.length; i++) {
            Assertions.assertThat(actual[i]).isEqualTo(expect[i]);
        }
    }

    @AfterEach
    void deleteFile(){
        File newFilePath = new File(Paths.get(AB_ROUTE, TEST_PATH).toString());
        for(File f:newFilePath.listFiles()){
            f.delete();
        }
        newFilePath.delete();
    }
}