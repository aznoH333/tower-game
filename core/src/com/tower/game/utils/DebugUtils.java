package com.tower.game.utils;

import com.badlogic.gdx.Gdx;

public class DebugUtils {

    private final DebugInfoLevel logLevel = DebugInfoLevel.INFO;
    public static String lastMessage = "";

    private static void log(String logMessage, DebugInfoLevel level, boolean allowDuplicates){

        if (!allowDuplicates && lastMessage.equals(logMessage)){
            return;
        }
        lastMessage = logMessage;
        System.out.println("|" + level.text + "| " + logMessage);
    }

    public static void fatalCrash(String crashReason){
        log(crashReason, DebugInfoLevel.FATAL_ERROR, true);
        System.exit(-1);
    }

    public static void debugMessage(String message, boolean allowDuplicates){
        log(message, DebugInfoLevel.DEBUG, allowDuplicates);
    }

    public static void warn(String message){
        log(message, DebugInfoLevel.WARNING, true);
    }

    public static void info(String message){
        log(message, DebugInfoLevel.INFO, true);
    }

    public static void checkFps(){
        int fps = Gdx.graphics.getFramesPerSecond();

        if (fps < 60){
            log("Dropped fps bellow 60 : " + fps, DebugInfoLevel.WARNING, false);
        }
    }
}
