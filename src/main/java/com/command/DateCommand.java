package com.command;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class DateCommand {

    public static void action(ArrayList<String> command){
        LocalDate date = LocalDate.now();
        int dayNum = 0;

        if (command.size() > 0) {
            if (isNumber(command.get(0))) {
                dayNum = Integer.parseInt(command.get(0));
            } else if (command.size() > 1 && Arrays.asList("c", "cal", "calculate").contains(command.get(0))) {
                String meta = command.get(1);
                boolean defaultDay = meta.equals("+")||meta.equals("-");
                String op = defaultDay?meta:"";
                dayNum = defaultDay&&command.size()>2 ? Integer.parseInt(command.get(2)) :0;

                if (!defaultDay) {
                    try {
                        date = LocalDate.parse(meta, DateTimeFormatter.BASIC_ISO_DATE); //yyyyMMdd
                    } catch (Exception e) {
                        System.out.println("error: " + meta + "는 올바르지 않은 포맷입니다.\n" +
                            "다음과 같이 포맷 양식을 지켜주세요.\n" +
                            "`yyyyMMdd 패턴으로 받습니다.`");
                        return;
                    }

                    if(command.size() > 2){
                        op = command.get(2);
                    }else{
                        System.out.println("error: " + String.join(" ", command) + "는 올바르지 않은 메타 데이터입니다. \n" +
                            "다음의 메타 데이터를 지켜주세요.\n" +
                            "[c|cal|calculate] [|txt:yyyyMMdd] [+|-] [|num:days]"
                        );
                    }

                    if (command.size()==4) {
                        if (isNumber(command.get(3))){
                            dayNum = Integer.parseInt(command.get(3));
                        }else {
                            System.out.println("error: "+command.get(3)+"는 올바르지 않은 포맷입니다.\n" +
                                "다음과 같이 포맷 양식을 지켜주세요.\n" +
                                "`숫자 데이터만 가능합니다.`"
                            );
                            return;
                        }
                    }
                }

                if (op.equals("-")) {
                    dayNum = dayNum * -1;
                } else if (!op.equals("+")) {
                    System.out.println("error: `" + op + "`는 올바르지 않은 메타데이터 입니다.\n" +
                        "다음의 메타 데이터중 하나를 선택해주세요.\n" +
                        "[+|-]"
                    );
                    return;
                }
            } else {
                System.out.println("error: " + String.join(" ", command) + "는 올바르지 않은 메타 데이터입니다. \n" +
                    "다음의 메타 데이터를 지켜주세요.\n" +
                    "[|txt:addNumber]\n" +
                    "[c|cal|calculate] [|txt:yyyyMMdd] [+|-] [|num:days]"
                );
                return;
            }
        }
        System.out.println(date.plusDays(dayNum).format(DateTimeFormatter.ISO_LOCAL_DATE)); //yyyy-MM-dd
    }

    static boolean isNumber(String data){
        if (data.charAt(0)=='-'||data.charAt(0)=='+'){
            data = data.substring(1);
        }

        for(char c:data.toCharArray()){
            if (!Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }

}
