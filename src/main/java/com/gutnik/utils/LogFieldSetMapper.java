package com.gutnik.utils;

import com.gutnik.dto.LogDTO;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

/**
 * Created by meirg
 */
public class LogFieldSetMapper implements FieldSetMapper<LogDTO> {
    @Override
    public LogDTO mapFieldSet(FieldSet fieldSet) throws BindException {
        LogDTO logDTO = new LogDTO();
        logDTO.setDate(fieldSet.readString("date"));
        logDTO.setTime(fieldSet.readString("time"));
        logDTO.setTimeTaken(fieldSet.readString("time-taken"));
        logDTO.setCIp(fieldSet.readString("c-ip"));
        logDTO.setCsUsername(fieldSet.readString("cs-username"));
        logDTO.setCsAuthGroup(fieldSet.readString("cs-auth-group"));
        logDTO.setXExceptionId(fieldSet.readString("x-exception-id"));
        logDTO.setScFilterResult(fieldSet.readString("sc-filter-result"));
        logDTO.setCsCategories(fieldSet.readString("cs-categories"));
        logDTO.setCsReferer(fieldSet.readString("cs(Referer)"));
        logDTO.setScStatus(fieldSet.readString("sc-status"));
        logDTO.setSAction(fieldSet.readString("s-action"));
        logDTO.setCsMethod(fieldSet.readString("cs-method"));
        logDTO.setCsUriScheme(fieldSet.readString("cs-uri-scheme"));
        logDTO.setCsHost(fieldSet.readString("cs-host"));
        logDTO.setCsuriPort(fieldSet.readString("cs-uri-port"));
        logDTO.setCsUriPath(fieldSet.readString("cs-uri-path"));
        logDTO.setCsUriQuery(fieldSet.readString("cs-uri-query"));
        logDTO.setCsUriExtension(fieldSet.readString("cs-uri-extension"));
        logDTO.setCsUserAgent(fieldSet.readString("cs(User-Agent)"));
        logDTO.setSIp(fieldSet.readString("s-ip"));
        logDTO.setScBytes(fieldSet.readString("sc-bytes"));
        logDTO.setXVirusId(fieldSet.readString("x-virus-id"));
        return logDTO;
    }
}