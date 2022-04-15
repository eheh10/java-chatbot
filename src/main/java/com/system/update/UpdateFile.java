package com.system.update;

import java.io.IOException;
import java.util.List;

public interface UpdateFile {
    boolean isSupport(String file);
    void execute(List<String> command) throws IOException;
//    String getContent(List command);
}
