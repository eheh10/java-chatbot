package com.system;

import com.domain.meta.Meta;
import com.file.BaseFile;

import java.util.ArrayList;

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
    protected Meta getSupportCommand() {
        return Meta.FILE;
    }


}
