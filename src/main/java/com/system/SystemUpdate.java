package com.system;

import com.banner.BootBanner;
import com.banner.ExitBanner;
import com.file.BaseFile;
import com.system.update.UpdateBoot;
import com.system.update.UpdateExit;
import com.system.update.UpdateFile;
import com.system.update.UpdatePath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SystemUpdate extends CommonSystemMeta{
    private final BaseFile baseFile;
    private final BootBanner bootBanner;
    private final ExitBanner exitBanner;

    public SystemUpdate (BaseFile baseFile, BootBanner bootBanner, ExitBanner exitBanner){
        this.baseFile = baseFile;
        this.bootBanner = bootBanner;
        this.exitBanner = exitBanner;
    }

    @Override
    public void execute(ArrayList<String> command) throws IOException {
        String file = command.get(1);

        List<UpdateFile> updateFiles = List.of(new UpdatePath(baseFile), new UpdateExit(exitBanner), new UpdateBoot(bootBanner));
        UpdateFile updateFile = updateFiles.stream()
                                .filter(up -> up.isSupport(file))
                                .findFirst()
                                .orElseThrow();

        boolean isDefaultBanner = !List.of("b","banner","e","exit","f","file").contains(file);
        String txt = String.join(" ",command.subList(isDefaultBanner?1:2,command.size()));
        updateFile.execute(txt);

    }

    @Override
    protected List getSupportCommand() {
        return List.of("u","update");
    }

}
