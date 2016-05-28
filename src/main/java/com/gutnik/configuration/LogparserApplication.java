package com.gutnik.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@PropertySource("classpath:application.properties")
@SpringBootApplication
@ComponentScan("com.gutnik")
@EnableScheduling
public class LogparserApplication implements SchedulingConfigurer{

	@Value("${upload.folder}")
	private String UPLOAD_FOLDER;

	@Value("${working.folder}")
	private String WORKING_FOLDER;

	@Value("${done.folder}")
	private String DONE_FOLDER;

	@Value("${thread.pool.size}")
	private Integer THREAD_POLL_SIZE;

	public static void main(String[] args) {
		SpringApplication.run(LogparserApplication.class, args);
	}

	@Bean(destroyMethod = "shutdown")
	public Executor taskScheduler() {
		return Executors.newScheduledThreadPool(THREAD_POLL_SIZE);
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(taskScheduler());
	}

	@Bean
	public CommandLineRunner initUpload() {
		return (String[] args) -> {
			new File(UPLOAD_FOLDER).mkdir();
		};
	}

	@Bean
	public CommandLineRunner initWorking() {
		return (String[] args) -> {
			new File(WORKING_FOLDER).mkdir();
		};
	}

	@Bean
	public CommandLineRunner initDone() {
		return (String[] args) -> {
			new File(DONE_FOLDER).mkdir();
		};
	}
}
