package com.command;


import com.file.BaseFile;
import com.file.EditFile;
import com.util.InputStreamUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class FileCommand {
    static ArrayList<EditFile> openFileList = new ArrayList<>();

    public static void action(ArrayList<String> command) throws IOException {
        String meta = command.size()>0? command.get(0): "list";
        boolean defaultListMode = Arrays.asList("ec","eo","o","open","e","exit").contains(meta);
        boolean defaultFile = Arrays.asList("c","cur","current").contains(meta);
        String mode = "";

        if (command.size()>1 && !defaultListMode){
            mode = command.get(1);
        }else if(defaultListMode){
            meta = "list";
            mode = command.get(0);
        }

        if(Arrays.asList("l","list").contains(meta)){
            displayList(mode);
        }else{
            BaseFile baseFile = new BaseFile();
            String defaultPath = baseFile.getSaveFilePath();
            String currentFileName = "";

            if (defaultFile){
                if(command.size()!=2 || Arrays.asList("c","commit","rol","rollback","e","exit").contains(mode)){
                    System.out.println("error: `"+mode+"`는 올바르지 않은 메타데이터 입니다.\n" +
                        "다음의 메타 데이터중 하나를 선택해주세요.\n" +
                        "[c|cur|current|txt:fileName] [e|exit]\n" +
                        "[c|cur|current|txt:fileName] [c|commit]\n" +
                        "[c|cur|current|txt:fileName] [r|rollback]"
                    );
                    return;
                }else if(openFileList.size()==0){
                    System.out.println("error: 열려 있는 파일이 없습니다.");
                    return;
                }
                currentFileName = openFileList.get(openFileList.size()-1).getCurrentFile().getName();
            }else{
                currentFileName = meta;
            }

            File currentFile = new File(Paths.get(defaultPath,currentFileName).toString());
            if (!currentFile.exists()){
                System.out.println("warning: `"+currentFileName+"`는 존재하지 않는 파일 입니다.");
                return;
            }

            if (!defaultFile) {
                if (mode.equals("") || mode.equals("r") || mode.equals("read")) {
                    String output = InputStreamUtil.read(new FileInputStream(currentFile));
                    System.out.println(output);
                    return;
                }else if (Arrays.asList("w","write","a","append").contains(mode)){
                    EditFile ef = searchEditFile(currentFileName);
                    if (ef.getCurrentFile()==null){
                        ef.setCurrentFile(currentFile);

                        OutputStream os=null;
                        if (mode.equals("w") || mode.equals("write")) {
                            os = new FileOutputStream(currentFile);
                        } else if (mode.equals("a") || mode.equals("append")) {
                            os = new FileOutputStream(currentFile, true);
                        }
                        ef.setOutputStream(os);
                        openFileList.add(ef);
                    }

                    InputStream is = System.in;
                    BufferedInputStream bis = new BufferedInputStream(is,8192);
                    InputStreamReader isr = new InputStreamReader(bis, StandardCharsets.UTF_8);
                    BufferedReader br = new BufferedReader(isr);
                    StringBuilder newInput=new StringBuilder();

                    String in;
                    while (!(in = br.readLine()).equals("")) {
                        newInput.append(in);
                    }

                    if (newInput.toString().split(" ")[0].equals("/file")) {
                        String[] newCommand = newInput.toString().split(" ");
                        action(new ArrayList<>(Arrays.asList(newCommand).subList(1, newCommand.length)));
                    } else {
                        ef.write(newInput.toString());
                        FileCommand.action(command);
                    }
                    return;
                }
            }

            EditFile ef = searchEditFile(currentFileName);
            if (ef.getCurrentFile()==null){
                System.out.println("warning: "+currentFileName+ "는 열려있는 파일이 아닙니다.");
                return;
            }

            if (mode.equals("e")||mode.equals("exit")){
                openFileList.remove(ef);
            }else if (mode.equals("c")||mode.equals("commit")){
                ef.commit();
            }else if (mode.equals("rol")||mode.equals("rollback")){
//                InputStreamUtil.rollback(os);
            }else if (!mode.equals("")){
                System.out.println("error: `"+mode+"`는 올바르지 않은 메타데이터 입니다.\n" +
                    "다음의 메타 데이터중 하나를 선택해주세요.\n" +
                    "[txt:fileName] [|r|read]\n" +
                    "[txt:fileName] [w|write]\n" +
                    "[txt:fileName] [a|append]" +
                    "[c|cur|current|txt:fileName] [e|exit]\n" +
                    "[c|cur|current|txt:fileName] [c|commit]\n" +
                    "[c|cur|current|txt:fileName] [r|rollback]"
                );
                return;
            }
        }
    }

    static void displayList(String mode){
        BaseFile baseFile = new BaseFile();
        File[] fileList = new File(baseFile.getSaveFilePath()).listFiles();

        if (mode.equals("")||mode.equals("ec")||mode.equals("eo")){
            for(File f:fileList){
                System.out.println(f.getName());
            }
        }else if (mode.equals("o")||mode.equals("open")){
            for(EditFile f: openFileList){
                System.out.println(f.getCurrentFile().getName());
            }
        }else if (mode.equals("e")||mode.equals("exit")){
            ArrayList<String> opened = new ArrayList<>();;

            for(EditFile f: openFileList){
                opened.add(f.getCurrentFile().getName());
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

    public static EditFile searchEditFile(String searchFileName){
        for (int i=0; i<openFileList.size(); i++){
            if (openFileList.get(i).getCurrentFile().getName().equals(searchFileName)){
                return openFileList.get(i);
            }
        }
        return new EditFile();
    }

}
