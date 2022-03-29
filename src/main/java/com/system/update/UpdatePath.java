package com.system.update;

import com.file.BaseFile;

import java.io.IOException;
import java.util.List;

public class UpdatePath extends CommonUpdate{
    private final BaseFile baseFile;

    public UpdatePath(BaseFile baseFile){
        this.baseFile = baseFile;
    }

    @Override
    protected List getSupportFile() {
        return List.of("f","file");
    }

    @Override
    public void execute(String path) throws IOException {
        baseFile.setRelativeFilePath(path);
    }
}
