package com.organicstore.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.organicstore.exception.ResourceNotFoundException;
import com.organicstore.model.Admin;
import com.organicstore.model.Product;
import com.organicstore.repository.AdminRepository;
import com.organicstore.repository.ProductRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ProductController {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired AdminRepository adminRepository;

	@GetMapping("/products/Admin")
	public List<Product> getAdminProducts() {
		return productRepository.findAll();
	}

	@GetMapping("/products/cust")
	public List<Product> getAllProducts() {
		List<Product> prodList=productRepository.findIfAvail();
		if(prodList.isEmpty()) {
			List<Admin> adminList = adminRepository.findAll();
			if(adminList.isEmpty()) {
				adminRepository.save(new Admin("admin","password"));
			}
			addProdIfEmpty(new Product(1,"Arbi","","FarmfreshVegetables",30,0,0,"yes","https://www.liveorganic.co.in/uploads/large/43_large.jpg"));
			addProdIfEmpty(new Product(2,"Bell pepper bulk pack","","FarmfreshVegetables",360,10,0,"yes","https://www.liveorganic.co.in/uploads/large/2326_large.png"));
			addProdIfEmpty(new Product(3,"Bhindi","","FarmfreshVegetables",65,0,0,"yes","https://www.liveorganic.co.in/uploads/large/45_large.jpg"));
			addProdIfEmpty(new Product(4,"Brinjal","","FarmfreshVegetables",65,0,0,"yes","https://www.liveorganic.co.in/uploads/large/520_large.jpg"));
			addProdIfEmpty(new Product(5,"Broccoli"," ","FarmfreshVegetables",175,5,0,"yes","https://www.liveorganic.co.in/uploads/large/75_large.jpg"));
			addProdIfEmpty(new Product(6,"Cabbage"," ","FarmfreshVegetables",80,0,0,"yes","https://www.liveorganic.co.in/uploads/large/46_large.jpg"));
			addProdIfEmpty(new Product(7,"apple gala","","seasonalfruits",80,0,0,"yes","https://www.liveorganic.co.in/uploads/large/2297_large.jpg"));
            addProdIfEmpty(new Product(8,"pears ","","seasonalfruits",80,0,0,"yes","https://www.liveorganic.co.in/uploads/large/1594_large.jpg"));
			addProdIfEmpty(new Product(9,"paneer ","","dairy",80,0,0,"yes","https://www.liveorganic.co.in/uploads/large/2471_large.jpg"));
			addProdIfEmpty(new Product(10,"milk","","dairy",80,0,0,"yes","https://provilac.com/img/inner/tgs.jpg"));
			addProdIfEmpty(new Product(11," cerals ","","groceries",80,0,0,"yes","https://www.liveorganic.co.in/uploads/large/2450_large.jpg"));
			addProdIfEmpty(new Product(12,"flours ","","groceries",80,0,0,"yes","https://www.liveorganic.co.in/uploads/large/2439_large.jpg"));
			
			
			prodList=productRepository.findIfAvail();
		}
		return prodList;
	}
	
	public void addProdIfEmpty(Product product) {
		int min = 10000;
		int max = 99999;
		int b = (int) (Math.random() * (max - min + 1) + min);
		product.setId(b);
		float temp = (product.getActualPrice()) * (product.getDiscount() / 100);
		float price = product.getActualPrice() - temp;
		product.setPrice(price);
		productRepository.save(product);
	}

	@PostMapping("/products")
	public Product addProduct(@RequestBody Product product) {
		int min = 10000;
		int max = 99999;
		int b = (int) (Math.random() * (max - min + 1) + min);
		product.setId(b);
		float temp = (product.getActualPrice()) * (product.getDiscount() / 100);
		float price = product.getActualPrice() - temp;
		product.setPrice(price);
		return productRepository.save(product);
	}
	
	@PutMapping("/products/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails){
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee Not Found with " + id));
		product.setName(productDetails.getName());
		product.setCategory(productDetails.getCategory());
		product.setImagepath(productDetails.getImagepath());
		product.setActualPrice(productDetails.getActualPrice());
		product.setDiscount(productDetails.getDiscount());
		product.setAvail(productDetails.getAvail());
		float temp = (product.getActualPrice()) * (product.getDiscount() / 100);
		float price = product.getActualPrice() - temp;
		product.setPrice(price);
		
		Product updatedProd = productRepository.save(product);
		return ResponseEntity.ok(updatedProd);
		
	}

	@DeleteMapping("/products/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteProduct(@PathVariable Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee Not Found with " + id));
		productRepository.delete(product);
		Map<String, Boolean> map = new HashMap<>();
		map.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(map);
	}

	@GetMapping("products/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product Not Found with " + id));
		return ResponseEntity.ok(product);
	}

	@GetMapping("products/search/{keyword}")
	public List<Product> getSearchProducts(@PathVariable String keyword) {
		return productRepository.homeSearch(keyword);
	}

	@GetMapping("products/FarmfreshVegetables")
	public List<Product> getFarmfreshVegetables() {
		return productRepository.getFarmfreshVegetables();
	}

	@GetMapping("products/seasonalfruits")
	public List<Product> getseasonalfruits() {
		return productRepository.getseasonalfruits();
	}

	@GetMapping("products/dairy")
	public List<Product> getdairy() {
		return productRepository.getdairy();
	}

	@GetMapping("products/groceries")
	public List<Product> getgroceries() {
		return productRepository.getgroceries();
	}
}