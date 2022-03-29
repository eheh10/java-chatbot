package com.system;

import java.io.IOException;
import java.util.ArrayList;

public interface SystemMeta {
    boolean isSupport(String subCommand);
    void execute(ArrayList<String> command) throws IOException;
}
