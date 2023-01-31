package com.limemul.easssue.mongorepo;

import com.limemul.easssue.entity.Test;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;

public interface TestRepo extends MongoRepository<Test, ObjectId> {

    Slice<Test> findByKwdsNotIn(Collection<List<String>> kwds, Pageable pageable);
}
