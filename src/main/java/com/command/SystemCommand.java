package com.command;

import com.banner.BootBanner;
import com.banner.ExitBanner;
import com.file.BaseFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SystemCommand {

    public static void action(BootBanner bootBanner, ArrayList<String> command) throws IOException {
        if (command.size()==0){
            displayMeta();
        }

        ExitBanner exitBanner = new ExitBanner();
        BaseFile baseFile = new BaseFile();
        String meta = command.get(0); // e|exit or f|file or u|update

        if(meta.equals("e")||meta.equals("exit")){
            System.out.println(exitBanner.display());
            System.exit(0);
        }else if(meta.equals("f")||meta.equals("file")){
            System.out.println(baseFile.getSaveFilePath());
        }else if(meta.equals("u")||meta.equals("update")){
            String updateFile = command.get(1);
            boolean defaultBanner = !Arrays.asList("b","banner","e","exit","f","file").contains(updateFile);
            String txt = String.join(" ",command.subList(defaultBanner?1:2,command.size()));

            if(updateFile.equals("f")||updateFile.equals("file")){
                baseFile.setRelativeFilePath(command.get(2));
            }else{
                if (updateFile.equals("b")||updateFile.equals("banner")){
                    bootBanner.update(txt);
                }else if (updateFile.equals("e")||updateFile.equals("exit")){
                    exitBanner.update(txt);
                }
            }

        }else{
            System.out.println("error: `"+meta+"`는 올바르지 않은 메타데이터 입니다.\n");
            displayMeta();
        }
    }

    public static void displayMeta(){
        System.out.println("다음의 메타 데이터중 하나를 선택해주세요.\n" +
                "[e|exit]\n" +
                "[f|file]\n" +
                "[u|update] [b|banner|] [txt:banner]\n" +
                "[u|update] [e|exit] [txt:bye] \n" +
                "[u|update] [f|file] [txt:relativeFilePath]");
    }

}
