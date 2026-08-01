package net.testspring.myApp.service;

import net.testspring.myApp.entity.JournalEntry;
import net.testspring.myApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository JournalEntryRepository;

    public void saveEntry(JournalEntry JournalEntry){
        JournalEntryRepository.save(JournalEntry);
    }
    public List<JournalEntry> getAll(){
        return JournalEntryRepository.findAll();
    }
    public Optional<JournalEntry> findById(ObjectId id){
        return JournalEntryRepository.findById(id);
    }
    public void deleteByid(ObjectId id){
        JournalEntryRepository.deleteById(id);
    }
}

//controller -> service -> repository
