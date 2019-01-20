package com.wm.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.wm.pedidovenda.model.Usuario;
import com.wm.pedidovenda.repository.Usuarios;
import com.wm.pedidovenda.util.jpa.Transactional;

public class CadastroUsuarioService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Usuarios usuarios;
	
	@Transactional
	public Usuario salvar(Usuario usuario) throws NegocioException {
		Usuario usuarioExistente = usuarios.porEmail(usuario.getEmail());
		
		if (usuarioExistente != null && !usuarioExistente.equals(usuario)) {
			throw new NegocioException("Já existe um usuário com o email informado.");
		}
		
		return usuarios.guardar(usuario);
	}
	
}
