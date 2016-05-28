package com.gutnik.task.impl;

import com.gutnik.configuration.LogparserApplication;
import com.gutnik.task.NewLogChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by meirg
 */

@Component
public class NewLogCheckerImpl implements NewLogChecker{

    private static final Logger LOGGER = LoggerFactory.getLogger(NewLogCheckerImpl.class);

    @Value("${upload.folder}")
    private String UPLOAD_FOLDER;

    @Value("${working.folder}")
    private String WORKING_FOLDER;

    @Value("${done.folder}")
    private String DONE_FOLDER;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Override
    @Async
    @Scheduled(fixedRate = 10000)
    public void checkForNewLog() {
        File rootFolder = new File(UPLOAD_FOLDER);
        List<String> fileNames = Arrays.stream(rootFolder.listFiles())
                .map(File::getName)
                .collect(Collectors.toList());
        LOGGER.info("Upload folder contains: " + fileNames.toString());
        if(rootFolder.listFiles().length > 0) {
            try {
                String dateParam = new Date().toString();
                String filePath = moveFileToFolder(rootFolder.listFiles()[0], WORKING_FOLDER);
                JobParameters param =
                        new JobParametersBuilder()
                                .addString("date", dateParam)
                                .addString("pathToFile", filePath)
                                .addString("delimiter", calculateDelimiter(filePath))
                                .toJobParameters();

                JobExecution execution = jobLauncher.run(job, param);
                BatchStatus batchStatus = execution.getStatus();
                while (batchStatus.isRunning()) {
                    LOGGER.info("Parsing new log file....");
                    Thread.sleep(10);
                }
                LOGGER.info(String.format("%s parse finished with status: %s", filePath,
                        execution.getExitStatus().getExitCode()));
                moveFileToFolder(new File(filePath), DONE_FOLDER);
            } catch (Exception e) {
                LOGGER.error("Error", e);
            }
        }
    }

    private String calculateDelimiter(String filePath) {
        String retVal = "";
        if(filePath.toLowerCase().endsWith("log")){
            retVal = " ";
        }
        else if (filePath.toLowerCase().endsWith("csv")){
            retVal = DelimitedLineTokenizer.DELIMITER_COMMA;
        }
        else if (filePath.toLowerCase().endsWith("tsv")){
            retVal = DelimitedLineTokenizer.DELIMITER_TAB;
        }
        else{
            //TODO: add more delimiters
        }

        return retVal;
    }

    private String moveFileToFolder(File file, String folder) throws IOException{
        return Files.move(FileSystems.getDefault().getPath(file.getAbsolutePath()),
                FileSystems.getDefault().getPath(new File(folder+File.separator+file.getName()).getAbsolutePath()),
                REPLACE_EXISTING).toAbsolutePath().toString();
    }
}
