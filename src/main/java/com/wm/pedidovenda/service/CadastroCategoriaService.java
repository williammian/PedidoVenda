package com.wm.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.wm.pedidovenda.model.Categoria;
import com.wm.pedidovenda.repository.Categorias;
import com.wm.pedidovenda.util.jpa.Transactional;

public class CadastroCategoriaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Categorias categorias;
	
	@Transactional
	public Categoria salvar(Categoria categoria) throws NegocioException {
		Categoria categoriaExistente = categorias.porDescricao(categoria.getDescricao());
		
		if (categoriaExistente != null && !categoriaExistente.equals(categoria)) {
			throw new NegocioException("Já existe uma categoria a descrição informada.");
		}
		
		return categorias.guardar(categoria);
	}
	
}
