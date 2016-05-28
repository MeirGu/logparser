package com.gutnik.service;

import com.gutnik.dto.FieldCountDTO;

import java.util.List;

/**
 * Created by meirg
 */
public interface LogsService {
    List<FieldCountDTO> getCountForField(String field);
}
