package com.system;

import com.banner.ExitBanner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    protected List getSupportCommand() {
        return List.of("e","exit");
    }

}
