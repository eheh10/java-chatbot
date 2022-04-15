package com.system.update;

import com.banner.BootBanner;
import com.domain.meta.BannerMeta1;
import com.domain.meta.ExitMeta1;
import com.domain.meta.FileMeta1;
import com.domain.meta.Meta1;

import java.io.IOException;
import java.util.List;

public class UpdateDefault implements UpdateFile{
    private final BootBanner bootBanner;

    public UpdateDefault(BootBanner bootBanner) {
        this.bootBanner = bootBanner;
    }

    @Override
    public boolean isSupport(String file) {
        BannerMeta1 bannerMeta = new BannerMeta1();
        ExitMeta1 exitMeta = new ExitMeta1();
        Meta1 defaultMeta1 = bannerMeta.combine(exitMeta).combine(new FileMeta1());

        return !List.of("b","banner","e","exit","f","file").contains(file);
    }

    @Override
    public void execute(List command) throws IOException {
//        updateBoot.execute(command.subList(1,command.size()));

//        bootBanner.update(String.join(" ",command.subList(1,command.size())));
        bootBanner.update(command.subList(1,command.size()));
    }
}
