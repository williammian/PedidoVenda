package com.wm.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.wm.pedidovenda.model.Categoria;
import com.wm.pedidovenda.repository.Categorias;
import com.wm.pedidovenda.service.CadastroCategoriaService;
import com.wm.pedidovenda.service.NegocioException;
import com.wm.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroCategoriaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Categorias categorias;
	
	@Inject
	private CadastroCategoriaService cadastroCategoriaService;
	
	private Categoria categoria;
		
	private List<Categoria> categoriasRaizes;
		
	public CadastroCategoriaBean() {
		limpar();
	}
	
	public void inicializar() {
		if (this.categoria == null) {
			limpar();
		}
		
		categoriasRaizes = categorias.raizes();
	}
	
	private void limpar() {
		categoria = new Categoria();
	}
	
	public void salvar() {
		try {
			this.categoria = cadastroCategoriaService.salvar(this.categoria);
			limpar();
			
			FacesUtil.addInfoMessage("Categoria salva com sucesso!");
		} catch (NegocioException ne) {
			FacesUtil.addErrorMessage(ne.getMessage());
		}
	}

	public Categoria getCategoria() {
		return categoria;
	}
	
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<Categoria> getCategoriasRaizes() {
		return categoriasRaizes;
	}
	
	public boolean isEditando() {
		return this.categoria.getId() != null;
	}

}