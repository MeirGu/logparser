package com.gutnik.dao.impl;


import com.gutnik.dao.LogsDao;
import com.gutnik.dto.FieldCountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by meirg
 */
@Repository
public class LogsDaoImpl implements LogsDao{

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<FieldCountDTO> getCountByField(String field) {
        Aggregation agg = newAggregation(
                group(field).count().as("count"),
                project("count").and(field).previousOperation(),
                sort(Sort.Direction.DESC, "count")
        );

        AggregationResults<FieldCountDTO> groupResults = mongoTemplate.aggregate(agg, "logs", FieldCountDTO.class);
        List<FieldCountDTO> result = groupResults.getMappedResults();

        return result;
    }
}
