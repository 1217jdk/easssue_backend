package com.limemul.easssue.mongorepo;

import com.limemul.easssue.entity.Test;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface TestRepo extends MongoRepository<Test, ObjectId> {

    Slice<Test> findByKwdsNotIn(Collection<List<String>> kwds, Pageable pageable);
    Slice<Test> findBy(Pageable pageable);
    Slice<Test> findByPubDateAfter(LocalDateTime pubDate,Pageable pageable);
}
