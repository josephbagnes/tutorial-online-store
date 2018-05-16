package com.store.restcontroller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.store.model.Product;
import com.store.utils.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRestControllerTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	@Test
	@Transactional
	public void testSaveOrUpdate() throws IOException, Exception {
		Product p = new Product("P004", "Royal 2L", 16.0);
		this.mvc.perform(post("/api/product/save").contentType(TestUtils.APPLICATION_JSON_UTF8)
				.content(TestUtils.convertObjectToJsonBytes(p))).andExpect(status().isOk())
				.andExpect(jsonPath("$.code", equalTo("P004"))).andExpect(jsonPath("$.name", equalTo("Royal 2L")))
				.andExpect(jsonPath("$.price", equalTo(16.0)));
	}

	@Test
	public void testDelete() throws IOException, Exception {
		Product p = new Product();
		p.setId(1);
		this.mvc.perform(post("/api/product/delete").contentType(TestUtils.APPLICATION_JSON_UTF8)
				.content(TestUtils.convertObjectToJsonBytes(p))).andExpect(status().isOk())
				.andExpect(content().string(containsString("OK")));
		this.mvc.perform(get("/api/product/get-all")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
	}

	@Test
	public void testFindAll() throws Exception {
		this.mvc.perform(get("/api/product/get-all")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0].code", equalTo("P001")))
				.andExpect(jsonPath("$[0].name", equalTo("Coca-Cola 1L")))
				.andExpect(jsonPath("$[0].price", equalTo(15.0)));
	}

	@Test
	public void testFindByName() throws Exception {
		this.mvc.perform(get("/api/product/get-by-name?name=Coca-Cola 1L")).andExpect(status().isOk())
				.andExpect(jsonPath("$.code", equalTo("P001"))).andExpect(jsonPath("$.name", equalTo("Coca-Cola 1L")))
				.andExpect(jsonPath("$.price", equalTo(15.0)));
	}

	@Test
	public void testFindByCode() throws Exception {
		this.mvc.perform(get("/api/product/get-one/P002")).andExpect(status().isOk())
				.andExpect(jsonPath("$.code", equalTo("P002"))).andExpect(jsonPath("$.name", equalTo("Sprite 1L")))
				.andExpect(jsonPath("$.price", equalTo(16.0)));
	}

}
