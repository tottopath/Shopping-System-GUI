package javaproj.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javaproj.model.Product;

public class ProductCsvLoader {

    public final static String filename = "/product-data.csv";
    
    public static Map<Long, Product> loadProducts() throws FileNotFoundException {
        Map<Long, Product> products = new HashMap<Long, Product>();

        InputStream in = ProductCsvLoader.class.getResourceAsStream(filename);
        if (in != null) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"))) {
                String line;
                br.readLine();
                while ((line = br.readLine()) != null) {
                    try {
                        Product p = new FromCsv(line).create();
                        products.put(p.getId(), p); 
                    } catch (NumberFormatException exc) {
                        System.err.println("Error the csv line: " + line + " cannot be parsed.");
                    }
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
                
            } 
        } else {
            throw new FileNotFoundException("The file /resources/product-data.csv cannot be found.");
        }
        return products;
    }
    

    public static class FromCsv {
        
        private String[] tokens;
        
        public FromCsv(String csvData) {
            tokens = csvData.split(",");
            for (int i = 0; i < tokens.length; i ++) {
                tokens[i] = tokens[i].trim();
            }
        }

        public Product create() throws NumberFormatException {
            int id;
            String name;
            float price;
            
            id = Integer.parseInt(tokens[0].trim());
            name = tokens[1];
            price = Float.parseFloat(tokens[2].trim().substring(1, tokens[2].length()));
            
            return new Product(id, name, price);
        }
    }
}
