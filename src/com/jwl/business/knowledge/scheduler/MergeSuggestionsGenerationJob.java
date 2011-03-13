package com.jwl.business.knowledge.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jwl.business.Environment;

public class MergeSuggestionsGenerationJob implements Job {

	public static final String jobName = "ArticleMergeSuggestionsGeneration";

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Environment.getKnowledgeFacade().pregenerateMergeSuggestion();
	}

}
