package com.command;


import com.message.Message;
import com.message.MessageA;
import com.message.MessageB;
import com.message.MessageC;

import java.util.ArrayList;
import java.util.List;

public class MessageCommand {
    private static final String A = "A 입니다";
    private static final String B = "B 입니다";
    private static final String C = "C 입니다";

    public static void action(ArrayList<String> meta) {
        String mode = meta.get(0);

        //절차지향 (흐름에 맞게 처리)
//        if (mode.equals("a")){
//            System.out.println(A);
//        }else if (mode.equals("b")){
//            System.out.println(B);
//        }else if (mode.equals("c")){
//            System.out.println(C);
//        }

        //객체지향 (조건에 따라 처리)
        //시스템화 -> 유지보수성 -> 변경사항 있을때 범위 알 수 있다! (MessageA/B/C 내부만 수정하면 정상 동작)
        List<Message> messages = List.of(new MessageA(),new MessageB(), new MessageC()); //messageList 변수명은 x(타입이 노출됨)
        String value = messages.stream()
                .filter(msg->msg.isSupport(mode))
                .map(msg -> msg.getValue())
                .findFirst()
                .orElse("default message");
        System.out.println(value);

        // orElse() 를 통해 디폴트 기능을 정의하는 것은 아래와 같은 처리를 간단화해준다
//        boolean isCheck = false;
//        for (Message m :messages){
//            if (m.isSupport(mode)){
//                isCheck = true;
//                System.out.println(m.getValue());
//                break;
//            }
//        }
//        if (!isCheck){
//            System.out.println("default message");
//        }
    }
}
