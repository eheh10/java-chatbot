package com.command;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class DateCommand {

    public static void action(ArrayList<String> command){
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
