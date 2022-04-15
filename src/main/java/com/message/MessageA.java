package com.message;

public class MessageA implements Message {

    @Override
    public boolean isSupport(String meta) {
//        if ("a".equals(meta)){ //nullPointError 방지 - Objects.equals("a",meta);
//            return true;
//        }
        return "a".equals(meta);
    }

    @Override
    public String getValue() {
        return "A message";
    }
}
