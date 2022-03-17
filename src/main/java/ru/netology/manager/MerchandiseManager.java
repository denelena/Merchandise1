package ru.netology.manager;

import ru.netology.domain.*;
import ru.netology.storage.Repository;

public class MerchandiseManager {
    private Repository repository;

    public MerchandiseManager(Repository r) {
        repository = r;
    }

    public void add(Product newItem) {
        repository.add(newItem);
    }

    public Product[] searchBy(String text) {
        Product[] results = new Product[0];
        for (Product currProduct : repository.findAll()) {
            if (matches(currProduct, text)) {
                Product[] expandedResults = new Product[results.length + 1];
                System.arraycopy(results, 0, expandedResults, 0, results.length);
                expandedResults[results.length] = currProduct;
                results = expandedResults;
            }
        }
        return results;
    }

    public boolean matches(Product product, String search) {
        //I believe we want case-insensitive match

        //match on Price, too! Although doesn't make much sense to match price as "contains", because, for example, $10, $100, $1000 - all contain "1" and "0".
        // it would be more reasonable to find exact price match
        if (String.valueOf(product.getPrice()).equals(search))
            return true;

        if (product instanceof Book) {
            Book book = (Book) product;
            if (book.getAuthor().toLowerCase().contains(search.toLowerCase())) {
                return true;
            }
            if (book.getTitle().toLowerCase().contains(search.toLowerCase())) {
                return true;
            }
            return false;
        }

        if (product instanceof Smartphone) {
            Smartphone phone = (Smartphone) product;
            if (phone.getModel().toLowerCase().contains(search.toLowerCase())) {
                return true;
            }
            if (phone.getManufacturer().toLowerCase().contains(search.toLowerCase())) {
                return true;
            }
            return false;
        }
        return false;// oops! neither Book, nor Smartphone :-)
    }
}
