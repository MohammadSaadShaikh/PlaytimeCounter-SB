package net.testspring.myApp.controller;

import net.testspring.myApp.entity.GameEntry;
import net.testspring.myApp.service.GameEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/games")
public class GameEntryController {

    @Autowired
    private GameEntryService gameEntryService;

    /**
     * GET /games/getall
     * Returns all game entries stored in the database.
     */
    @GetMapping("/getall")
    public ResponseEntity<List<GameEntry>> getAllGames() {
        List<GameEntry> games = gameEntryService.getAll();
        if (games != null && !games.isEmpty()) {
            return new ResponseEntity<>(games, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * GET /games/id/{id}
     * Returns a single game entry by its MongoDB ObjectId.
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<GameEntry> getGameById(@PathVariable("id") ObjectId id) {
        Optional<GameEntry> gameEntry = gameEntryService.findById(id);
        if (gameEntry.isPresent()) {
            return new ResponseEntity<>(gameEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * POST /games
     * Creates a new game entry.
     *
     * Expected JSON body:
     * {
     *   "title":    "Elden Ring",
     *   "playtime": "120h 45m",
     *   "platform": "PC",
     *   "genre":    "Action RPG",
     *   "notes":    "Amazing open world. Finished all bosses."
     * }
     */
    @PostMapping
    public ResponseEntity<GameEntry> createGame(@RequestBody GameEntry gameEntry) {
        try {
            gameEntry.setDate(LocalDateTime.now());
            gameEntryService.saveEntry(gameEntry);
            return new ResponseEntity<>(gameEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * PUT /games/id/{id}
     * Updates an existing game entry. Only non-null, non-empty fields are updated.
     */
    @PutMapping("/id/{id}")
    public ResponseEntity<GameEntry> updateGame(
            @PathVariable("id") ObjectId id,
            @RequestBody GameEntry updatedEntry) {

        GameEntry existing = gameEntryService.findById(id).orElse(null);

        if (existing != null) {

            if (updatedEntry.getTitle() != null && !updatedEntry.getTitle().isEmpty()) {
                existing.setTitle(updatedEntry.getTitle());
            }

            if (updatedEntry.getPlaytime() != null && !updatedEntry.getPlaytime().isEmpty()) {
                existing.setPlaytime(updatedEntry.getPlaytime());
            }

            if (updatedEntry.getPlatform() != null && !updatedEntry.getPlatform().isEmpty()) {
                existing.setPlatform(updatedEntry.getPlatform());
            }

            if (updatedEntry.getGenre() != null && !updatedEntry.getGenre().isEmpty()) {
                existing.setGenre(updatedEntry.getGenre());
            }

            if (updatedEntry.getNotes() != null && !updatedEntry.getNotes().isEmpty()) {
                existing.setNotes(updatedEntry.getNotes());
            }

            existing.setDate(LocalDateTime.now());
            gameEntryService.saveEntry(existing);

            return new ResponseEntity<>(existing, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * DELETE /games/id/{id}
     * Deletes a game entry by ID.
     */
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable("id") ObjectId id) {
        if (gameEntryService.findById(id).isPresent()) {
            gameEntryService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
