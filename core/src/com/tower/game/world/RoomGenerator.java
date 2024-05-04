package com.tower.game.world;

import com.tower.game.utils.DebugUtils;
import com.tower.game.utils.FileWrapper;
import com.tower.game.utils.Utils;
import com.tower.game.world.enums.FloorLevel;
import com.tower.game.world.enums.RoomArchetype;

import java.util.ArrayList;
import java.util.HashMap;

public class RoomGenerator {
    private static RoomGenerator instance;
    public static RoomGenerator getInstance(){
        if (instance == null){
            instance = new RoomGenerator();
        }
        return instance;
    }

    private final HashMap<FloorLevel, HashMap<RoomArchetype, ArrayList<RoomContents>>> levelLayoutMap;
    public RoomGenerator(){
        // load rooms
        levelLayoutMap = new HashMap<>();

        for (FloorLevel level : FloorLevel.values()){
            loadFloorLevel(level);
        }
    }

    private void loadFloorLevel(FloorLevel level){
        levelLayoutMap.put(level, new HashMap<>());

        for (RoomArchetype archetype : RoomArchetype.values()){
            levelLayoutMap.get(level).put(archetype, new ArrayList<>());

            loadRoomsForArchetype(level, archetype);
        }
    }

    private void loadRoomsForArchetype(FloorLevel level, RoomArchetype archetype){
        ArrayList<RoomContents> target = levelLayoutMap.get(level).get(archetype);
        ArrayList<FileWrapper> loadedFiles = Utils.getAllFilesInFolderWrapped("./assets/gamedata/floorLayoutData/" + level.layoutFileFolderName + "/" + archetype.layoutFolderName);
        HashMap<String, LevelGenerationFileCollection> temp = new HashMap<>();

        // create collection
        for (FileWrapper wrapper : loadedFiles){
            String targetName = wrapper.getFileName().substring(0, wrapper.getFileName().lastIndexOf("_"));
            if (!temp.containsKey(targetName)){
                temp.put(targetName, new LevelGenerationFileCollection());
            }
            if (wrapper.getFileName().endsWith("tiles.csv")){
                temp.get(targetName).layoutFile = wrapper;
            }else if (wrapper.getFileName().endsWith("objects.csv")){
                temp.get(targetName).entityFile = wrapper;
            }
        }

        // create rooms from loaded files
        for (LevelGenerationFileCollection collection : temp.values()){
            target.add(collection.initializeRoomContents());
        }
    }

    public TowerRoom getRoom(FloorLevel level, RoomArchetype archetype){
        ArrayList<RoomContents> possibleLayout = levelLayoutMap.get(level).get(archetype);
        return new TowerRoom(possibleLayout.get(Utils.getRandomInRange(0, possibleLayout.size() - 1)), RoomArchetype.NORMAL);
    }

    private static class LevelGenerationFileCollection{
        public FileWrapper layoutFile = null;
        public FileWrapper entityFile = null;

        private LevelGenerationFileCollection(){
            // private constructor
        }
        public RoomContents initializeRoomContents(){
            try {
                return new RoomContents(Utils.parseCsv(layoutFile.getContents(), ","), Utils.parseCsv(entityFile.getContents(), ","));
            }catch (Exception e){
                DebugUtils.fatalCrash("Level loading failed " + e.getMessage());
            }
            return null;
        }

    }

}
