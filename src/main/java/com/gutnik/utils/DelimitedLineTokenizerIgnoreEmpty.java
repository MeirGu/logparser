package com.gutnik.utils;

import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by meirg
 */
public class DelimitedLineTokenizerIgnoreEmpty extends DelimitedLineTokenizer {

    @Override
    protected List<String> doTokenize(String line) {
        List<String> retVal = new ArrayList<>();
        List<String> superResult = super.doTokenize(line);
        retVal.addAll(superResult.stream().filter(token -> !token.isEmpty()).collect(Collectors.toList()));
        return retVal;
    }
}
