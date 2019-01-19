package com.wm.pedidovenda.repository.filter;

import java.io.Serializable;

import com.wm.pedidovenda.model.Grupo;

public class UsuarioFilter implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String nome;
	
	private Grupo grupo;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

}
