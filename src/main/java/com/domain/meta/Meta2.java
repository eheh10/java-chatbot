package com.domain.meta;

import java.util.List;

public enum Meta2 {
    BANNER(List.of("b","banner")),
    EXIT(List.of("e","exit")),
    FILE(List.of("f","file"));

    private final List<String> values;

    private Meta2(List<String> values){
        this.values = values;
    }

    public boolean contain(Meta2 meta){
        return this.values.stream()
                .anyMatch(meta.values::contains);
    }

}
