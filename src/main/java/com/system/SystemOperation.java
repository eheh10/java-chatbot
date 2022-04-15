package com.system;

import com.domain.meta.Meta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SystemOperation implements SystemMeta{
    private final SystemMeta systemMeta;

    //사용처를 제공
//    public SystemOperation (List<SystemMeta> systemMetas, SystemMeta defaultSystemMeta, String subCommand){
//        this.systemMeta = systemMetas.stream()
//                .filter(meta -> meta.isSupport(subCommand))
//                .findFirst()
//                .orElse(defaultSystemMeta); //순서 영향 없애기 - 느슨한 결합
//    }
//
    public SystemOperation (SystemMeta systemMeta){
        this.systemMeta = systemMeta;
    }

    @Override
    public boolean isSupport(Meta subCommand) {
        return Objects.nonNull(systemMeta);
    }

    @Override
    public void execute(ArrayList<String> command) throws IOException {
        systemMeta.execute(command);
    }

    //팩토리 "메소드"
    public static SystemOperation of(List<SystemMeta> systemMetas, SystemMeta defaultSystemMeta, Meta subCommand){
        if (systemMetas == null){
            return new SystemOperation(defaultSystemMeta);
        }

        SystemMeta systemMeta = systemMetas.stream()
                .filter(meta -> meta.isSupport(subCommand))
                .findFirst()
                .orElse(defaultSystemMeta);

        return new SystemOperation(systemMeta);
    }
}
