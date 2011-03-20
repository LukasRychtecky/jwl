package com.jwl.business.knowledge.scheduler;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jwl.business.Environment;

public class KeyWordGenerationJob implements Job {

	public static final String jobName = "KeyWordGeneration";

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Logger.getLogger(KeyWordGenerationJob.class.getName()).log(Level.INFO, "Periodic key word generation started");
		Environment.getKnowledgeFacade().extractKeyWords();
		Logger.getLogger(KeyWordGenerationJob.class.getName()).log(Level.INFO, "Periodic key word generation finished");
	}

}
