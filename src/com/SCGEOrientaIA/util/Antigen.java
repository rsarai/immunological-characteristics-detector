package com.SCGEOrientaIA.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import com.SCGEOrientaIA.dao.util.IOManipulation;
import com.SCGEOrientaIA.helper.PreProcess;
import com.SCGEOrientaIA.programSettings.ProjectProperties;

/*
 * Nesse projeto usaremos os antigenos como perguntas.
 * Cada resposta tem uma descrição, um assunto e suas keyWords
 */

public class Antigen {
	private List<Keyword> keywords;
	private String description;
	
	public Antigen(){
		this.setKeywords(new ArrayList<Keyword>());
		Properties projectProperties = ProjectProperties.getProperties();
		List<String> questionsDictionary = IOManipulation.readFileContent(projectProperties.getProperty("questionsDictionaryFile"));
		int numberOfQuestionsInDictionary = questionsDictionary.size();
		
		Random index = new Random();
		String question = "";
		
		int nextInt = index.nextInt(numberOfQuestionsInDictionary-1);
		question = questionsDictionary.get(nextInt);
		this.description = question;
		String[] cleanQuestion = PreProcess.cleanQuestion(question).split(" ");
	
		for(String s :cleanQuestion){
			Keyword word = new Keyword(); 
			word.setKeyword(s.trim());
			this.keywords.add(word);
		}
	}

	public Antigen(String description){
		this.setKeywords(new ArrayList<Keyword>());
		
		this.description = description;
		String[] cleanQuestion = PreProcess.cleanQuestion(description).split(" ");
	
		for(String s :cleanQuestion){
			Keyword word = new Keyword(); 
			word.setKeyword(s.trim());
			this.keywords.add(word);
		}
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public List<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<Keyword> keywords) {
		this.keywords = keywords;
	}
}
