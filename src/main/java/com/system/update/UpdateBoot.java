package com.system.update;

import com.banner.BootBanner;

import java.io.IOException;
import java.util.List;

public class UpdateBoot extends CommonUpdate{
    private final BootBanner bootBanner;

    public UpdateBoot(BootBanner bootBanner) {
        this.bootBanner = bootBanner;
    }

    @Override
    public boolean isSupport(String file) {
        return true; //default file
    }

    @Override
    protected List getSupportFile() {
        return List.of("b","banner");
    }

    @Override
    public void execute(String content) throws IOException {
        bootBanner.update(content);
    }
}
