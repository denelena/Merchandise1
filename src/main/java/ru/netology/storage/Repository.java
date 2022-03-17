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

    public boolean removeItemById(int idToRemove) {
        //Here we return true, if item is removed. We return false if item is not found
        int removeeIndex = getIndexIfExists(idToRemove);
        if (removeeIndex == -1) {
            return false; // Item with such id not found. Do nothing with items array
        }

        //now we know for sure that we will remove one
        Product[] modifiedStorage = new Product[items.length - 1];

        //easy cases: removing either from 0-th or from the last position:
        if (removeeIndex == 0) {
            System.arraycopy(items, 1, modifiedStorage, 0, items.length - 1);
        }

        if (removeeIndex == items.length - 1) {
            System.arraycopy(items, 0, modifiedStorage, 0, items.length - 1);
        }

        if (removeeIndex > 0 && removeeIndex < items.length - 1) {
            //need to copy 2 blocks, before removee and after
            System.arraycopy(items, 0, modifiedStorage, 0, removeeIndex);
            System.arraycopy(items, removeeIndex + 1, modifiedStorage, removeeIndex, items.length - 1 - removeeIndex);
        }
        items = modifiedStorage;
        return true;
    }

    private int getIndexIfExists(int idToCheck) {
        int foundAtIndex = -1;
        for (int currIndex = 0; currIndex < items.length; currIndex++) {
            if (items[currIndex].getId() == idToCheck) {
                foundAtIndex = currIndex;
                break;
            }
        }
        return foundAtIndex;
    }
}
