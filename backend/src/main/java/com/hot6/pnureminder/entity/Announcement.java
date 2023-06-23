package com.hot6.pnureminder.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.annotation.Id;


@Data
@NoArgsConstructor
//컬렉션 이름 고정하지 않기
@Document
public class Announcement {

    @Id
    private String id;

    @Field(name = "title")
    private String title;

    @Field(name = "urls")
    private String urls;

    @Field(name = "date")
    private String date;
}
