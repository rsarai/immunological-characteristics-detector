package com.SCGEOrientaIA.util;

public class PerguntaResposta {
	private Assunto assunto;
	private IndiceSatisfacao indiceSatisfacao;
	private Pergunta pergunta;
	private Resposta resposta;
	private UnidadeGestora unidadeGestora;
	private double questionAccuracy, valorSatisfacao;
	
	public PerguntaResposta(){}
	public PerguntaResposta(Assunto assunto, Pergunta pergunta, Resposta resposta, UnidadeGestora unidadeGestora, IndiceSatisfacao indiceSatisfacao, double valorSatisfacao){
		this.assunto = assunto; this.pergunta = pergunta; this.resposta = resposta;
		this.unidadeGestora = unidadeGestora; this.indiceSatisfacao = indiceSatisfacao;
		this.valorSatisfacao = valorSatisfacao;
	}
	
	public Assunto getAssunto() {
		return assunto;
	}
	public void setAssunto(Assunto assunto) {
		this.assunto = assunto;
	}
	public IndiceSatisfacao getIndiceSatisfacao() {
		return indiceSatisfacao;
	}
	public void setIndiceSatisfacao(IndiceSatisfacao indiceSatisfacao) {
		this.indiceSatisfacao = indiceSatisfacao;
	}
	public Pergunta getPergunta() {
		return pergunta;
	}
	public void setPergunta(Pergunta pergunta) {
		this.pergunta = pergunta;
	}
	public Resposta getResposta() {
		return resposta;
	}
	public void setResposta(Resposta resposta) {
		this.resposta = resposta;
	}
	public UnidadeGestora getUnidadeGestora() {
		return unidadeGestora;
	}
	public void setUnidadeGestora(UnidadeGestora unidadeGestora) {
		this.unidadeGestora = unidadeGestora;
	}
	public double getQuestionAccuracy() {
		return questionAccuracy;
	}
	public void setQuestionAccuracy(double questionAccuracy) {
		this.questionAccuracy = questionAccuracy;
	}
	public double getValorSatisfacao() {
		return valorSatisfacao;
	}
	public void setValorSatisfacao(double valorSatisfacao) {
		this.valorSatisfacao = valorSatisfacao;
	}
}
