package com.SCGEOrientaIA.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import com.SCGEOrientaIA.algorithms.EnumSubject;
import com.SCGEOrientaIA.dao.util.IOManipulation;
import com.SCGEOrientaIA.helper.PreProcess;
import com.SCGEOrientaIA.programSettings.ProjectProperties;

/*
 * Nesse projeto usaremos os linf�citos como as respostas das perguntas.
 * Cada resposta tem uma descri��o, um assunto e suas keyWords
 */

public class Antibody implements Comparable<Antibody>{
	private List<Keyword> keywords;
	private String description;
	private String subject;
	private EnumSubject enumSubject;
	private int affinity;
	
	public Antibody(String d, String s, List<Keyword> k){
		this.subject = s;
		this.description = d;
		this.keywords = k;
		for(EnumSubject e : EnumSubject.values()){
			if(this.subject.toUpperCase().equals(e.getSubject().toUpperCase())){
				this.enumSubject = e;
				break;
			}
		}
	}
	
	public Antibody(){
		this.keywords = new ArrayList<Keyword>();
		Properties projectProperties = ProjectProperties.getProperties();
		List<String> answersDictionary = IOManipulation.readFileContent(projectProperties.getProperty("answersDictionaryFile"));
		int numberOfAnswersInDictionary = answersDictionary.size();
		
		Random index = new Random();
		String answers = "";
		int answersOnDetector = 0;
		int sizeOfDetectors = Integer.parseInt(projectProperties.getProperty("sizeOfDetectors"));
		
		answers = answersDictionary.get(index.nextInt(numberOfAnswersInDictionary-1));
		this.description = answers;
		this.subject = answers.substring(answers.lastIndexOf("	")).trim();
		
		for(EnumSubject e : EnumSubject.values()){
			if(this.subject.toUpperCase().equals(e.getSubject().toUpperCase())){
				this.enumSubject = e;
				break;
			}
		}
		
	
		String[] cleanQuestion = PreProcess.cleanQuestion(answers.replace(subject, "").trim()).split(" ");
		int count = 1;
		do{
			int randomNumber = generateRandomNumber(cleanQuestion);
			Keyword word = new Keyword(); 
			word.setKeyword(cleanQuestion[randomNumber].trim());
			
			if(this.getKeywords().isEmpty()){
				word.setRepeatedTimesPerText(1);
				this.keywords.add(word);
				answersOnDetector++;
			}else{
				for(Keyword key : getKeywords()){
					if(key.getKeyword().equals(word.getKeyword())){
						word.setRepeatedTimesPerText(key.getRepeatedTimesPerText()+1);
						this.keywords.remove(key);
						answersOnDetector--;
						break;
					}
				}
				this.keywords.add(word);
				answersOnDetector++;
			}
			count++;
			if(count == 550000){
				break;
			}
		}while(answersOnDetector < sizeOfDetectors && answersOnDetector < cleanQuestion.length-1);
	}
	
	private int generateRandomNumber(String[] cleanQuestion) {
		Random index = new Random();
		return index.nextInt(cleanQuestion.length);
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getAffinity() {
		return affinity;
	}

	public void setAffinity(int affinity) {
		this.affinity = affinity;
	}

	public List<Keyword> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<Keyword> keywords) {
		this.keywords = keywords;
	}
	
	public String toString(){
		StringBuilder s = new StringBuilder();
		
		s.append("RESPOSTA: ");
		s.append(getDescription());
		s.append("\n");
		s.append("KEYWORDS: ");
		for(Keyword key : getKeywords()){
			s.append(key.getKeyword());
			s.append(" ");
		}
		s.append("\n");
		s.append("ASSUNTO: ");
		s.append(getSubject());
		
		s.append("\n");
		s.append("Enum: ");
		s.append(this.getEnumSubject().toString());
		
		return s.toString();
	}
	
	public boolean equals(Object o){
		 if((o instanceof Antibody) && (((Antibody)o).getKeywords().equals(keywords))){
             return true;
		 }else{
             return false;
		 }
	}

	@Override
	public int compareTo(Antibody o) {
		if(this.affinity < o.getAffinity()){
			return -1;
		}else if(this.affinity > o.getAffinity()){
			return 1;
		}
		return 0;
	}

	public EnumSubject getEnumSubject() {
		return enumSubject;
	}

	public void setEnumSubject(EnumSubject enumSubject) {
		this.enumSubject = enumSubject;
	}
	
	
	public Antibody(int index){
		this.keywords = new ArrayList<Keyword>();
		Properties projectProperties = ProjectProperties.getProperties();
		List<String> answersDictionary = IOManipulation.readFileContent(projectProperties.getProperty("answersDictionaryFile"));
		int numberOfAnswersInDictionary = answersDictionary.size();
		
		String answers = "";
		int answersOnDetector = 0;
		int sizeOfDetectors = Integer.parseInt(projectProperties.getProperty("sizeOfDetectors"));
		
		answers = answersDictionary.get(index);
		this.description = answers;
		this.subject = answers.substring(answers.lastIndexOf("	")).trim();
		
		for(EnumSubject e : EnumSubject.values()){
			if(this.subject.toUpperCase().equals(e.getSubject().toUpperCase())){
				this.enumSubject = e;
				break;
			}
		}
		
	
		String[] cleanQuestion = PreProcess.cleanQuestion(answers.replace(subject, "").trim()).split(" ");
		int count = 1;
		do{
			int randomNumber = generateRandomNumber(cleanQuestion);
			Keyword word = new Keyword(); 
			word.setKeyword(cleanQuestion[randomNumber].trim());
			
			if(this.getKeywords().isEmpty()){
				word.setRepeatedTimesPerText(1);
				this.keywords.add(word);
				answersOnDetector++;
			}else{
				for(Keyword key : getKeywords()){
					if(key.getKeyword().equals(word.getKeyword())){
						word.setRepeatedTimesPerText(key.getRepeatedTimesPerText()+1);
						this.keywords.remove(key);
						answersOnDetector--;
						break;
					}
				}
				this.keywords.add(word);
				answersOnDetector++;
			}
			count++;
			if(count == 550000){
				break;
			}
		}while(answersOnDetector < sizeOfDetectors && answersOnDetector < cleanQuestion.length-1);
	}
}
