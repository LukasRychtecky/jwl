package com.jwl.business.knowledge;

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

import com.jwl.business.knowledge.exceptions.KnowledgeManagementSettingsExceptiuon;

public class SettingsSource implements ISettingsSource {
	Map<String, NeuronRecord> neuronRecords;
	private static final String settingsFile = "/resources/knowledgemanagementsettings.xml";
	
	@Override
	public Map<String, WeightRecord> getWeights(String neuronName)
			throws KnowledgeManagementSettingsExceptiuon {
		checkRecordAvailability(neuronName);
		return neuronRecords.get(neuronName).getWeights();
	}

	@Override
	public float getTreshold(String neuronName)
			throws KnowledgeManagementSettingsExceptiuon {
		checkRecordAvailability(neuronName);
		return neuronRecords.get(neuronName).getThreshold();
	}

	@Override
	public float getWeight(String neuronName, String inputName)
			throws KnowledgeManagementSettingsExceptiuon {	
		checkRecordAvailability(neuronName);
		return neuronRecords.get(neuronName).getWeights().get(inputName)
				.getWeight();
	}
	
	private void checkRecordAvailability(String neuronName) throws KnowledgeManagementSettingsExceptiuon{
		if (neuronRecords == null) {
			fillNeuronRecords();
		}
		if (!neuronRecords.containsKey(neuronName)) {
			throw new KnowledgeManagementSettingsExceptiuon(
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

	private void fillNeuronRecords()
			throws KnowledgeManagementSettingsExceptiuon {
		Document doc;
		try {
			doc = setUpXmlParser();
		} catch (Exception e) {
			throw new KnowledgeManagementSettingsExceptiuon(
					"Knowledge settings file cound not be read.", e);
		}
		neuronRecords = new HashMap<String, NeuronRecord>();
		NodeList nodeList = doc.getElementsByTagName("feature");
		for (int i = 0; i < nodeList.getLength(); i++) {
			String featureName = null;
			Float featureThreshold = null;
			Map<String, WeightRecord> weights = new HashMap<String, WeightRecord>();
			Node featureNode = nodeList.item(i);
			NodeList featureChildren = featureNode.getChildNodes();
			for (int j = 0; i < featureChildren.getLength(); i++) {
				Node e = featureChildren.item(j);
				if (e.getNodeName().equals("name")) {
					featureName = e.getNodeValue();
					continue;
				}
				if (e.getNodeName().equals("threshold")) {
					featureThreshold = new Float(e.getNodeValue());
					continue;
				}
				if (e.getNodeName().equals("input")) {
					NodeList inputChildren = e.getChildNodes();
					for (int k = 0; k < inputChildren.getLength(); k++) {
						Node f = inputChildren.item(k);
						String inputName = null;
						Float inputWeight = null;
						if (f.getNodeName().equals("name")) {
							inputName = f.getNodeValue();
						}
						if (f.getNodeName().equals("weight")) {
							inputWeight = new Float(f.getNodeValue());
						}
						if (inputName != null && inputWeight != null) {
							WeightRecord wr = new WeightRecord(inputName,
									inputWeight.floatValue());
							weights.put(inputName, wr);
						}
					}
				}
				if (featureName != null && featureThreshold != null
						&& weights.size() != 0) {
					NeuronRecord nr = new NeuronRecord(featureName, weights,
							featureThreshold);
					neuronRecords.put(featureName, nr);
				}
			}
		}
	}
}
