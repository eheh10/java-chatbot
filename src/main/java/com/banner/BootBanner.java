package com.banner;

public class BootBanner extends Banner{
    private static final String FILE_NAME = "banner.txt";
//    private Banner banner = new Banner(FILE_NAME);

    public BootBanner() {
        super(FILE_NAME);
    }

//    public String display() throws IOException {
//        String output = banner.display();
//        return output+" 버전1";
//    }

}