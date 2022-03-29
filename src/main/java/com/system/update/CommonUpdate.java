package com.system.update;

import java.util.List;

public abstract class CommonUpdate implements UpdateFile{
    @Override
    public boolean isSupport(String file) {
        return getSupportFile().contains(file);
    }

    protected abstract List getSupportFile();
}
