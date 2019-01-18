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

import org.apache.commons.lang3.StringUtils;

import com.wm.pedidovenda.model.Categoria;
import com.wm.pedidovenda.repository.filter.CategoriaFilter;
import com.wm.pedidovenda.service.NegocioException;
import com.wm.pedidovenda.util.jpa.Transactional;

public class Categorias implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public Categoria guardar(Categoria categoria) {
		return manager.merge(categoria);
	}
	
	@Transactional
	public void remover(Categoria categoria) throws NegocioException {
		try {
			categoria = porId(categoria.getId());
			manager.remove(categoria);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Categoria não pode ser excluída.");
		}
	}
	
	public Categoria porId(Long id) {
		return manager.find(Categoria.class, id);
	}
	
	public Categoria porDescricao(String descricao) {
		try {
			return manager.createQuery("from Categoria where upper(descricao) = :descricao", Categoria.class)
				.setParameter("descricao", descricao.toUpperCase())
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<Categoria> raizes() {
		return manager.createQuery("from Categoria where categoriaPai is null", 
				Categoria.class).getResultList();
	}
	
	public List<Categoria> subcategoriasDe(Categoria categoriaPai) {
		return manager.createQuery("from Categoria where categoriaPai = :raiz", 
				Categoria.class).setParameter("raiz", categoriaPai).getResultList();
	}
	
	public List<Categoria> filtrados(CategoriaFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Categoria> criteriaQuery = builder.createQuery(Categoria.class);
		List<Predicate> predicates = new ArrayList<>();
		
		Root<Categoria> categoriaRoot = criteriaQuery.from(Categoria.class);
		Join<Categoria, Categoria> categoriaJoin = categoriaRoot.join("categoriaPai");
						
		if(filtro.getCategoriaRaiz() != null) {
			predicates.add(builder.equal(categoriaRoot.get("categoriaPai"), filtro.getCategoriaRaiz()));
		}
		
		if (StringUtils.isNotBlank(filtro.getDescricao())) {
			predicates.add(builder.like(builder.lower(categoriaRoot.get("descricao")), 
					"%" + filtro.getDescricao().toLowerCase() + "%"));
		}
		
		criteriaQuery.select(categoriaRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		
		List<Order> orderList = new ArrayList<Order>(); 
		orderList.add(builder.asc(categoriaJoin.get("descricao")));
		orderList.add(builder.asc(categoriaRoot.get("descricao")));
		criteriaQuery.orderBy(orderList);
		
		TypedQuery<Categoria> query = manager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
}