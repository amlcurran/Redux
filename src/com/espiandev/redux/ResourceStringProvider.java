package com.espiandev.redux;

public interface ResourceStringProvider {
    String getString(int id);
    String getString(int id, Object... args);
}
