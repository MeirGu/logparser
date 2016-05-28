package com.gutnik.controller;

import com.gutnik.constants.WebPage;
import com.gutnik.service.LogsService;
import com.gutnik.service.TextService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by meirg
 */

@Controller
@RequestMapping("/log")
public class LogParserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogParserController.class);

    @Value("${upload.folder}")
    private String UPLOAD_FOLDER;

    @Autowired
    private LogsService logsService;

    @Autowired
    private TextService textService;

    @RequestMapping(value = "/load", method = RequestMethod.GET)
    public String load(){
        return WebPage.UPLOAD;
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public String upload(Map<String, Object> model, @RequestParam("file") MultipartFile file){
        String fileName = null;
        if (!file.isEmpty()) {
            try {
                fileName = file.getOriginalFilename();
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(new File(UPLOAD_FOLDER + File.separator + fileName)));
                FileCopyUtils.copy(file.getInputStream(), stream);
                stream.close();
                model.put("uploaded", true);
                return WebPage.UPLOAD;
            } catch (Exception e) {
                model.put("status", 500);
                model.put("error", "You failed to upload " + fileName + ": " + e.getMessage());
                return WebPage.ERROR;
            }
        } else {
            model.put("status", 500);
            model.put("error", "Unable to upload. File is empty.");
            return WebPage.ERROR;
        }
    }

    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public String report(Map<String, Object> model){
        model.put("search", "cs-host");
        return WebPage.REPORT;
    }

    @RequestMapping(value = "/count", method = RequestMethod.POST)
    public String count(Map<String, Object> model, String field){
        List countList = logsService.getCountForField(textService.toCamelCase(field));
        model.put("search", field);
        model.put("values", countList);
        return WebPage.REPORT;
    }
}
