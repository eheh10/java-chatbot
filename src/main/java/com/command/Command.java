package com.command;

import java.io.IOException;
import java.util.ArrayList;

public interface Command {
    void action(ArrayList<String> command) throws IOException;
    boolean isSupport(String command);
}
