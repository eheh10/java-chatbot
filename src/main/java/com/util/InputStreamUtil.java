package com.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class InputStreamUtil {
    private InputStreamUtil(){}

    public static String read(InputStream is) throws IOException {
        InputStreamReader rd = new InputStreamReader(is, StandardCharsets.UTF_8);
        char[] cbuffer = new char[100];
        int len=0;
        StringBuilder output=new StringBuilder();

        while((len=rd.read(cbuffer))!=-1){
            output.append(cbuffer,0,len);
        }
        return output.toString();
    }
}
