package com.wm.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.wm.pedidovenda.model.Cliente;
import com.wm.pedidovenda.repository.Clientes;
import com.wm.pedidovenda.util.jpa.Transactional;

public class CadastroClienteService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Clientes clientes;
	
	@Transactional
	public Cliente salvar(Cliente cliente) throws NegocioException {
		Cliente clienteExistente = clientes.buscarPorNome(cliente.getNome());
		
		if (clienteExistente != null && !clienteExistente.equals(cliente)) {
			throw new NegocioException("Já existe um cliente com o nome informado.");
		}
		
		return clientes.guardar(cliente);
	}
	
}
