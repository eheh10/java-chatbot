package com.system;

import com.file.BaseFile;

import java.util.ArrayList;
import java.util.List;

public class SystemFile extends CommonSystemMeta{
    private final BaseFile baseFile;

    public SystemFile(BaseFile baseFile){
        this.baseFile = baseFile;
    }

    @Override
    public void execute(ArrayList<String> command) {
        System.out.println(baseFile.getSaveFilePath());
    }

    @Override
    protected List getSupportCommand() {
        return List.of("f","file");
    }


}
