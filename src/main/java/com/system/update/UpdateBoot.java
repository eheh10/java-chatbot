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
    protected List getSupportFile() {
        return List.of("b","banner");
    }

    @Override
    public void execute(List command) throws IOException {
        bootBanner.update(String.join(" ",command.subList(2,command.size())));
    }
}
