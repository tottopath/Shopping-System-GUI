package javaproj.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javaproj.model.Product;



public class ShoppingCart {

	private Map<Product, Integer> content;
	private float totalPrice; 
	
	public ShoppingCart() {
		content = new HashMap<Product, Integer>();
		totalPrice = 0;
	}
	
	public Map<Product, Integer> getContent() {
		return content;
	}


	public float getTotalPrice() {
		totalPrice = 0;
        for (Map.Entry<Product, Integer> entry: content.entrySet()) {
        	Product p = entry.getKey();
        	float productTotalPrice = computePriceForProductByQuantity(p);
        	totalPrice += productTotalPrice;
        }

		return round(totalPrice, 2);
	}

	public static float round(float d, int decimalPlace) {
		BigDecimal bd = new BigDecimal(Float.toString(d));
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.floatValue();
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		if (content.size() > 0) {
			for (Map.Entry<Product, Integer> entry: content.entrySet()) {
	        	Product p = entry.getKey();
	        	float productTotalPrice = computePriceForProductByQuantity(p);
	        	sb.append("Product: ").append(p.toString())
	        	.append("\tQuantity: ").append(entry.getValue())
	        	.append("\tProduct TotalPrice: ").append(productTotalPrice)
	        	.append("\n");
	        	
	        }

		} else {
		    sb.append("The cart is empty");
		}
		return sb.toString();
	}
	

	public float computePriceForProductByQuantity(Product p) {
		return p.getPrice() * content.get(p);
	}
	

	public void addProduct(Product p, int quantity) {
		content.put(p, quantity);
	}
	
}
