package com.wm.pedidovenda.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.wm.pedidovenda.model.Cliente;
import com.wm.pedidovenda.model.Endereco;
import com.wm.pedidovenda.model.TipoPessoa;
import com.wm.pedidovenda.service.CadastroClienteService;
import com.wm.pedidovenda.service.NegocioException;
import com.wm.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private CadastroClienteService cadastroClienteService;
	
	private Cliente cliente;
	
	private String mascaraCpfCnpj;
	private Integer sizeCpfCnpj;
	
	private Endereco enderecoSelecionado;
		
	public CadastroClienteBean() {
		limpar();
	}
	
	public void inicializar() {
		if (this.cliente == null) {
			limpar();
		}
	}
	
	private void limpar() {
		cliente = new Cliente();
		cliente.setTipo(TipoPessoa.FISICA);
	}
	
	public void salvar() {
		try {
			this.cliente = cadastroClienteService.salvar(this.cliente);
			limpar();
			
			FacesUtil.addInfoMessage("Cliente salvo com sucesso!");
		} catch (NegocioException ne) {
			FacesUtil.addErrorMessage(ne.getMessage());
		}
	}

	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public TipoPessoa[] getTipos () {
        return TipoPessoa.values();
    }
	
	public boolean isEditando() {
		return this.cliente.getId() != null;
	}
	
	public void trocarMascara(){
		if(this.cliente.getTipo() != null){
			if (this.cliente.getTipo().equals(TipoPessoa.JURIDICA)) {  
		        mascaraCpfCnpj = "99.999.999/9999-99"; 
		        sizeCpfCnpj = 18;
		    } else {
		        mascaraCpfCnpj = "999.999.999-99";
		        sizeCpfCnpj = 14;
		    }  
		} 
	}

	public String getMascaraCpfCnpj() {
		return mascaraCpfCnpj;
	}

	public void setMascaraCpfCnpj(String mascaraCpfCnpj) {
		this.mascaraCpfCnpj = mascaraCpfCnpj;
	}

	public Integer getSizeCpfCnpj() {
		return sizeCpfCnpj;
	}

	public void setSizeCpfCnpj(Integer sizeCpfCnpj) {
		this.sizeCpfCnpj = sizeCpfCnpj;
	}

	public Endereco getEnderecoSelecionado() {
		return enderecoSelecionado;
	}

	public void setEnderecoSelecionado(Endereco enderecoSelecionado) {
		this.enderecoSelecionado = enderecoSelecionado;
	}
	
	public void excluirEndereco() {
		
	}
	

}