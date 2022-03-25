package com.file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class EditFile {
    private File currentFile=null;
    private OutputStream os=null;
    private StringBuilder commit = new StringBuilder();

    public void setCurrentFile(File file){
        currentFile=file;
    }

    public File getCurrentFile(){
        return currentFile;
    }

    public void setOutputStream(OutputStream os){
        this.os=os;
    }

    public OutputStream getOutputStream(){
        return os;
    }

    public void write(String inputText){
        commit.append(inputText+"\n");
    }

    public void commit() throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(os,8192);

        bos.write(commit.toString().getBytes(StandardCharsets.UTF_8));

        bos.flush();
        bos.close();
    }

    public void rollback(OutputStream os) throws IOException {

    }

}

