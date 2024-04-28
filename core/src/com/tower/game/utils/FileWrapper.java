package com.tower.game.utils;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileWrapper {
    private File baseFile;
    private ArrayList<String> contents;
    public FileWrapper(File baseFile){
        this.baseFile = baseFile;
        loadContentsFromBaseFile();
    }
    private void loadContentsFromBaseFile(){

        contents = new ArrayList<>();

        try {
            Scanner sc = new Scanner(baseFile);
            while (sc.hasNextLine()){
                contents.add(sc.nextLine());
            }
        }catch (Exception e){
            DebugUtils.fatalCrash("couldn't read file " + e.getMessage());
        }
    }

    public ArrayList<String> getContents(){
        return contents;
    }

    public void saveFile(){
        try {
            FileWriter writer = new FileWriter(baseFile);

            writer.flush();

            for (String s : contents){
                writer.write(s);
            }

            writer.close();
        }catch (Exception e){
            DebugUtils.fatalCrash("couldn't write to file " + e.getMessage());
        }
    }

}
