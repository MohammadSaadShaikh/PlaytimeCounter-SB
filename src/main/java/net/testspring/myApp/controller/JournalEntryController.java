package net.testspring.myApp.controller;

import net.testspring.myApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/_journal")
public class JournalEntryController {
    public Map<ObjectId, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping("/getall")
    public List<JournalEntry> getAll(){
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public void createEntry(@RequestBody JournalEntry myEntry){
        journalEntries.put(myEntry.getId() , myEntry);
    }
}
