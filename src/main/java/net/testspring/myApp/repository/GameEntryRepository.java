package net.testspring.myApp.repository;

import net.testspring.myApp.entity.GameEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameEntryRepository extends MongoRepository<GameEntry, ObjectId> {
}
