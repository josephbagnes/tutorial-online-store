package com.store.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BaseService<T> {

	private CrudRepository<T, Integer> crudRepository;

	public BaseService(CrudRepository<T, Integer> crudRepository) {
		this.crudRepository = crudRepository;
	}

	public T addOrUpdate(T model) {
		return this.crudRepository.save(model);
	}

	public void delete(int id) {
		this.crudRepository.delete(id);
	}

	public List<T> findAll() {
		return (List<T>) this.crudRepository.findAll();
	}
}
