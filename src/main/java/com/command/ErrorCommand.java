package com.command;

import java.io.IOException;
import java.util.ArrayList;

public class ErrorCommand implements Command{
    private String command;

    public ErrorCommand(String command){
        this.command=command;
    }

    @Override
    public void action(ArrayList<String> command) throws IOException {
        System.out.println("warning: `"+this.command+"`는 존재하지 않는 명령어 입니다.");
    }

    @Override
    public boolean isSupport(String command) {
        return true;
    }
}
