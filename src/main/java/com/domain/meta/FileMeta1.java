package com.domain.meta;

import java.util.List;

public class FileMeta1 extends Meta1 {

    @Override
    protected List<String> getValues() {
        return List.of("f","file");
    }
}
