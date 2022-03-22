package com.file;

import com.command.FileCommand;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class OpenFile {
    private File filename;
    private ArrayList<String> commitList = new ArrayList<>();
    private int commitIdx = 0;

    private InputStream is = System.in;
    private BufferedInputStream bis = new BufferedInputStream(is,1024);
    private InputStreamReader isr = new InputStreamReader(bis, StandardCharsets.UTF_8);
    private BufferedReader br = new BufferedReader(isr);

    private OutputStream os = null;
    private BufferedOutputStream bos = null;
    private char mode;

    public File getFilename() {
        return this.filename;
    }

    public void setFilename(File filename){
        this.filename = filename;
    }

    public void setOs(boolean isAppend) throws FileNotFoundException {
        this.os = new FileOutputStream(this.filename.getPath(),isAppend);
        setBos(new BufferedOutputStream(os,1024));
    }

    public void setBos(BufferedOutputStream bos) {
        this.bos = bos;
    }

    public char getMode() {
        return mode;
    }

    public void setMode(char mode) {
        this.mode = mode;
    }

    public void readFile() throws IOException {
        InputStream is = new FileInputStream(filename.getPath());
        BufferedInputStream bis = new BufferedInputStream(is,1024);
        InputStreamReader isr = new InputStreamReader(bis,StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);

        String output;
        while((output=br.readLine())!=null){
            System.out.println(output);
        }
    }

    public void writeCommit() throws IOException {
        StringBuilder commit = new StringBuilder();
        String input;
        while(!(input=br.readLine()).equals("")){
            commit.append(input+"\n");
        }

        String[] command = commit.toString().replace("\n","").split(" ");
        if (command[0].trim().equals("/file")){
            FileCommand.action(new ArrayList(Arrays.asList(command).subList(1,command.length)));
            FileCommand.checkOpenMode();
        }else{
            commitList.add(commit.toString());
        }
    }

    public void pushCommit() throws IOException {
        if (commitIdx < 0){
            commitIdx = 0;
        }

        int i=commitIdx;
        for(;i<commitList.size(); i++){
            bos.write(commitList.get(i).getBytes(StandardCharsets.UTF_8));
        }
        commitIdx = i;
        bos.flush();
    }

    public void rollback() throws IOException {
        commitList.remove(commitList.size()-1);
        commitIdx=commitIdx-2;
        setOs(false);
        pushCommit();
    }
}
