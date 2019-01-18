package com.wm.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.wm.pedidovenda.model.Categoria;
import com.wm.pedidovenda.repository.Categorias;
import com.wm.pedidovenda.repository.filter.CategoriaFilter;
import com.wm.pedidovenda.service.NegocioException;
import com.wm.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaCategoriasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Categorias categorias;
	
	private List<Categoria> categoriasRaizes;
	
	private CategoriaFilter filtro;
	private List<Categoria> categoriasFiltradas;
	
	private Categoria categoriaSelecionada;
	
	public PesquisaCategoriasBean() {
		filtro = new CategoriaFilter();
	}
	
	public void preRender() {
		categoriasRaizes = categorias.raizes();
	}
	
	public void excluir() {
		try {
			categorias.remover(categoriaSelecionada);
			categoriasFiltradas.remove(categoriaSelecionada);
			
			FacesUtil.addInfoMessage("Categoria " + categoriaSelecionada.getDescricao() 
					+ " excluída com sucesso.");
		} catch (NegocioException ne) {
			FacesUtil.addErrorMessage(ne.getMessage());
		}
	}
	
	public void pesquisar() {
		categoriasFiltradas = categorias.filtrados(filtro);
	}
	
	public List<Categoria> getCategoriasFiltradas() {
		return categoriasFiltradas;
	}

	public CategoriaFilter getFiltro() {
		return filtro;
	}

	public Categoria getCategoriaSelecionada() {
		return categoriaSelecionada;
	}

	public void setCategoriaSelecionada(Categoria categoriaSelecionada) {
		this.categoriaSelecionada = categoriaSelecionada;
	}

	public List<Categoria> getCategoriasRaizes() {
		return categoriasRaizes;
	}

}