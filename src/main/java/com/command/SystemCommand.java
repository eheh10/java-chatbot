package com.command;

import com.banner.BootBanner;
import com.banner.ExitBanner;
import com.file.BaseFile;
import com.system.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SystemCommand implements Command{
    private final BootBanner bootBanner;

    public SystemCommand(BootBanner bootBanner){
        this.bootBanner=bootBanner;
    }

    @Override
    public boolean isSupport(String command) {
        return Objects.equals("/system",command);
    }

    public void action(ArrayList<String> command) throws IOException {
        String subCommand = command.size() > 0? command.get(0): "";

        ExitBanner exitBanner = new ExitBanner();
        BaseFile baseFile = new BaseFile();

        List<SystemMeta> systemMetas = List.of(new SystemExit(exitBanner)
                                            ,new SystemFile(baseFile)
                                            ,new SystemUpdate(baseFile,bootBanner,exitBanner)
                                            ,new SystemError()
                                        );

        SystemMeta systemMeta = systemMetas.stream()
                                .filter(meta -> meta.isSupport(subCommand))
                                .findFirst()
                                .orElseThrow();
        systemMeta.execute(command);
    }

}
