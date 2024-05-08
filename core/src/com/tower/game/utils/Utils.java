package com.tower.game.utils;

import com.badlogic.gdx.Game;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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

    public static ArrayList<FileWrapper> getAllFilesInFolderWrapped(String folderPath){
        ArrayList<File> temp = getAllFilesInFolder(folderPath);
        ArrayList<FileWrapper> output = new ArrayList<>();

        for (File f : temp){
            output.add(new FileWrapper(f));
        }

        return output;
    }

    public static String removeFileExtensionFromName(String fileName){
        return fileName.substring(0, fileName.indexOf('.'));
    }

    public static ArrayList<ArrayList<String>> parseCsv(ArrayList<String> fileContents, String separator){
        ArrayList<ArrayList<String>> output = new ArrayList<>();

        for (String line : fileContents){
            ArrayList<String> temp = new ArrayList<>(Arrays.asList(line.split(separator)));
            output.add(temp);
        }

        return output;
    }

    public static int getRandomInRange(int min, int max){
        return (int)(Math.random() * ((max + 1) - min)) + min;
    }



    public static float lerp(float start, float end, float value){
        return ((end - start) * value) + start;
    }

    /**
     * 1 world unit = 16 screen units
     * @param screenUnits amount to convert
     * @return result
     */
    public static int convertWorldToTile(float screenUnits){
        return (int)(screenUnits / GameConstants.TILE_SIZE);
    }

    public static float pythagoras(float x1, float y1, float x2, float y2){
        return (float) Math.sqrt(Math.pow(Math.abs(x1 - x2), 2) +  Math.pow(Math.abs(y1 - y2), 2));
    }

}
