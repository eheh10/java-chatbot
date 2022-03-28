package com.command;

import java.io.IOException;
import java.util.ArrayList;

public class ErrorCommand implements Command{
    @Override
    public void action(ArrayList<String> command) throws IOException {
        System.out.println("warning: `"+command+"`는 존재하지 않는 명령어 입니다.");
    }

    @Override
    public boolean isSupport(String command) {
        return true;
    }
}
