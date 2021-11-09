package javaproj.service;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javaproj.model.Product;


public class ProductCatalog {

	Map<Long, Product> products;

	public ProductCatalog() {
		products = new HashMap<Long, Product>();
	}
	
	public Map<Long, Product> getProducts() {
		return products;
	}
	
	public boolean existsInProductCatalogue(long productId) {
		return products.containsKey(productId);
	}
	
	public int getSize() {
		return products.size();
	}
	

	public void loadProducts() throws FileNotFoundException {
		products = ProductCsvLoader.loadProducts();
	}

	public Product getProduct(long productId) {
		return products.get(productId);
	}
}
