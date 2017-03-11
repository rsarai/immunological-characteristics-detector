package com.SCGEOrientaIA.dao.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class IOManipulation {

	public static void criarKeywords(String content, String end){
		try {
			boolean writeFile = true;
			List<String> fileContent = readFileContent("res/wordsForHypermutation/" + end);
			for (String s : fileContent){
				if(s.trim().equals(content.trim())){
					writeFile = false;
				}
			}
			if(writeFile){
				content += "\n";
		    	Files.write(Paths.get("res/wordsForHypermutation/" + end), content.getBytes(), StandardOpenOption.APPEND);
			}
		} catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
		}
	}
	
	
	
	
	public static List<String> readFileContent(String filePath){
		List<String> fileContent = new ArrayList<String>();
		
		try {
			BufferedReader bReader = new BufferedReader(new FileReader(new File(filePath)));
			while(bReader.ready()){
				fileContent.add(bReader.readLine());
			}
			bReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return fileContent;
	}
	
	
	public static void tratarPergunta(){
		String inputPerguntas = "res/questions.txt";
		FileOutputStream fop = null;
		try {
			FileInputStream fstream = new FileInputStream(inputPerguntas);
	        DataInputStream in = new DataInputStream(fstream);
	        @SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
	        
	        String content = "";
	        int value = 1;
	        while(br.ready()){
				content = br.readLine() + "\n";
				
				int zeros = 5 - Integer.toString(value).length();
				String name = "";
				for(int i = 0; i < zeros; i++){
					name +="0";
				}
				name += Integer.toString(value);
				File file = new File("res/questions/" + name + ".txt");
				if (file.createNewFile()){
			       System.out.println("File is created!");
			    }else{
			       System.out.println("File already exists.");
			    }
				
				
				fop = new FileOutputStream(file);

				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}

				// get the content in bytes
				byte[] contentInBytes = content.getBytes();

				fop.write(contentInBytes);
				fop.flush();
				fop.close();

				System.out.println("Done");
				value++;
			}
	        
		} catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
