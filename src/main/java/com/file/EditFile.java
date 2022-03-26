package com.file;

import com.util.InputStreamUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class EditFile {
    private File currentFile=null;
    private OutputStream os=null;
    private boolean isAppend = false;
    private String defaultContent = "";
    private StringBuilder input = new StringBuilder();
    private ArrayList<String> commit = new ArrayList<>();
    private int commitIdx = 0;
    private int minIdx = 0;

    public void setCurrentFile(File file){
        currentFile=file;
    }

    public File getCurrentFile(){
        return currentFile;
    }

    public void setOutputStream(OutputStream os) throws IOException {
        this.os=os;
        defaultContent = InputStreamUtil.read(new FileInputStream(currentFile));
    }

    public void setIsAppend(boolean isAppend) throws IOException {
        this.isAppend=isAppend;
    }

    public void write(ArrayList<String> inputText){
        input.append(String.join("\n",inputText));
    }

    public void commit() throws IOException {
        commit.add(input.toString());

        BufferedOutputStream bos = new BufferedOutputStream(os,8192);

        bos.write("\n".getBytes(StandardCharsets.UTF_8));
        bos.write(String.join("",commit.subList(commitIdx,commit.size())).getBytes(StandardCharsets.UTF_8));
        bos.flush();

        commitIdx = commit.size();
        //input reset
        input.setLength(0);
    }

    public void rollback() throws IOException {
        OutputStream os = new FileOutputStream(currentFile,false);
        BufferedOutputStream bos = new BufferedOutputStream(os,8192);

        if (commitIdx==minIdx){
            bos.write((defaultContent).getBytes(StandardCharsets.UTF_8));
            bos.flush();
            System.out.println("warning: rollback 할 수 있는 commit 이 없습니다.");
            return;
        }

        if (isAppend){
            bos.write((defaultContent+"\n").getBytes(StandardCharsets.UTF_8));
        }
        bos.write(String.join("",commit.subList(0,--commitIdx)).getBytes(StandardCharsets.UTF_8));

        bos.flush();
    }

    public void exit() throws IOException {
        if (os!=null){
            os.close();
        }
    }
}

