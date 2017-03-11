package com.SCGEOrientaIA.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;


public class PreProcess {
	public static String clearSpaces(String text){
		String body = "";
		for(int i = 0; i < text.length(); i++){
			if(text.charAt(i) == '\n'){
				if(text.charAt(i+1) == '\n'){
					body = text.substring(i+2);
					break;
				}
			}
		}
		return body;
	}
	
	public static List<String> cleanQuestions(List<String> answers){
		List<String> cleanAnswersContents = new ArrayList<String>();
		String nonStopWords = "";
		
		if(answers != null){
			for(String a : answers){
				String[] wordsInEmail = removeSignals(a).toLowerCase().split(" ");
				List<String> stopWords = getStopWords();
				
				for(int i = 0; i < wordsInEmail.length; i++){
					for(String s : stopWords){
						if(s.trim().equals(wordsInEmail[i].toLowerCase().trim())){
							wordsInEmail[i] = "";
						}
					}
				}
				
				for(String word : wordsInEmail){
					if(!word.equals("")){
						nonStopWords += word.trim() + " ";
					}
				}
				
				cleanAnswersContents.add(nonStopWords);
			}
		}
		
		return cleanAnswersContents;
	}
	
	
	public static String cleanQuestion(String text){
		String noStopWords = "";
		
		String[] wordsInEmail = removeSignals(removerAcentos(text)).toLowerCase().split(" ");
		List<String> stopWords = getStopWords();
		
		for(int i = 0; i < wordsInEmail.length; i++){
			for(String s : stopWords){
				if(s.trim().equals(wordsInEmail[i].toLowerCase().trim())){
					wordsInEmail[i] = "";
					break;
				}
			}
		}
		
		for(String word : wordsInEmail){
			if(!word.equals("")){
				noStopWords += word.trim() + " ";
			}
		}
		
		return noStopWords;
	}


	private static String removeSignals(String text) {
		return text.replace(",", "").replace(".", "").replace("?", "").replace("!", "").replace(":", "").replace("/", " ").replace("-", "").replace("\"", "").
				replace(";", "").replace("(", "").replace(")", "".replace("_", ""));
	}
	
	public static List<String> getStopWords() {
		List<String> stopWords = null;
		
		try {
			File stopWordsFile = new File("res/stopwords.txt");
			@SuppressWarnings("resource")
			BufferedReader bReader = new BufferedReader(new FileReader(stopWordsFile));
			stopWords = new ArrayList<String>();
			
			while(bReader.ready()){
				stopWords.add(bReader.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stopWords;
	}
	
	public static String removerAcentos(String str) {
	    return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}
	
	
	

}
