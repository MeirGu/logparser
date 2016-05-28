package com.gutnik.service.impl;

import com.gutnik.dao.LogsDao;
import com.gutnik.dto.FieldCountDTO;
import com.gutnik.service.LogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by meirg
 */
@Service
public class LogsServiceImpl implements LogsService {

    @Autowired
    private LogsDao logsDao;

    @Override
    public List<FieldCountDTO> getCountForField(String field) {
        return logsDao.getCountByField(field);
    }
}
