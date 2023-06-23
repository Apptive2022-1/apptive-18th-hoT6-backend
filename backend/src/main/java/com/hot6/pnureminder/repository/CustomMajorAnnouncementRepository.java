package com.hot6.pnureminder.repository;


import com.hot6.pnureminder.entity.Announcement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
    public class CustomMajorAnnouncementRepository {
        private final MongoTemplate mongoTemplate;
        public CustomMajorAnnouncementRepository(MongoTemplate mongoTemplate) {
            this.mongoTemplate = mongoTemplate;
        }
        public List<Announcement> findAllAnnouncementsByDepartment(String department) {
            return mongoTemplate.findAll(Announcement.class, department);
        }

    }
