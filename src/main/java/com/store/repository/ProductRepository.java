package com.store.repository;

import org.springframework.data.repository.CrudRepository;

import com.store.model.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {

	Product findOneByName(String name);

	Product findOneByCode(String code);
}
