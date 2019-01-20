package com.wm.pedidovenda.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.lang3.StringUtils;

import com.wm.pedidovenda.model.Grupo;
import com.wm.pedidovenda.model.Usuario;
import com.wm.pedidovenda.repository.filter.UsuarioFilter;
import com.wm.pedidovenda.service.NegocioException;
import com.wm.pedidovenda.util.jpa.Transactional;

public class Usuarios implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Usuario guardar(Usuario usuario) {
		return manager.merge(usuario);
	}
	
	@Transactional
	public void remover(Usuario usuario) throws NegocioException {
		try {
			usuario = porId(usuario.getId());
			manager.remove(usuario);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Usuário não pode ser excluído.");
		}
	}
	
	public Usuario porId(Long id) {
		return this.manager.find(Usuario.class, id);
	}
	
	public List<Usuario> vendedores() {
		// TODO filtrar apenas vendedores (por um grupo específico)
		return this.manager.createQuery("from Usuario", Usuario.class)
				.getResultList();
	}

	public Usuario porEmail(String email) {
		Usuario usuario = null;
		
		try {
			usuario = this.manager.createQuery("from Usuario where lower(email) = :email", Usuario.class)
				.setParameter("email", email.toLowerCase()).getSingleResult();
		} catch (NoResultException e) {
			// nenhum usuário encontrado com o e-mail informado
		}
		
		return usuario;
	}
	
	public List<Grupo> grupos() {
		return this.manager.createQuery("from Grupo", Grupo.class)
				.getResultList();
	}
	
	public List<Usuario> filtrados(UsuarioFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteriaQuery = builder.createQuery(Usuario.class);
		List<Predicate> predicates = new ArrayList<>();
		
		Root<Usuario> usuarioRoot = criteriaQuery.from(Usuario.class);	
								
		if (StringUtils.isNotBlank(filtro.getNome())) {
			predicates.add(builder.like(builder.lower(usuarioRoot.get("nome")), 
					"%" + filtro.getNome().toLowerCase() + "%"));
		}
		
		criteriaQuery.select(usuarioRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		
		if(filtro.getGrupo() != null) {
			Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
		    Root<Usuario> subroot = subquery.from(Usuario.class);
		    subquery.select(subroot.<Long>get("id"));
		    Join<Usuario, Grupo> grupos = subroot.join("grupos");
		    subquery.where(builder.equal(grupos.get("id"), filtro.getGrupo().getId()));
		    criteriaQuery.where(builder.in(usuarioRoot.get("id")).value(subquery));
		}
		
		List<Order> orderList = new ArrayList<Order>(); 
		orderList.add(builder.asc(usuarioRoot.get("nome")));
		criteriaQuery.orderBy(orderList);
		
		TypedQuery<Usuario> query = manager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
}