package com.jwl.business.knowledge.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jwl.business.Environment;

public class KeyWordGenerationJob implements Job {

	public static final String jobName = "KeyWordGeneration";

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Environment.getKnowledgeFacade().extractKeyWords();
	}

}
