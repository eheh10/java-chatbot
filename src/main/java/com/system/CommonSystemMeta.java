package com.system;

import java.util.List;

public abstract class CommonSystemMeta implements SystemMeta{
    @Override
    public boolean isSupport(String subCommand) {
        return getSupportCommand().contains(subCommand);
    }

    protected abstract List getSupportCommand();
}
