package com.jwl.business.knowledge.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.jwl.business.knowledge.exceptions.KnowledgeManagementSettingsException;

public class SettingsSource implements ISettingsSource {
	Map<String, FeatureRecord> neuronRecords;
	Map<String, String> schedulerRecords;
	private String settingsFile;
	private String wordCountsFile;
	private String mergeFile;
	private String mergeIgnoreFile;
	
	public SettingsSource(String jwlHomePath){
		String pathSeparator = System.getProperty("file.separator");
		String settingFilePath = pathSeparator+"private"+pathSeparator+"jwl"+pathSeparator;
		settingsFile = jwlHomePath + settingFilePath+"KnowledgeManagementSettings.xml";
		wordCountsFile = jwlHomePath + settingFilePath+"WordCounts";
		mergeFile = jwlHomePath + settingFilePath+"Merge";
		mergeIgnoreFile = jwlHomePath + settingFilePath+"MergeIgnore";
	}
		
	@Override
	public Map<String, WeightRecord> getWeights(String featureName)
			throws KnowledgeManagementSettingsException {
		checkRecordAvailability(featureName);
		return neuronRecords.get(featureName).getWeights();
	}

	@Override
	public float getThreshold(String featureName)
			throws KnowledgeManagementSettingsException {
		checkRecordAvailability(featureName);
		return neuronRecords.get(featureName).getThreshold();
	}

	@Override
	public float getWeight(String featureName, String inputName)
			throws KnowledgeManagementSettingsException {
		checkRecordAvailability(featureName);
		return neuronRecords.get(featureName).getWeights().get(inputName)
				.getWeight();
	}

	private void checkRecordAvailability(String neuronName)
			throws KnowledgeManagementSettingsException {
		if (neuronRecords == null) {
			fillFeatureRecords();
		}
		if (!neuronRecords.containsKey(neuronName)) {
			throw new KnowledgeManagementSettingsException(
					"Unknown neuron name");
		}
	}

	private Document setUpXmlParser() throws ParserConfigurationException,
			SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(false);
		DocumentBuilder builder = dbf.newDocumentBuilder();
		Document doc = builder.parse(settingsFile);
		return doc;
	}

	private void fillFeatureRecords()
			throws KnowledgeManagementSettingsException {
		Document doc;
		try {
			doc = setUpXmlParser();
		} catch (Exception e) {
			throw new KnowledgeManagementSettingsException(
					"Knowledge settings file cound not be read.", e);
		}
		neuronRecords = new HashMap<String, FeatureRecord>();
		NodeList nodeList = doc.getElementsByTagName("feature");
		for (int i = 0; i < nodeList.getLength(); i++) {
			String featureName = null;
			float featureThreshold = 0;
			Map<String, WeightRecord> weights = new HashMap<String, WeightRecord>();
			Node featureNode = nodeList.item(i);
			NodeList featureChildren = featureNode.getChildNodes();
			for (int j = 0; j < featureChildren.getLength(); j++) {
				Node e = featureChildren.item(j);
				if (e.getNodeName().equals("name")) {
					featureName = e.getTextContent().trim();
					continue;
				}
				if (e.getNodeName().equals("threshold")) {
					featureThreshold = new Float(e.getTextContent().trim());
					continue;
				}
				if (e.getNodeName().equals("input")) {
					NodeList inputChildren = e.getChildNodes();
					String inputName = null;
					Float inputWeight = null;
					for (int k = 0; k < inputChildren.getLength(); k++) {
						Node f = inputChildren.item(k);
						if (f.getNodeName().equals("name")) {
							inputName = f.getTextContent().trim();
						}
						if (f.getNodeName().equals("weight")) {
							inputWeight = new Float(f.getTextContent().trim());
						}
					}
					WeightRecord wr = new WeightRecord(inputName,
							inputWeight.floatValue());
					weights.put(inputName, wr);
				}
			}
			FeatureRecord nr = new FeatureRecord(featureName, weights,
					featureThreshold);
			neuronRecords.put(featureName, nr);
		}
	}

	@Override
	public String getCronExpression(String taskName)
			throws KnowledgeManagementSettingsException {
		if (schedulerRecords == null) {
			fillSchedulerRecords();
		}
		if (!schedulerRecords.containsKey(taskName)) {
			throw new KnowledgeManagementSettingsException(
					"Unknown neuron name");
		}
		return schedulerRecords.get(taskName);
	}

	private void fillSchedulerRecords()
			throws KnowledgeManagementSettingsException {
		Document doc;
		try {
			doc = setUpXmlParser();
		} catch (Exception e) {
			throw new KnowledgeManagementSettingsException(
					"Knowledge settings file cound not be read.", e);
		}
		schedulerRecords = new HashMap<String, String>();
		NodeList nodeList = doc.getElementsByTagName("scheduledJob");
		for (int i = 0; i < nodeList.getLength(); i++) {
			String jobName = null;
			String cronExpression = null;
			Node featureNode = nodeList.item(i);
			NodeList scheduledJobChildren = featureNode.getChildNodes();
			for (int j = 0; j < scheduledJobChildren.getLength(); j++) {
				Node e = scheduledJobChildren.item(j);
				if (e.getNodeName().equals("name")) {
					jobName = e.getTextContent().trim();
					continue;
				}
				if (e.getNodeName().equals("cronExpression")) {
					cronExpression = e.getTextContent().trim();
					continue;
				}
			}
			cronExpression = "0 "+cronExpression;
			schedulerRecords.put(jobName, cronExpression);
		}
	}

	@Override
	public String getWordCountsFile(){
		return wordCountsFile;
	}

	@Override
	public String getMergeFile(){
		return mergeFile;
	}

	@Override
	public String getMergeIgnoreFile(){
		return mergeIgnoreFile;
	}
	
}
