package com.main;

import com.banner.BootBanner;
import com.command.CalCommand;
import com.command.DateCommand;
import com.command.FileCommand;
import com.command.SystemCommand;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Chatbot {

    public static void main(String[] args) throws IOException {
        System.out.println("==========챗봇 프로그램==========");
        BootBanner bootBanner = new BootBanner();
        bootBanner.display();

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
