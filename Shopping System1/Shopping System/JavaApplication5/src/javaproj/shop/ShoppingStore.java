package javaproj.shop;

import java.io.FileNotFoundException;

import javaproj.model.Product;
import javaproj.service.ProductCatalog;
import javaproj.service.ShoppingCart;

public class ShoppingStore {

	private ProductCatalog catalog;
	private ShoppingCart shoppingCart;
	
	public ShoppingStore() {
		shoppingCart = new ShoppingCart();
		catalog = new ProductCatalog();
	}
	
	public ProductCatalog getCatalog() {
		return catalog;
	}
	
	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}
	

	public boolean existsInShoppingCart(Product p) {
		return shoppingCart.getContent().containsKey(p);
	}
	

    public void loadProducts() {
    	try {
			catalog.loadProducts();
		} catch (FileNotFoundException ex) {
			System.err.println(ex.getMessage());
			System.exit(0);
		}
    }


    public void listProducts() {
    	System.out.println("List products");
        Long[] productIdArray = catalog.getProducts().keySet().toArray(new Long[catalog.getProducts().size()]);
        for (Long productId: productIdArray) {
        	System.out.println(catalog.getProducts().get(productId));
        }
    }


    public void addProductToBasket(int productId) throws IllegalStateException {
        if (catalog.existsInProductCatalogue(productId)) {
        	Product p = catalog.getProduct(productId);
        	if (!shoppingCart.getContent().containsKey(p)) {
        		shoppingCart.addProduct(p, 1);
        	} else {
        		int quantity = shoppingCart.getContent().get(p);
        		shoppingCart.addProduct(p, quantity + 1);
        	}
        } else { 
        	throw new IllegalArgumentException("Product with id: " + productId + " is not in catalog. It cannot be added to the basket. Use List command to list the valid products.");
        }
        Product p = catalog.getProduct(productId);
        System.out.println("The quantity of product with id: " + productId + " is: " + shoppingCart.getContent().get(p));
    }


    public void removeProductFromBasket(int productId) throws IllegalStateException {
        if (catalog.existsInProductCatalogue(productId)) {
        	Product p = catalog.getProduct(productId);
        	if (shoppingCart.getContent().containsKey(p)) {
        		int quantity = shoppingCart.getContent().get(p);
        		if (quantity > 1) {
        			shoppingCart.getContent().put(p, quantity - 1);
        		} else { // quantity == 1 
        			shoppingCart.getContent().remove(p);
        		}
        	} else {
        		throw new IllegalArgumentException("Product with id: " + productId + " is not in the shopping cart. It cannot be removed.");
        	}
        } else {
            throw new IllegalArgumentException("Product with id: " + productId + " is not in catalog. It cannot be removed from the basket. Use List command to list the valid products.");
        }
        Product p = catalog.getProduct(productId);
        if (shoppingCart.getContent().get(p) != null) {
            System.out.println("The quantity of product with id: " + productId + " is: " + shoppingCart.getContent().get(p));
        } else {
            System.out.println("The quantity of product with id: " + productId + " has reached 0. The product was removed from the shopping cart.");
        }
    }


    public void getTotal() {
		System.out.println(shoppingCart.getTotalPrice());
        System.out.println("Shopping cart total price: " + shoppingCart.getTotalPrice() + " Rupees");
        System.out.println("Shopping cart content list: \n" + shoppingCart.toString());
    }

	public String totalProducts()
	{
		String str1 = String.valueOf(shoppingCart.toString());
		return str1;
	}

	public String totalPrice()
	{
		String str = String.valueOf(shoppingCart.getTotalPrice());
		return str;
	}

}
