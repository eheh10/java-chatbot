package com.main;

import com.banner.BootBanner;
import com.command.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class Chatbot {

    public static void main(String[] args) throws IOException {
        System.out.println("==========챗봇 프로그램==========");
        BootBanner bootBanner = new BootBanner();
        System.out.println(bootBanner.display());

        InputStream is = System.in;
        BufferedInputStream bis = new BufferedInputStream(is,1024);
        InputStreamReader isr = new InputStreamReader(bis);
        BufferedReader br = new BufferedReader(isr);

        while(true){
            System.out.print(">");
            String[] input = br.readLine().split(" ");
            String command = input[0];
            ArrayList<String> meta = new ArrayList<>(Arrays.asList(input).subList(1,input.length));

            Command cmd = Stream.of(new SystemCommand(bootBanner), new CalCommand(), new DateCommand(), new FileCommand(), new ErrorCommand())
                    .filter(_cmd -> _cmd.isSupport(command))
                    .findFirst()
                    .orElseThrow();

            cmd.action(meta);

        }
    }
}
