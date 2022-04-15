package com.message;

public class MessageC implements Message {

    @Override
    public boolean isSupport(String meta) {
        return "c".equals(meta);
    }

    @Override
    public String getValue() {
        return "C message";
    }
}
