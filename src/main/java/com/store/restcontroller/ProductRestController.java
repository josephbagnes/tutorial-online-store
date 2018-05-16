package com.store.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.store.model.Product;
import com.store.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductRestController {

	@Autowired
	private ProductService productService;

	@PostMapping("/save")
	public Product saveOrUpdate(@RequestBody Product product) {
		return this.productService.addOrUpdate(product);
	}

	@PostMapping("/delete")
	public String delete(@RequestBody Product product) {
		this.productService.delete(product.getId());
		return "OK";
	}

	@GetMapping("/get-all")
	public List<Product> findAll() {
		return this.productService.findAll();
	}

	@GetMapping("/get-by-name")
	public Product findByName(@RequestParam String name) {
		return this.productService.findByName(name);
	}

	@GetMapping("/get-one/{code}")
	public Product findByCode(@PathVariable("code") String code) {
		return this.productService.findByCode(code);
	}
}
