package com.gutnik.dao;

import com.gutnik.dto.FieldCountDTO;

import java.util.List;

/**
 * Created by meirg
 */
public interface LogsDao {

    List<FieldCountDTO> getCountByField(String field);
}
