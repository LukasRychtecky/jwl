package com.jwl.business.knowledge.scheduler;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

import com.jwl.business.Environment;
import com.jwl.business.knowledge.exceptions.KnowledgeManagementSettingsException;
import com.jwl.business.knowledge.util.ISettings;

public class ScheduledJobManager {
	ISettings settingsSource;
	private static final String jobGroup ="Knowledge Management";

	public ScheduledJobManager() {
	}

	public void schedule() {
		ISettings settings = Environment.getKnowledgeSettings();
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			attachKeyWordGeneration(scheduler, settings);
			attachLivabilityPeriodicDecrease(scheduler, settings);
			attachMergeSuggestionsGeneration(scheduler, settings);
			attachMergeSuggestionsClenUp(scheduler, settings);
		} catch (Exception e) {
			Logger.getLogger(ScheduledJobManager.class.getName()).log(Level.SEVERE, "could not set up scheduler");
		}
	}
	
	private void attachKeyWordGeneration(Scheduler scheduler,
			ISettings settings) {
		String cronExpression = null;
		try {
			cronExpression = settings
					.getCronExpression(KeyWordGenerationJob.jobName);
		} catch (KnowledgeManagementSettingsException e) {
			return;
		}
		try {
			CronTrigger trigger = new CronTrigger("Key word generation",
					"knowledge management");
			trigger.setCronExpression(cronExpression);
			JobDetail jobDetail = new JobDetail("Key word generation",
					jobGroup, KeyWordGenerationJob.class);
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
			Logger.getLogger(ScheduledJobManager.class.getName()).log(Level.SEVERE, "could not set up key word generation scheduling");
		}
	}
	
	private void attachLivabilityPeriodicDecrease(Scheduler scheduler,
			ISettings settings) {
		String cronExpression = null;
		try {
			cronExpression = settings
					.getCronExpression(LivabilityPeriodicReductionJob.jobName);
		} catch (KnowledgeManagementSettingsException e) {
			return;
		}
		try {
			CronTrigger trigger = new CronTrigger("LivabilityPeriodicReduction",
					"knowledge management");
			trigger.setCronExpression(cronExpression);
			JobDetail jobDetail = new JobDetail("LivabilityPeriodicReduction",
					jobGroup, LivabilityPeriodicReductionJob.class);
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
			Logger.getLogger(ScheduledJobManager.class.getName()).log(Level.SEVERE, "could not set up livability decrease scheduling");
		}
	}

	private void attachMergeSuggestionsGeneration(Scheduler scheduler,
			ISettings settings) {
		String cronExpression = null;
		try {
			cronExpression = settings
					.getCronExpression(MergeSuggestionsGenerationJob.jobName);
		} catch (KnowledgeManagementSettingsException e) {
			return;
		}
		try {
			CronTrigger trigger = new CronTrigger("MergeSuggestionsGeneration",
					"knowledge management");
			trigger.setCronExpression(cronExpression);
			JobDetail jobDetail = new JobDetail("MergeSuggestionsGeneration",
					jobGroup, MergeSuggestionsGenerationJob.class);
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
			Logger.getLogger(ScheduledJobManager.class.getName()).log(Level.SEVERE, "could not set up merge suggestions generation scheduling");
		}
	}

	private void attachMergeSuggestionsClenUp(Scheduler scheduler,
			ISettings settings) {
		String cronExpression = null;
		try {
			cronExpression = settings
					.getCronExpression(MergeSuggestionsCleanUpJob.jobName);
		} catch (KnowledgeManagementSettingsException e) {
			return;
		}
		try {
			CronTrigger trigger = new CronTrigger("MergeSuggestionsClenUp",
					"knowledge management");
			trigger.setCronExpression(cronExpression);
			JobDetail jobDetail = new JobDetail("MergeSuggestionsClenUp",
					jobGroup, MergeSuggestionsCleanUpJob.class);
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
			Logger.getLogger(ScheduledJobManager.class.getName()).log(Level.SEVERE, "could not set up merge suggestions clean up scheduling");
		}
	}

}
