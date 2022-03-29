package com.system;

import java.util.ArrayList;
import java.util.List;

public class SystemError extends CommonSystemMeta{
    private String subCommand;

    @Override
    public boolean isSupport(String subCommand) {
        this.subCommand = subCommand;
        return true;
    }

    @Override
    public void execute(ArrayList<String> command) {
        displayMeta(subCommand);
    }

    @Override
    protected List getSupportCommand() {
        return List.of();
    }

    private void displayMeta(String subCommand){
        System.out.println("error: `"+subCommand+"`는 올바르지 않은 메타데이터 입니다.\n"+
                "[e|exit]\n" +
                "[f|file]\n" +
                "[u|update] [b|banner|] [txt:banner]\n" +
                "[u|update] [e|exit] [txt:bye] \n" +
                "[u|update] [f|file] [txt:relativeFilePath]"
        );
    }
}
