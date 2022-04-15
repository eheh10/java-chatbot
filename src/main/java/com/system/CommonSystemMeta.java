package com.system;

import com.domain.meta.Meta;

public abstract class CommonSystemMeta implements SystemMeta{
    @Override
    public boolean isSupport(Meta subCommand) {
        return getSupportCommand().contain(subCommand);
    }

    protected abstract Meta getSupportCommand();
}
