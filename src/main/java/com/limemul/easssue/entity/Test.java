package com.limemul.easssue.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "test")
@Getter
@Setter
@NoArgsConstructor
public class Test {

    @Id
    private ObjectId id;

    private String title;

    private LocalDateTime pubDate;

    private List<String> kwds;
}
