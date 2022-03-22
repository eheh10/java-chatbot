package com.banner;

import java.io.IOException;

public class BootBanner{
    private static final String FILE_NAME = "banner.txt";
    private Banner banner = new Banner(FILE_NAME);

    public String display() throws IOException {
        String output = banner.display();
        return output+" 버전1";
    }

    public void update(String content){

    }
}