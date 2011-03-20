package com.jwl.business.knowledge.scheduler;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jwl.business.Environment;

public class MergeSuggestionsCleanUpJob implements Job {

	public static final String jobName = "ArticleMergeSuggestionsCleanUp";

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Logger.getLogger(KeyWordGenerationJob.class.getName()).log(Level.INFO, "Periodic merge suggestions clean-up started");
		Environment.getKnowledgeFacade().cleanIgnoredMergeSuggestions();
		Logger.getLogger(KeyWordGenerationJob.class.getName()).log(Level.INFO, "Periodic merge suggestions clen-up finished");
	}

}
