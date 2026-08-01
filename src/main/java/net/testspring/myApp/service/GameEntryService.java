package net.testspring.myApp.service;

import net.testspring.myApp.entity.GameEntry;
import net.testspring.myApp.repository.GameEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GameEntryService {

    @Autowired
    private GameEntryRepository gameEntryRepository;

    public void saveEntry(GameEntry gameEntry) {
        gameEntryRepository.save(gameEntry);
    }

    public List<GameEntry> getAll() {
        return gameEntryRepository.findAll();
    }

    public Optional<GameEntry> findById(ObjectId id) {
        return gameEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id) {
        gameEntryRepository.deleteById(id);
    }
}
