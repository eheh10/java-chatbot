package com.system;

import com.banner.ExitBanner;
import com.domain.meta.Meta;

import java.io.IOException;
import java.util.ArrayList;

public class SystemExit extends CommonSystemMeta{
    private final ExitBanner exitBanner;

    public SystemExit(ExitBanner exitBanner) {
        this.exitBanner = exitBanner;
    }

    @Override
    public void execute(ArrayList<String> command) throws IOException {
        System.out.println(exitBanner.display());
        System.exit(0);
    }

    @Override
    protected Meta getSupportCommand() {
        return Meta.EXIT;
    }

}
