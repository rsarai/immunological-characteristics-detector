package com.SCGEOrientaIA.util;

import java.util.List;

public class EntropyNode {
	private List<PerguntaResposta> perguntaRespostaList;
	private Keyword keyword;
	private double entropy;
	private EntropyNode leftNode, rightNode;
	
	public EntropyNode(){}
	public EntropyNode(Keyword keyword, List<PerguntaResposta> perguntaRespostaList, double entropy){
		this.keyword = keyword; this.perguntaRespostaList = perguntaRespostaList; this.entropy = entropy;
	}
	
	public List<PerguntaResposta> getPerguntaRespostaList() {
		return perguntaRespostaList;
	}
	public void setPerguntaRespostaList(List<PerguntaResposta> perguntaRespostaList) {
		this.perguntaRespostaList = perguntaRespostaList;
	}
	public Keyword getKeyword() {
		return keyword;
	}
	public void setKeyword(Keyword keyword) {
		this.keyword = keyword;
	}
	public double getEntropy() {
		return entropy;
	}
	public void setEntropy(double entropy) {
		this.entropy = entropy;
	}
	public EntropyNode getLeftNode() {
		return leftNode;
	}
	public void setLeftNode(EntropyNode leftNode) {
		this.leftNode = leftNode;
	}
	public EntropyNode getRightNode() {
		return rightNode;
	}
	public void setRightNode(EntropyNode rightNode) {
		this.rightNode = rightNode;
	}
}
