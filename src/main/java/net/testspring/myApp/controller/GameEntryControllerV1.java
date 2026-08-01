package net.testspring.myApp.controller;

import net.testspring.myApp.entity.GameEntry;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/_games")
public class GameEntryControllerV1 {

    public Map<ObjectId, GameEntry> gameEntries = new HashMap<>();

    /**
     * GET /_games/getall
     * Returns all in-memory game entries.
     */
    @GetMapping("/getall")
    public List<GameEntry> getAllGames() {
        return new ArrayList<>(gameEntries.values());
    }

    /**
     * POST /_games
     * Adds a new game entry to the in-memory map.
     */
    @PostMapping
    public void createGame(@RequestBody GameEntry gameEntry) {
        gameEntries.put(gameEntry.getId(), gameEntry);
    }
}
