package com.command;


import com.file.BaseFile;
import com.file.OpenFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class FileCommand {
    static ArrayList<OpenFile> openFileList = new ArrayList<>();

    public static void action(ArrayList<String> command) throws IOException {
        command = addDefault(command);

        String meta = command.get(0);
        String mode = command.get(1);

        if(Arrays.asList("l","list").contains(meta)){
            displayList(mode);
        }else if (Arrays.asList("c","cur","current").contains(meta)){
            if(openFileList.size()==0){
                System.out.println("error: 열려 있는 파일이 없습니다.");
                return;
            }

            OpenFile of = openFileList.get(openFileList.size()-1);

            if (mode.equals("e")||mode.equals("exit")){
                openFileList.remove(openFileList.size()-1);
            }else if (mode.equals("c")||mode.equals("commit")){
                of.pushCommit();
            }else if (mode.equals("r")||mode.equals("rollback")){
                of.rollback();
            }else{
                System.out.println("error: `"+mode+"`는 올바르지 않은 메타데이터 입니다.\n" +
                        "다음의 메타 데이터중 하나를 선택해주세요.\n" +
                        "[c|cur|current|txt:fileName] [e|exit]\n" +
                        "[c|cur|current|txt:fileName] [c|commit]\n" +
                        "[c|cur|current|txt:fileName] [r|rollback]"
                );
                return;
            }
        }else{ //fileName
            BaseFile baseFile = new BaseFile();
            String defaultPath = baseFile.getSaveFilePath();

            File filename = new File(Paths.get(defaultPath,meta).toString());
            if (!filename.exists()){
                System.out.println("warning: `"+filename.getName()+"`는 존재하지 않는 파일 입니다.");
                return;
            }

            OpenFile of = null;
            int idx = -1;
            for(int i = 0; i< openFileList.size(); i++){
                if (openFileList.get(i).getFilename().getName().equals(meta)){
                    of = openFileList.get(i);
                    idx=i;
                }
            }

            if(of==null){
                of = new OpenFile();
                of.setFilename(filename);
            }

            if (mode.equals("r")||mode.equals("read")){
                of.readFile();
                return;
            }

            if (mode.equals("w")||mode.equals("write")){
                if (idx==-1){
                    openFileList.add(of);
                }
                of.setOs(false);
                of.setMode(mode.charAt(0));
                of.writeCommit();
                return;
            }else if (mode.equals("a")||mode.equals("append")){
                if (idx==-1){
                    openFileList.add(of);
                }
                of.setOs(true);
                of.setMode(mode.charAt(0));
                of.writeCommit();
                return;
            }

            if (idx==-1){
                System.out.println("error: `"+of.getFilename().getName()+"`는 열려 있지 않은 파일 입니다.");
                return;
            }

            if (mode.equals("c")||mode.equals("commit")){
                of.pushCommit();
            }else if (mode.equals("r")||mode.equals("rollback")){
                of.rollback();
            }else if (mode.equals("e")||mode.equals("exit")){
                openFileList.remove(idx);
            }else{
                System.out.println("error: `"+mode+"`는 올바르지 않은 메타데이터 입니다.\n" +
                        "다음의 메타 데이터중 하나를 선택해주세요.\n" +
                        "[txt:fileName] [|r|read]\n" +
                        "[txt:fileName] [w|write]\n" +
                        "[txt:fileName] [a|append]"
                );
            }
        }
    }

    static ArrayList<String> addDefault(ArrayList<String> command){
        if (command.size()==0){
            command.add("list");
            command.add("ec");
            return command;
        }

        if (Arrays.asList("ec","eo","o","open","e", "exit").contains(command.get(0))){
            command.add(0,"list");
            return command;
        }

        if (command.size()==1){
            command.add("read");
            return command;
        }

        return command;
    }

    static void displayList(String mode){
        BaseFile baseFile = new BaseFile();
        File[] fileList = new File(baseFile.getSaveFilePath()).listFiles();

        if (mode.equals("ec")||mode.equals("eo")){
            for(File f:fileList){
                System.out.println(f.getName());
            }
        }else if (mode.equals("o")||mode.equals("open")){
            for(OpenFile f: openFileList){
                System.out.println(f.getFilename().getName());
            }
        }else if (mode.equals("e")||mode.equals("exit")){
            ArrayList<String> opened = new ArrayList<>();;

            for(OpenFile f: openFileList){
                opened.add(f.getFilename().getName());
            }

            for(File f:fileList){
                if(!opened.contains(f.getName())) {
                    System.out.println(f.getName());
                }
            }
        }else{
            System.out.println("error: `"+mode+"`는 올바르지 않은 메타데이터 입니다.\n" +
                    "다음의 메타 데이터중 하나를 선택해주세요.\n" +
                    "[|l|list] [|ec|eo]\n" +
                    "[|l|list] [o|open]\n" +
                    "[|l|list] [e|exit]"
            );
        }
    }

    public static void checkOpenMode() throws IOException {
        if (openFileList.size()==0){
            return;
        }

        OpenFile of = openFileList.get(openFileList.size()-1);
        String fileName = of.getFilename().getName();
        String mode = String.valueOf(of.getMode());

        System.out.println("("+fileName+" "+mode+"모드)");
        ArrayList prevMeta = new ArrayList();
        prevMeta.add(fileName);
        prevMeta.add(mode);

        FileCommand.action(prevMeta);
    }

}
