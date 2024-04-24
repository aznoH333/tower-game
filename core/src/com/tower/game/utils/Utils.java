package com.tower.game.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class Utils {
    public static ArrayList<File> getAllFilesInFolder(String folderPath){
        ArrayList<File> output = new ArrayList<>();

        File folder = new File(folderPath);

        for (File f : Objects.requireNonNull(folder.listFiles())){
            if (f.isFile()){
                output.add(f);
            }else{
                output.addAll(getAllFilesInFolder(f.getPath()));
            }
        }

        return output;
    }

    public static String removeFileExtensionFromName(String fileName){
        return fileName.substring(0, fileName.indexOf('.'));
    }
}
