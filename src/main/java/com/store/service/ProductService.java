package com.store.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.store.model.Product;
import com.store.repository.ProductRepository;

@Service
@Transactional
public class ProductService extends BaseService<Product> {

	private ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		super(productRepository);
		this.productRepository = productRepository;
	}

	public Product findByName(String name) {
		return this.productRepository.findOneByName(name);
	}

	public Product findByCode(String code) {
		return this.productRepository.findOneByCode(code);
	}
}
