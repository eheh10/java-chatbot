package com.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BaseFile {
    private static final String AB_ROUTE = new File("").getAbsolutePath();
    private static String relativeFilePath = "resource";

    public void setRelativeFilePath(String relativeFilePath) throws IOException {
        File oldFilePath = new File(Paths.get(AB_ROUTE,this.relativeFilePath).toString());
        File newFilePath = new File(Paths.get(AB_ROUTE, relativeFilePath).toString());

        // 디렉토리 생성
        if(!newFilePath.exists()){
            newFilePath.mkdirs();
        }

        // 기존 파일 복사 및 삭제
        for(File f:oldFilePath.listFiles()){
            Files.copy(f.toPath(),Paths.get(AB_ROUTE, relativeFilePath,f.getName()));
            f.delete();
        }

        //기존 디렉토리 삭제
        oldFilePath.delete();

        this.relativeFilePath = relativeFilePath;
    }

    public String getSaveFilePath(){
        return Paths.get(AB_ROUTE, relativeFilePath).toString();
    }

}
