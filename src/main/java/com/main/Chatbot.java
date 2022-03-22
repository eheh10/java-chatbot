package com.main;

import com.banner.BootBanner;
import com.banner.ExitBanner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

class SystemCommand {
    static String abRoute = new File("").getAbsolutePath();
    static String fileRoute = "resource";

    static void action(BootBanner bootBanner, ArrayList<String> command) throws IOException {
        ExitBanner exitBanner = new ExitBanner();
        String meta = command.get(0); // e|exit or f|file or u|update

        if(meta.equals("e")||meta.equals("exit")){
            exitBanner.display();
            System.exit(0);
        }else if(meta.equals("f")||meta.equals("file")){
            System.out.println(Paths.get(abRoute, fileRoute).toString());
        }else if(meta.equals("u")||meta.equals("update")){
            String filename = command.get(1);
            boolean defaultBanner = !Arrays.asList("b","banner","e","exit","f","file").contains(filename);

            String txt = String.join(" ",command.subList(defaultBanner?1:2,command.size()));
            System.out.println(txt);

            if(filename.equals("f")||filename.equals("file")){
                String prevFile = fileRoute;
                fileRoute = command.get(2);

                File oldFile = new File(Paths.get(abRoute,prevFile).toString());
                File newFile = new File(Paths.get(abRoute, fileRoute).toString());

                // 디렉토리 생성
                if(!newFile.exists()){
                    newFile.mkdirs();
                }

                // 기존 파일 복사 및 삭제
                for(File f:oldFile.listFiles()){
                    Files.copy(f.toPath(),Paths.get(abRoute, fileRoute,f.getName()));
                    f.delete();
                }

                //기존 디렉토리 삭제
                oldFile.delete();

            }else{
                if (filename.equals("b")||filename.equals("banner")){
                    bootBanner.update(txt);
                }else if (filename.equals("e")||filename.equals("exit")){
                    exitBanner.update(txt);
                }
            }

        }else{
            System.out.println("error: `"+meta+"`는 올바르지 않은 메타데이터 입니다.");
            return;
        }
    }

}

class CalCommand {

    static void action(ArrayList<String> command){
        int left = 0, right = 0;

        if(command.size()!=3){
            System.out.println("error: "+String.join(" ",command)+"는 올바르지 않은 메타데이터입니다. \n" +
                    "다음의 메타 데이터를 지켜주세요.\n" +
                    "[num:leftOperand] [+|-|*|/] [num:rightOperand]");
            return;
        }

        if (checkNumber(command.get(0))){
            left = Integer.parseInt(command.get(0));
        }else{
            return;
        }

        if (checkNumber(command.get(2))){
            right = Integer.parseInt(command.get(2));
        }else{
            return;
        }

        String op = command.get(1);

        if(op.equals("+")){
            System.out.println(left+right);
        }else if(op.equals("-")){
            System.out.println(left-right);
        }else if(op.equals("*")){
            System.out.println(left*right);
        }else if(op.equals("/")){
            if(right==0){
                System.out.println("error: "+right+"는 올바르지 않은 포맷입니다.\n" +
                        "다음과 같이 포맷 양식을 지켜주세요.\n" +
                        "`0으로 나눌 수 없습니다.`");
                return;
            }
            System.out.println(left/right);
        }else{
            System.out.println("error: `"+op+"`는 올바르지 않은 메타데이터 입니다.\n" +
                    "다음의 메타 데이터중 하나를 선택해주세요.\n" +
                    "[+|-|*|/]");
            return;
        }
    }

    static boolean checkNumber(String data){
        try {
            Integer.parseInt(data);
        }catch (Exception e){
            System.out.println("error: "+data+"는 올바르지 않은 포맷입니다.\n" +
                    "다음과 같이 포맷 양식을 지켜주세요.\n" +
                    "`숫자 데이터만 가능합니다.`");
            return false;
        }
        return true;
    }

}

class DateCommand {

    static void action(ArrayList<String> command){
        LocalDate today = LocalDate.now();

        if (command.size() == 0){
            System.out.println(today);
        }else if(command.size() == 1){
            if (checkNumber(command.get(0))){
                int dayNum = Integer.parseInt(command.get(0));
                System.out.println(today.plusDays(dayNum));
            }else{
                return;
            }
        }else if(Arrays.asList("c","cal","calculate").contains(command.get(0))){
            String meta = command.get(1);
            LocalDate date = today;

            if (meta.equals("+") || meta.equals("-")){
                command.add(1,date.format(DateTimeFormatter.BASIC_ISO_DATE));
            }else{
                try{
                    date = LocalDate.parse(meta, DateTimeFormatter.BASIC_ISO_DATE); //yyyyMMdd
                }catch (Exception e){
                    System.out.println("error: "+meta+"는 올바르지 않은 포맷입니다.\n" +
                            "다음과 같이 포맷 양식을 지켜주세요.\n" +
                            "`yyyyMMdd 패턴으로 받습니다.`");
                    return;
                }
            }

            int dayNum = 0;
            if (command.size()==4){
                if (checkNumber(command.get(3))){
                    dayNum = Integer.parseInt(command.get(3));
                }else{
                    return;
                }
            }

            String op = command.get(2);
            if(op.equals("-")){
                dayNum = dayNum * -1;
            }else if(!op.equals("+")){
                System.out.println("error: `"+op+"`는 올바르지 않은 메타데이터 입니다.\n" +
                        "다음의 메타 데이터중 하나를 선택해주세요.\n" +
                        "[+|-]");
                return;
            }
            System.out.println(date.plusDays(dayNum).format(DateTimeFormatter.ISO_LOCAL_DATE)); //yyyy-MM-dd

        }else{
            System.out.println("error: "+String.join(" ",command)+"는 올바르지 않은 메타 데이터입니다. \n" +
                    "다음의 메타 데이터를 지켜주세요.\n" +
                    "[|txt:addNumber]\n" +
                    "[c|cal|calculate] [|txt:yyyyMMdd] [+|-] [|num:days]");
            return;
        }
    }

    static boolean checkNumber(String data){
        try {
            Integer.parseInt(data);
        }catch (Exception e){
            System.out.println("error: "+data+"는 올바르지 않은 포맷입니다.\n" +
                    "다음과 같이 포맷 양식을 지켜주세요.\n" +
                    "`숫자 데이터만 가능합니다.`");
            return false;
        }
        return true;
    }
}

class OpenFile {
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

class FileCommand {
    static ArrayList<OpenFile> openFileList = new ArrayList<>();

    static void action(ArrayList<String> command) throws IOException {
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
            File filename = new File(Paths.get(SystemCommand.abRoute, SystemCommand.fileRoute,meta).toString());
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
        File[] fileList = new File(Paths.get(SystemCommand.abRoute, SystemCommand.fileRoute).toString()).listFiles();

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

    static void checkOpenMode() throws IOException {
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

public class Chatbot {

    public static void main(String[] args) throws IOException {
        System.out.println("==========챗봇 프로그램==========");
        BootBanner bootBanner = new BootBanner();
        bootBanner.display();
//        main.SystemCommand.displayBanner(main.SystemCommand.START_FILE); //상수 사용

        InputStream is = System.in;
        BufferedInputStream bis = new BufferedInputStream(is,1024);
        InputStreamReader isr = new InputStreamReader(bis);
        BufferedReader br = new BufferedReader(isr);

        while(true){
            System.out.print(">");
            String[] input = br.readLine().split(" ");
            String command = input[0];
            ArrayList<String> meta = new ArrayList<>(Arrays.asList(input).subList(1,input.length));

            switch (command){
                case "/system":
                    SystemCommand.action(bootBanner,meta);
                    break;
                case "/cal":
                    CalCommand.action(meta);
                    break;
                case "/date":
                    DateCommand.action(meta);
                    break;
                case "/file":
                    FileCommand.action(meta);
                    break;
                default:
                    System.out.println("warning: `"+command+"`는 존재하지 않는 명령어 입니다.");
            }
        }
    }
}
