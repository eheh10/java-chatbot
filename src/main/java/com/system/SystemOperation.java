package com.system;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SystemOperation implements SystemMeta{
    List<SystemMeta> systemMetas;
    SystemMeta systemMeta;

    public SystemOperation (List<SystemMeta> systemMetas, String subCommand){
        this.systemMetas = systemMetas;
        this.systemMeta = systemMetas.stream()
                .filter(meta -> meta.isSupport(subCommand))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public boolean isSupport(String subCommand) {
        return Objects.nonNull(systemMetas);
    }

    @Override
    public void execute(ArrayList<String> command) throws IOException {
        systemMeta.execute(command);
    }
}
