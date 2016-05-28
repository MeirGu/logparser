package com.gutnik.dto;

import lombok.Data;

/**
 * Created by meirg
 */
@Data
public class FieldCountDTO {
    private String date;
    private String time;
    private String timeTaken;
    private String cIp;
    private String csUsername;
    private String csAuthGroup;
    private String xExceptionId;
    private String scFilterResult;
    private String csCategories;
    private String csReferer;
    private String scStatus;
    private String sAction;
    private String csMethod;
    private String rsContentType;
    private String csUriScheme;
    private String csHost;
    private String csuriPort;
    private String csUriPath;
    private String csUriQuery;
    private String csUriExtension;
    private String csUserAgent;
    private String sIp;
    private String scBytes;
    private String csBytes;
    private String xVirusId;
    private Integer count;
}
