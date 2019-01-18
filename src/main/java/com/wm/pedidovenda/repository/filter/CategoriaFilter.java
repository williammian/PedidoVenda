package com.wm.pedidovenda.repository.filter;

import java.io.Serializable;

import com.wm.pedidovenda.model.Categoria;

public class CategoriaFilter implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String descricao;
	
	private Categoria categoriaRaiz;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Categoria getCategoriaRaiz() {
		return categoriaRaiz;
	}

	public void setCategoriaRaiz(Categoria categoriaRaiz) {
		this.categoriaRaiz = categoriaRaiz;
	}

}
