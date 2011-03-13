package com.jwl.business.knowledge.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jwl.business.Environment;

public class MergeSuggestionsCleanUpJob implements Job {

	public static final String jobName = "ArticleMergeSuggestionsCleanUp";

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Environment.getKnowledgeFacade().cleanIgnoredMergeSuggestions();
	}

}
