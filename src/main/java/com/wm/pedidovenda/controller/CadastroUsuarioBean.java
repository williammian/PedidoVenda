package com.wm.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.sun.faces.taglib.html_basic.SelectManyCheckboxTag;
import com.wm.pedidovenda.model.Grupo;
import com.wm.pedidovenda.model.Usuario;
import com.wm.pedidovenda.repository.Usuarios;
import com.wm.pedidovenda.service.CadastroUsuarioService;
import com.wm.pedidovenda.service.NegocioException;
import com.wm.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Usuarios usuarios;
	
	@Inject
	private CadastroUsuarioService cadastroUsuarioService;
	
	private Usuario usuario;
		
	private List<Grupo> grupos;
	
	private List<Grupo> gruposSelecionados;
		
	public CadastroUsuarioBean() {
		limpar();
	}
	
	public void inicializar() {
		if (this.usuario == null) {
			limpar();
		}
		
		grupos = usuarios.grupos();
		
		if(this.usuario != null) {
			gruposSelecionados = usuario.getGrupos();
		}
	}
	
	private void limpar() {
		usuario = new Usuario();
	}
	
	public void salvar() {
		try {
			this.usuario.setGrupos(gruposSelecionados);
			
			this.usuario = cadastroUsuarioService.salvar(this.usuario);
			limpar();
			
			FacesUtil.addInfoMessage("Usuário salvo com sucesso!");
		} catch (NegocioException ne) {
			FacesUtil.addErrorMessage(ne.getMessage());
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}
	
	public List<Grupo> getGruposSelecionados() {
		return gruposSelecionados;
	}

	public void setGruposSelecionados(List<Grupo> gruposSelecionados) {
		this.gruposSelecionados = gruposSelecionados;
	}

	public boolean isEditando() {
		return this.usuario.getId() != null;
	}

}