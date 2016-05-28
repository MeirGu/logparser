package com.gutnik.service.impl;

import com.gutnik.service.TextService;
import org.springframework.stereotype.Service;

/**
 * Created by meirg
 */
@Service
public class TextServiceImpl implements TextService {

    @Override
    public String toCamelCase(String text) {
        text = text.replace(")","").replace("(","-");
        String[] parts = text.split("-");
        String camelCaseString = "";
        for (int i=1; i<parts.length; i++){
            camelCaseString = camelCaseString + toProperCase(parts[i]);
        }
        return parts[0].toLowerCase()+camelCaseString;
    }

    private String toProperCase(String s) {
        return s.substring(0, 1).toUpperCase() +
                s.substring(1).toLowerCase();
    }
}
