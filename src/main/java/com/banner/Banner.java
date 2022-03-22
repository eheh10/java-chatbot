package com.banner;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Banner {
    private final String fileName;

    protected Banner(String fileName){
        this.fileName=fileName;
    }

    public String display() throws IOException {
        InputStream is = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(is,8192);
        InputStreamReader isr = new InputStreamReader(bis, StandardCharsets.UTF_8);
        char[] buffer = new char[10];
        int len;

        StringBuilder input=new StringBuilder(1024);

        while ((len=isr.read(buffer))!=-1){
            input.append(buffer,0,len);
        }

        return input.toString();
    }

    public void update(String content){

    }
}
