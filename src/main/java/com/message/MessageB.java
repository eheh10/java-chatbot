package com.message;

public class MessageB implements Message {

    @Override
    public boolean isSupport(String meta) {
        return "b".equals(meta);
    }

    @Override
    public String getValue() {
        return "B message";
    }
}
