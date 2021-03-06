package com.system.update;

import com.banner.ExitBanner;

import java.io.IOException;
import java.util.List;

public class UpdateExit extends CommonUpdate{
    private final ExitBanner exitBanner;

    public UpdateExit(ExitBanner exitBanner) {
        this.exitBanner = exitBanner;
    }

    @Override
    protected List getSupportFile() {
        return List.of("e","exit");
    }


    @Override
    public void execute(List<String> command) throws IOException {
        exitBanner.update(String.join(" ",command.subList(2,command.size())));
    }
}
