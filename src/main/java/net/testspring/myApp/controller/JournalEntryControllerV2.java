package net.testspring.myApp.controller;

import net.testspring.myApp.entity.JournalEntry;
import net.testspring.myApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping("/getall")
    public ResponseEntity<?> getAll() {
        List<JournalEntry> all= journalEntryService.getAll();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/id/{my_id}")
    public ResponseEntity<JournalEntry> getJournalById(
            @PathVariable("my_id") ObjectId myId) {

        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);

        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{my_id}")
    public ResponseEntity<Void> deleteJournalEntry(
            @PathVariable("my_id") ObjectId myId) {

        if (journalEntryService.findById(myId).isPresent()) {
            journalEntryService.deleteByid(myId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(
            @RequestBody JournalEntry myEntry) {

        try {
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry);

            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/id/{my_id}")
    public ResponseEntity<JournalEntry> updateEntry(
            @PathVariable("my_id") ObjectId myId,
            @RequestBody JournalEntry newEntry) {

        JournalEntry old = journalEntryService.findById(myId).orElse(null);

        if (old != null) {

            old.setTitle(
                    newEntry.getTitle() != null &&
                            !newEntry.getTitle().isEmpty()
                            ? newEntry.getTitle()
                            : old.getTitle()
            );

            old.setPlaytime(
                    newEntry.getPlaytime() != null &&
                            !newEntry.getPlaytime().isEmpty()
                            ? newEntry.getPlaytime()
                            : old.getPlaytime()
            );

            journalEntryService.saveEntry(old);

            return new ResponseEntity<>(old, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
