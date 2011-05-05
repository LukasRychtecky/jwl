package com.jwl.business.knowledge.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.jwl.business.knowledge.exceptions.KnowledgeManagementSettingsException;

public class SettingsSource implements ISettings {
	Map<String, FeatureRecord> neuronRecords;
	Map<String, String> schedulerRecords;
	private String settingsFile;
	private String wordCountsFile;
	private String mergeFile;
	private String mergeIgnoreFile;
	private Integer keyWordNumber;
	private Boolean usePorterStamer;
	private Map<String, String> parameterRecords;
	private static int DEFAUL_KEY_WORD_NUMBER = 15;
	private String stopWordSetPath;
	private String jwlHome;

	public SettingsSource(String jwlHomePath) {
		String pathSeparator = System.getProperty("file.separator");
		String settingFilePath = pathSeparator + "private" + pathSeparator
				+ "jwl" + pathSeparator;
		settingsFile = jwlHomePath + settingFilePath
				+ "KnowledgeManagementSettings.xml";
		wordCountsFile = jwlHomePath + settingFilePath + "WordCounts";
		mergeFile = jwlHomePath + settingFilePath + "Merge";
		mergeIgnoreFile = jwlHomePath + settingFilePath + "MergeIgnore";
		keyWordNumber = null;
		usePorterStamer = null;
		stopWordSetPath = null;
		jwlHome=jwlHomePath;
	}

	@Override
	public Map<String, WeightRecord> getWeights(String featureName)
			throws KnowledgeManagementSettingsException{
		checkRecordAvailability(featureName);
		return neuronRecords.get(featureName).getWeights();
	}

	@Override
	public float getThreshold(String featureName)
			throws KnowledgeManagementSettingsException{
		checkRecordAvailability(featureName);
		return neuronRecords.get(featureName).getThreshold();
	}

	@Override
	public float getWeight(String featureName, String inputName)
			throws KnowledgeManagementSettingsException{
		checkRecordAvailability(featureName);
		return neuronRecords.get(featureName).getWeights().get(inputName)
				.getWeight();
	}

	private void checkRecordAvailability(String neuronName)
			throws KnowledgeManagementSettingsException{
		if(neuronRecords == null){
			fillFeatureRecords();
		}
		if(!neuronRecords.containsKey(neuronName)){
			throw new KnowledgeManagementSettingsException(
					"Unknown neuron name");
		}
	}

	private Document setUpXmlParser() throws ParserConfigurationException,
			SAXException, IOException{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(false);
		DocumentBuilder builder = dbf.newDocumentBuilder();
		Document doc = builder.parse(settingsFile);
		return doc;
	}

	private void fillFeatureRecords()
			throws KnowledgeManagementSettingsException{
		Document doc;
		try{
			doc = setUpXmlParser();
		}catch(Exception e){
			throw new KnowledgeManagementSettingsException(
					"Knowledge settings file cound not be read.", e);
		}
		neuronRecords = new HashMap<String, FeatureRecord>();
		NodeList nodeList = doc.getElementsByTagName("feature");
		for (int i = 0; i < nodeList.getLength(); i++){
			String featureName = null;
			float featureThreshold = 0;
			Map<String, WeightRecord> weights = new HashMap<String, WeightRecord>();
			Node featureNode = nodeList.item(i);
			NamedNodeMap featureAttributes = featureNode.getAttributes();
			for (int j = 0; j < featureAttributes.getLength(); j++){
				Node attribute = featureAttributes.item(j);
				if(attribute.getNodeName().equals("name")){
					featureName = attribute.getTextContent().trim();
					continue;
				}
				if(attribute.getNodeName().equals("threshold")){
					featureThreshold = new Float(attribute.getTextContent()
							.trim());
					continue;
				}
			}
			NodeList featureChildren = featureNode.getChildNodes();
			for (int j = 0; j < featureChildren.getLength(); j++){
				Node inputNode = featureChildren.item(j);
				NamedNodeMap inputAttributes = inputNode.getAttributes();
				if(inputAttributes==null){
					continue;
				}
				String inputName = null;
				Float inputWeight = null;				
				for (int k = 0; k < inputAttributes.getLength(); k++){
					Node attribute = inputAttributes.item(k);
					if(attribute.getNodeName().equals("name")){
						inputName = attribute.getTextContent().trim();
					}
					if(attribute.getNodeName().equals("value")){
						inputWeight = new Float(attribute.getTextContent().trim());
					}
				}			
				WeightRecord wr = new WeightRecord(inputName,
						inputWeight.floatValue());
				weights.put(inputName, wr);
			}
			FeatureRecord nr = new FeatureRecord(featureName, weights,
					featureThreshold);
			neuronRecords.put(featureName, nr);
		}
	}

	@Override
	public String getCronExpression(String taskName)
			throws KnowledgeManagementSettingsException{
		if(schedulerRecords == null){
			fillSchedulerRecords();
		}
		if(!schedulerRecords.containsKey(taskName)){
			throw new KnowledgeManagementSettingsException(
					"Unknown neuron name");
		}
		return schedulerRecords.get(taskName);
	}

	private void fillSchedulerRecords()
			throws KnowledgeManagementSettingsException{
		Document doc;
		try{
			doc = setUpXmlParser();
		}catch(Exception e){
			throw new KnowledgeManagementSettingsException(
					"Knowledge settings file cound not be read.", e);
		}
		schedulerRecords = new HashMap<String, String>();
		NodeList nodeList = doc.getElementsByTagName("scheduledJob");
		for (int i = 0; i < nodeList.getLength(); i++){
			String jobName = null;
			String cronExpression = null;
			Node jobNode = nodeList.item(i);
			NamedNodeMap attributes = jobNode.getAttributes();
			for (int j = 0; j < attributes.getLength(); j++){
				Node attribute = attributes.item(j);
				if(attribute.getNodeName().equals("name")){
					jobName = attribute.getNodeValue().trim();
					continue;
				}
				if(attribute.getNodeName().equals("cronExpression")){
					cronExpression = attribute.getNodeValue().trim();
					continue;
				}
			}
			cronExpression = "0 " + cronExpression;
			schedulerRecords.put(jobName, cronExpression);
		}
	}

	private void fillParameterRecords()
			throws KnowledgeManagementSettingsException{
		Document doc;
		try{
			doc = setUpXmlParser();
		}catch(Exception e){
			throw new KnowledgeManagementSettingsException(
					"Knowledge settings file cound not be read.", e);
		}
		parameterRecords = new HashMap<String, String>();
		NodeList nodeList = doc.getElementsByTagName("parameter");
		for (int i = 0; i < nodeList.getLength(); i++){
			Node parameterNode = nodeList.item(i);
			NamedNodeMap attributes = parameterNode.getAttributes();
			String paramName = null;
			String paramValue = null;
			for (int j = 0; j < attributes.getLength(); j++){
				Node attribute = attributes.item(j);

				if(attribute.getNodeName().equals("name")){
					paramName = attribute.getNodeValue().trim();
					continue;
				}
				if(attribute.getNodeName().equals("value")){
					paramValue = attribute.getNodeValue().trim();
					continue;
				}
			}
			parameterRecords.put(paramName, paramValue);
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

	@Override
	public int getKeyWordNumber(){
		if(keyWordNumber != null){
			return keyWordNumber.intValue();
		}
		if(parameterRecords == null){
			try{
				fillParameterRecords();
			}catch(KnowledgeManagementSettingsException e){
				keyWordNumber = DEFAUL_KEY_WORD_NUMBER;
			}
		}
		String numberStr = parameterRecords.get("keyWordNumber");
		if(numberStr == null){
			keyWordNumber = DEFAUL_KEY_WORD_NUMBER;
		}
		keyWordNumber = Integer.parseInt(numberStr);
		return keyWordNumber;
	}

	@Override
	public boolean getUsePorterStamer(){
		if(usePorterStamer != null){
			return usePorterStamer.booleanValue();
		}
		if(parameterRecords == null){
			try{
				fillParameterRecords();
			}catch(KnowledgeManagementSettingsException e){
				usePorterStamer = false;
			}
		}
		String valueStr = parameterRecords.get("porterStemmer");
		if(valueStr == null){
			usePorterStamer = false;
		}
		usePorterStamer = Boolean.parseBoolean(valueStr);
		return usePorterStamer;
	}

	@Override
	public String getStopWordSetPath(){
		if(stopWordSetPath==null){
			if(parameterRecords == null){
				try{
					fillParameterRecords();
				}catch(KnowledgeManagementSettingsException e){
					stopWordSetPath="";
				}
			}
			String valueStr = parameterRecords.get("stopWordsSet");
			if(valueStr==null){
				stopWordSetPath="";
			}else{
				String pathSeparator = System.getProperty("file.separator");
				stopWordSetPath=jwlHome+pathSeparator+"private"+pathSeparator+"jwl"+pathSeparator+"stopWordsSets"+pathSeparator+valueStr;
			}
		}
		return stopWordSetPath;
	}

}
