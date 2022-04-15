package com.system;

import com.domain.meta.Meta;

import java.io.IOException;
import java.util.ArrayList;

public interface SystemMeta {
    boolean isSupport(Meta subCommand);
    void execute(ArrayList<String> command) throws IOException;
}
