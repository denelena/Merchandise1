package ru.netology.storage;

import ru.netology.domain.Product;

public class Repository {
    private Product[] items = new Product[0];

    public Repository() {
    }

    public void add(Product newItem) {
        //По-хорошему, сначала надо проверить, не хранится ли уже в репозитории продукт с таким же айди.
        //Но в задании об этом ни слова, так что подразумеваем, что все id уникальны.
        Product[] modifiedStorage = new Product[items.length + 1];
        System.arraycopy(items, 0, modifiedStorage, 0, items.length);
        modifiedStorage[items.length] = newItem;
        items = modifiedStorage;
    }

    public Product[] findAll() {
        Product[] temp = new Product[items.length];
        System.arraycopy(items, 0, temp, 0, items.length);
        return temp;
    }
    public void removeItemById(int id) {
        //if product with given id is not found, do nothing with items array.
        //These 2 lines also handle the case if repository is empty.
        if(!isExisting(id))
            return;


        //now we know for sure, that we will modify items array:
        int length = items.length - 1;
        Product[] tmp = new Product[length];
        int index = 0;
        for (Product item : items) {
            if (item.getId() != id) {
                tmp[index] = item;
                index++;
            }
        }
        items = tmp;
    }
    private boolean isExisting(int idToCheck) {
        for (Product currProduct:items) {
            if(currProduct.getId() == idToCheck)
                return true;
        }
        return false;
    }
}
