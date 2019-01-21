package com.wm.pedidovenda.repository.filter;

import java.io.Serializable;

public class ClienteFilter implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String nome;
	
	private String documentoReceitaFederal;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDocumentoReceitaFederal() {
		return documentoReceitaFederal;
	}

	public void setDocumentoReceitaFederal(String documentoReceitaFederal) {
		this.documentoReceitaFederal = documentoReceitaFederal;
	}
	
	
}
