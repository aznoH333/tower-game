package com.tower.game.utils;

public class DebugUtils {

    private final DebugInfoLevel logLevel = DebugInfoLevel.INFO;
    public static void log(String logMessage, DebugInfoLevel level){
        System.out.println("|" + level.text + "| " + logMessage);
    }

    public static void fatalCrash(String crashReason){
        log(crashReason, DebugInfoLevel.FATAL_ERROR);
        System.exit(-1);
    }

    public static String lastDebugMessage = "";
    public static void debugMessage(String message, boolean allowDuplicates){
        if (!allowDuplicates && lastDebugMessage.equals(message)){
            return;
        }
        log(message, DebugInfoLevel.DEBUG);
        lastDebugMessage = message;
    }
}
