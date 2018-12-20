package com.wm.pedidovenda.controller;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.velocity.tools.generic.NumberTool;

import com.outjected.email.api.MailMessage;
import com.outjected.email.impl.templating.velocity.VelocityTemplate;
import com.wm.pedidovenda.model.Pedido;
import com.wm.pedidovenda.util.jsf.FacesUtil;
import com.wm.pedidovenda.util.mail.Mailer;

@Named
@RequestScoped
public class EnvioPedidoEmailBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Mailer mailer;
	
	@Inject
	@PedidoEdicao
	private Pedido pedido;
	
	public void enviarPedido() throws Exception {
		MailMessage message = mailer.novaMensagem();
		
		//new VelocityTemplate(getClass().getResourceAsStream("/emails/pedido.template"))
		
		URL resource = getClass().getResource("/emails/pedido.template");
		File file = new File(resource.toURI());
		
		message.to(this.pedido.getCliente().getEmail())
			.subject("Pedido " + this.pedido.getId())
			.bodyHtml(new VelocityTemplate(file))
			.put("pedido", this.pedido)
			.put("numberTool", new NumberTool())
			.put("locale", new Locale("pt", "BR"))
			.send();
		
		FacesUtil.addInfoMessage("Pedido enviado por e-mail com sucesso!");
	}
	
	
}
