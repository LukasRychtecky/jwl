package com.jwl.business.knowledge.scheduler;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jwl.business.Environment;

public class LivabilityPeriodicReductionJob implements Job {
	
	public static final String jobName = "LivabilityPeriodicReduction";
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Logger.getLogger(KeyWordGenerationJob.class.getName()).log(Level.INFO, "Periodic livability decrease started");
		Environment.getKnowledgeFacade().doLivabilityPeriodicReduction();
		Logger.getLogger(KeyWordGenerationJob.class.getName()).log(Level.INFO, "Periodic livability decrease finished");
	}

}
