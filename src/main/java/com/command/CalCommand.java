package com.command;

import java.util.ArrayList;

public class CalCommand {

    public static void action(ArrayList<String> command){
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
