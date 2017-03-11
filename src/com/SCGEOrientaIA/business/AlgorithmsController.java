package com.SCGEOrientaIA.business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import com.SCGEOrientaIA.dao.util.IOManipulation;
import com.SCGEOrientaIA.helper.PreProcess;
import com.SCGEOrientaIA.programSettings.ProjectProperties;
import com.SCGEOrientaIA.util.Antibody;
import com.SCGEOrientaIA.util.Antigen;
import com.SCGEOrientaIA.util.Keyword;

public class AlgorithmsController {
	private static Properties projectProperties = ProjectProperties.getProperties();
	private static Random r = new Random();
	private static String[] list = {"assuntoNaoDefinido.txt", "capacitacaoTreinamento.txt", "classificacaoDaDespesa.txt", "contratoLicitacao.txt", "controleInterno.txt",
			"convenio.txt", "despesaExerciciosAnteriores.txt" , "despesaPessoal.txt" , "diaria.txt" , "diversos.txt" , "documentoFiscalComprovacaoDespesa.txt" 
			, "empenhosLiquidacaoPagamentos.txt" , "pcgPMG.txt" , "prestacaoDeContas.txt" , "referenciaLegalNormaLegal.txt" , "restosAPagar.txt" , "retencoes.txt" , "semTratamento.txt" 
			, "suprimentoIndividual.txt" ,"suprimentoInstitucional.txt" };
	
	//CALCULO DA TAXA DE MUTAÇÃO PODE SER MODIFICADA, IMPLEMENTAÇÃO FEITA BASEADA EM EXEMPLO ENCONTRADO NO GITHUB
	public static <T> List<Antibody> hypermutate(List<Antibody> clones){
		
		
		System.out.println("Performing mutations...");
		List<Antibody> antibodiesMutate = new ArrayList<Antibody>();
		
		Collections.sort(clones);
		
		//MODIFICACAO POSSIVEL GERAR PALAVRAS A PARTIR DE UM GRUPO DE PALAVRAS PARA CADA ASSUNTO
		for (int i = 0; i < clones.size(); i++){
			if(clones.get(i) != null){
				Antibody forMutation = clones.get(i);
				int lengthOfKeywords = forMutation.getKeywords().size();
				double x = lengthOfKeywords / 2;
				double taxOfHypermutation = Math.exp(-(i+1) / lengthOfKeywords);
				int numOfMutations = (int) Math.round(x * taxOfHypermutation);
				
				for(int j = 0; j < numOfMutations; j++){
					String newWord = generateWordForHypermutate(forMutation);
					int index = r.nextInt(lengthOfKeywords);
					forMutation.getKeywords().get(index).setKeyword(newWord);
				}
				
				antibodiesMutate.add(forMutation);
			}
		}
		
		return antibodiesMutate;
	}
	
	private static String generateWordForHypermutate(Antibody forMutation) {
		String endereco = list[forMutation.getEnumSubject().getId()-1];
		List<String> a = IOManipulation.readFileContent("res/wordsForHypermutation/" + endereco);
		int size = a.size();
		return a.get(r.nextInt(size));
	}

	public static List<Antibody> affinity(Antigen ag, List<Antibody> p){
		List<Antibody> acceptedAntibodies = new ArrayList<Antibody>();
		int discardThreshold = Integer.parseInt(projectProperties.getProperty("discardThreshold"));
		//int affinityIdentificationThreshold = Integer.parseInt(projectProperties.getProperty("affinityIdentificationThreshold"));
		
		for(Antibody antibody : p){
			int matchCount = 0;
			
			for(Keyword wordAntibody : antibody.getKeywords()){
				for(Keyword wordAntigen : ag.getKeywords()){
					if(wordAntibody.getKeyword().length() == wordAntigen.getKeyword().length()){
						if(verifyTerms(wordAntibody.getKeyword(), wordAntigen.getKeyword())){
							matchCount++;
							matchCount += wordAntibody.getRepeatedTimesPerText();
							break;
						}
					}
				}
			}
			antibody.setAffinity(matchCount);
			if(matchCount >= discardThreshold){
				acceptedAntibodies.add(antibody);
			}
		}
		
		return acceptedAntibodies;
	}
	
	public static int getAffinity(Antigen ag, Antibody p){
		int matchCount = 0;
		
		for(Keyword wordAntibody : p.getKeywords()){
			for(Keyword wordAntigen : ag.getKeywords()){
				if(wordAntibody.getKeyword().length() == wordAntigen.getKeyword().length()){
					if(verifyTerms(wordAntibody.getKeyword(), wordAntigen.getKeyword())){
						matchCount++;
						break;
					}
				}
			}
		}
		
		return matchCount;
	}
	
	public static Antibody higherAffinity(Antigen ag, List<Antibody> p){
		List<Antibody> acceptedAntibodies = affinity(ag, p); 
		int affinity = 0;

		if(!acceptedAntibodies.isEmpty()){
			for (int i = 0; i < acceptedAntibodies.size(); i++){
				if(acceptedAntibodies.get(i).getAffinity() >= acceptedAntibodies.get(affinity).getAffinity()){
					affinity = i;
				}
			}
			return acceptedAntibodies.get(affinity);
		}else{
			Antibody a = new Antibody();
			a.setAffinity(getAffinity(ag, a));
			return a;
		}
	}
	
	public static Antibody lessAffinity(List<Antibody> m){
		Antibody less = m.get(0);
		for(Antibody a : m){
			if(a.getAffinity() < less.getAffinity()){
				less = a;
			}
		}
		
		return less;
	}
	
	public static void replaceCellsWithLessAffinity(List<Antibody> pr){
		//float replacement = Float.parseFloat(projectProperties.getProperty("replacement"));
		//int numberOfReplacements = Math.round(pr.size()/replacement);
		int numberOfReplacements = Math.round((pr.size()*Integer.parseInt(projectProperties.getProperty("replacement")))/100);
		
		for(int i = 0; i < numberOfReplacements; i++){
			pr.remove(lessAffinity(pr));
			pr.add(new Antibody());
		}
	}
	
	public static List<Antibody> cloning(List<Antibody> antibodiesWithAffinity){
		int size = antibodiesWithAffinity.size();
		double beta = Double.parseDouble(projectProperties.getProperty("beta"));
		int numberOfAntibodies= Integer.parseInt(projectProperties.getProperty("numberOfAntibodies"));
		List<Antibody> clones = new ArrayList<Antibody>();
		int totalClones = 0;
		
		for (int i = 0; i < size; i++){
			totalClones += Math.round((beta*numberOfAntibodies)/(i+1));
		}
		
		for(Antibody possibleAntibody : antibodiesWithAffinity){
			
			for (int i = 0; i < totalClones; i++){
				clones.add(possibleAntibody);
			}
			
		}
		return clones;
	}

	public static boolean verifyTerms(String s1, String s2){
		boolean termsMatches = false;
		int hammingDistanceThreshold = Integer.parseInt(projectProperties.getProperty("hammingDistanceThreshold"));
		int hammingDistance = hammingDistance(s1, s2);
		
		if(hammingDistance <= hammingDistanceThreshold){
			termsMatches = true;
		}
		
		return termsMatches;
	}
	
	public static int hammingDistance(String s1, String s2){
		int hammingDistance = 0;
		
		if(s1.length() == s2.length()){
			for(int i = 0; i < s1.length(); i++){
				if(s1.charAt(i) != s2.charAt(i))
					hammingDistance++;
			}
		}else{
			return Math.max(s1.length(), s2.length());
		}
		
		return hammingDistance;
	}
	
	//soluções candidatas aleatorias
	public static List<Antibody> generateAntibodies(){
		int numberOfAntibodies = Integer.parseInt(projectProperties.getProperty("numberOfAntibodies"));
		
		List<Antibody> antibodies = new ArrayList<Antibody>();

		//população restante
		for(int i = 0; i < numberOfAntibodies; i++){
			antibodies.add(new Antibody());
		}
		
		
		
		System.out.println("Antibodies OK");
		return antibodies;
	}
	
	//população de celulas de memoria
	public static List<Antibody> generatePopulationOfMemoryCells() {
		System.out.println("Loading memory cells...");
		System.out.println("This is definitely going to take a while");
		List<Antibody> memoryCells = new ArrayList<Antibody>();
		List<String> genericAnswers = IOManipulation.readFileContent(projectProperties.getProperty("answersDictionaryFile"));
		
		for(String answers : genericAnswers){
			String description = answers;
			String subject = answers.substring(answers.lastIndexOf("	")).trim();
			
			String[] cleanQuestion = PreProcess.cleanQuestion(answers.replace(subject, "").trim()).split(" ");
			List<Keyword> list = new ArrayList<Keyword>();
			
			for(String s : cleanQuestion){
				Keyword word = new Keyword(); 
				word.setKeyword(s.trim());
				list.add(word);
			}
			Antibody newAntibody = new Antibody(description, subject, list);
			memoryCells.add(newAntibody);
		}
		return memoryCells;
	}

	//conjunto de dados de treinamento
	public static List<Antigen> generateAntigens(){
		Properties projectProperties = ProjectProperties.getProperties();
		int numberOfDetectors = Integer.parseInt(projectProperties.getProperty("numberOfDetectors"));
		
		List<Antigen> antigensAG = new ArrayList<Antigen>();
		for (int i = 0; i < numberOfDetectors; i++){
			antigensAG.add(new Antigen());
		}
		
		System.out.println("Antigens OK");
		return antigensAG;
	}
	
	public static List<Antibody> generateListOfWords(){
		Properties projectProperties = ProjectProperties.getProperties();
		int numberOfAntibodies = Integer.parseInt(projectProperties.getProperty("numberOfAntibodies"));
		
		List<Antibody> antibodies = new ArrayList<Antibody>();
		
		for(int i = 0; i < numberOfAntibodies; i++){
			antibodies.add(new Antibody());
		}
		
		return antibodies;
	}
	
	
	public static void generateWordlistForHypermutation(String end){
		List<String> a = IOManipulation.readFileContent("res/treinamento/" + end);
		
		System.out.println("Ok! Let's do this!");
		for(int i = 0; i < a.size(); i++){
			String answer = a.get(i);
			answer = answer.substring(0,answer.lastIndexOf("	")-1);
			
			String[] cleanQuestion = PreProcess.cleanQuestion(answer.trim()).split(" ");
			for(String s : cleanQuestion){
				IOManipulation.criarKeywords(s.trim().toLowerCase(), end);
			}
		}
	}

	public static void removeFromPopulationAllNonMemoryCells(List<Antibody> populationOfAntibodies, List<Antibody> p) {
		List<Antibody> test = populationOfAntibodies;
		for(int i = 0; i < populationOfAntibodies.size(); i++){
			if(test.get(i).equals(p.get(i))){
				populationOfAntibodies.remove(i);
			}
		}
	}
}