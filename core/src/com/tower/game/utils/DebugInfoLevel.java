package com.tower.game.utils;

public enum DebugInfoLevel {
    INFO("INFO"),
    WARNING("WARNING"),
    FATAL_ERROR("FATAL ERROR"),
    DEBUG("DEBUG");


    public final String text;

    DebugInfoLevel(String text){
        this.text = text;
    }
}
