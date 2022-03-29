package com.system.update;

import java.io.IOException;

public interface UpdateFile {
    boolean isSupport(String file);
    void execute(String txt) throws IOException;
}
