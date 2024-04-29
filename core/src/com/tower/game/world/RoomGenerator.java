package com.tower.game.world;

import com.tower.game.utils.DebugUtils;
import com.tower.game.utils.FileWrapper;
import com.tower.game.utils.Utils;

import java.io.File;
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

    private HashMap<FloorLevel, HashMap<RoomArchetype, ArrayList<RoomContents>>> levelLayoutMap;
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

        for (FileWrapper wrapper : loadedFiles){
            target.add(new RoomContents(Utils.parseCsv(wrapper.getContents(), ",")));
        }
    }

    public TowerRoom getRoom(FloorLevel level, RoomArchetype archetype){
        ArrayList<RoomContents> possibleLayout = levelLayoutMap.get(level).get(archetype);
        return new TowerRoom(possibleLayout.get(Utils.getRandomInRange(0, possibleLayout.size() - 1)));
    }

}
