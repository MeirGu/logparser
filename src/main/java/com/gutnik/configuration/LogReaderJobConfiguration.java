package com.gutnik.configuration;

import com.gutnik.dto.LogDTO;
import com.gutnik.utils.DelimitedLineTokenizerIgnoreEmpty;
import com.gutnik.utils.LogFieldSetMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created by meirg
 */
@Configuration
@EnableBatchProcessing
public class LogReaderJobConfiguration {

    private static final String OVERRIDDEN_BY_EXPRESSION = null;

    private static final String WHITESPACE = " ";

    @Value("${fields.raw.title}")
    private String FIELDS_RAW_TITLE;

    @Value("${lines.to.skip}")
    private Integer LINES_TO_SKIP;

    @Value("${number.of.chunks}")
    private Integer NUMBER_OF_CHUNKS;

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    public Job addNewLogReaderJob(DelimitedLineTokenizerIgnoreEmpty delimitedLineTokenizer){
        return jobs.get("addNewLogReaderJob")
                .flow(step(delimitedLineTokenizer))
                .end()
                .build();
    }

    @Bean
    public Step step(DelimitedLineTokenizerIgnoreEmpty delimitedLineTokenizer){
        return stepBuilderFactory.get("parse_and_persist")
                .<LogDTO,LogDTO>chunk(NUMBER_OF_CHUNKS)
                .reader(reader(OVERRIDDEN_BY_EXPRESSION,OVERRIDDEN_BY_EXPRESSION,delimitedLineTokenizer))
                .writer(writer())
                .build();
    }

    @Bean
    public MongoItemWriter<LogDTO> writer(){
        MongoItemWriter retVal = new MongoItemWriter<LogDTO>();
        retVal.setCollection("logs");
        retVal.setTemplate(mongoTemplate);

        return retVal;
    }

    @Bean
    @StepScope
    public FlatFileItemReader<LogDTO> reader(@Value("#{jobParameters[pathToFile] ?: '/'}") String pathToFile,
                                             @Value("#{jobParameters[delimiter] ?: ' '}") String delimiter,
                                             DelimitedLineTokenizerIgnoreEmpty delimitedLineTokenizer) {
        FlatFileItemReader<LogDTO> reader = new FlatFileItemReader<>();
        reader.setStrict(false);
        reader.setComments(new String[]{"##"});
        reader.setResource(new FileSystemResource(pathToFile));
        reader.setSkippedLinesCallback(line -> {
            if(line.startsWith(FIELDS_RAW_TITLE)){
                String[] columnNames = line.substring(line.lastIndexOf(FIELDS_RAW_TITLE)
                        +FIELDS_RAW_TITLE.length()+WHITESPACE.length()).split(delimiter);
                delimitedLineTokenizer.setNames(columnNames);
            }
        });
        reader.setLinesToSkip(LINES_TO_SKIP);
        DefaultLineMapper<LogDTO> logLineMapper = new DefaultLineMapper<>();
        logLineMapper.setLineTokenizer(delimitedLineTokenizer);
        FieldSetMapper<LogDTO> logFieldSetMapper = new LogFieldSetMapper();
        logLineMapper.setFieldSetMapper(logFieldSetMapper);
        reader.setLineMapper(logLineMapper);

        return reader;
    }

    @Bean
    @StepScope
    public DelimitedLineTokenizerIgnoreEmpty delimitedLineTokenizer(@Value("#{jobParameters[delimiter] ?: ' '}") String delimiter){
        DelimitedLineTokenizerIgnoreEmpty logLineTokenizer = new DelimitedLineTokenizerIgnoreEmpty();
        logLineTokenizer.setDelimiter(delimiter);
        logLineTokenizer.setStrict(false);

        return logLineTokenizer;
    }
}
