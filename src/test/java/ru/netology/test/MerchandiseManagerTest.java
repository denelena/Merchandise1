package ru.netology.test;

import org.junit.jupiter.api.Test;
import ru.netology.domain.*;
import ru.netology.manager.MerchandiseManager;
import ru.netology.storage.Repository;

import static org.junit.jupiter.api.Assertions.*;

public class MerchandiseManagerTest {
    private Repository repository = new Repository();

    private Book book01 = new Book(10, "Uncle Tom's Cabin", "Harriet Beecher Stowe", 19);
    private Book book02 = new Book(20, "Alice's Adventures in Wonderland", "Lewis Carroll", 25);
    private Book book03 = new Book(30, "Do Androids Dream of Electric Sheep?", "Philip K. Dick", 60);
    private Book book04 = new Book(50, "The Hitchhiker's Guide to the Galaxy", "Douglas Adams", 42);

    private Smartphone phone01 = new Smartphone(1000, "Galaxy S8", "Samsung", 460);
    private Smartphone phone02 = new Smartphone(2000, "iPhone Pro", "Apple", 1300);
    private Smartphone phone03 = new Smartphone(3000, "īPhone Gro", "Uncle Tzao's factory", 125);

    @Test
    public void shouldAddBooks() {
        MerchandiseManager mm = new MerchandiseManager(repository);

        mm.add(book01);
        mm.add(book02);
        mm.add(book03);
        mm.add(book04);

        Product[] expected = new Product[]{book01, book02, book03, book04};
        assertArrayEquals(expected, repository.findAll());
    }

    @Test
    public void shouldAddPhones() {
        MerchandiseManager mm = new MerchandiseManager(repository);

        mm.add(phone01);
        mm.add(phone02);
        mm.add(phone03);

        Product[] expected = new Product[]{phone01, phone02, phone03};
        assertArrayEquals(expected, repository.findAll());
    }

    @Test
    public void shouldAddBookAndPhone() {
        MerchandiseManager mm = new MerchandiseManager(repository);
        mm.add(book01);
        mm.add(phone01);

        Product[] expected = new Product[]{book01, phone01};
        assertArrayEquals(expected, repository.findAll());
    }

    @Test
    public void shouldAddNothing() {
        MerchandiseManager mm = new MerchandiseManager(repository);
        Product[] expected = new Product[0];
        assertArrayEquals(expected, repository.findAll());
    }

    @Test
    public void shouldAddAndRemoveOne() {
        repository.add(book01);
        repository.add(book02);
        repository.add(book03);
        repository.add(book04);
        repository.add(phone01);
        repository.add(phone02);
        repository.add(phone03);

        repository.removeItemById(50); //book04 removed
        Product[] expected = new Product[]{book01, book02, book03, phone01, phone02, phone03};
        assertArrayEquals(expected, repository.findAll());
    }

    @Test
    public void shouldAddAndRemoveSeveral() {
        repository.add(book01);
        repository.add(book02);
        repository.add(book03);
        repository.add(book04);
        repository.add(phone01);
        repository.add(phone02);
        repository.add(phone03);

        repository.removeItemById(50); //book04 removed
        repository.removeItemById(10); //book01 removed, from the beginning
        repository.removeItemById(3000); //phone03 removed, from the end

        Product[] expected = new Product[]{book02, book03, phone01, phone02};
        assertArrayEquals(expected, repository.findAll());
    }

    @Test
    public void shouldAddAndRemoveAll() {
        repository.add(book01);
        repository.add(book04);
        repository.add(phone03);

        repository.removeItemById(50); //book04 removed
        repository.removeItemById(10); //book01 removed, from the beginning
        repository.removeItemById(3000); //phone03 removed, from the end

        Product[] expected = new Product[0];
        assertArrayEquals(expected, repository.findAll());
    }

    @Test
    public void shouldAddAndTryRemoveNonExisting() {
        repository.add(book01);
        repository.add(book04);
        repository.add(phone03);

        repository.removeItemById(512); //no such item

        Product[] expected = new Product[]{book01, book04, phone03};
        assertArrayEquals(expected, repository.findAll());
    }

    @Test
    public void shouldTryRemoveFromEmpty() {
        repository.removeItemById(10); //no such item
        Product[] expected = new Product[0];
        assertArrayEquals(expected, repository.findAll());
    }

    @Test
    public void shouldAddSearchAndFindNothing() {
        MerchandiseManager mm = new MerchandiseManager(repository);

        mm.add(book01);
        mm.add(book02);
        mm.add(book03);
        mm.add(book04);
        mm.add(phone01);
        mm.add(phone02);
        mm.add(phone03);

        Product[] found = mm.searchBy("Однаждывстуденуюзимнуюпору");
        Product[] expected = new Product[0];
        assertArrayEquals(expected, found);
    }

    @Test
    public void shouldAddSearchAndFindOneOnTitle() {
        MerchandiseManager mm = new MerchandiseManager(repository);

        mm.add(book01);
        mm.add(book02);
        mm.add(book03);
        mm.add(book04);
        mm.add(phone01);
        mm.add(phone02);
        mm.add(phone03);

        Product[] found = mm.searchBy("Android");
        Product[] expected = new Product[]{book03};
        assertArrayEquals(expected, found);

        found = mm.searchBy("iPhone");
        expected = new Product[]{phone02};
        assertArrayEquals(expected, found);
    }

    @Test
    public void shouldAddSearchAndFindOneOnModel() {
        MerchandiseManager mm = new MerchandiseManager(repository);

        mm.add(book01);
        mm.add(book02);
        mm.add(book03);
        mm.add(book04);
        mm.add(phone01);
        mm.add(phone02);
        mm.add(phone03);

        Product[] found = mm.searchBy("iPhone");
        Product[] expected = new Product[]{phone02};
        assertArrayEquals(expected, found);
    }

    @Test
    public void shouldAddSearchAndFindOneOnAuthor() {
        MerchandiseManager mm = new MerchandiseManager(repository);

        mm.add(book01);
        mm.add(book02);
        mm.add(book03);
        mm.add(book04);
        mm.add(phone01);
        mm.add(phone02);
        mm.add(phone03);
        mm.add(new Product(999, "Сепульки", 1)); // чтоб пройти по бранчу - neither Book, nor Smartphone

        Product[] found = mm.searchBy("Lewis");
        Product[] expected = new Product[]{book02};
        assertArrayEquals(expected, found);
    }

    @Test
    public void shouldAddSearchAndFindOneOnManufacturer() {
        MerchandiseManager mm = new MerchandiseManager(repository);

        mm.add(book01);
        mm.add(book02);
        mm.add(book03);
        mm.add(book04);
        mm.add(phone01);
        mm.add(phone02);
        mm.add(phone03);
   
        Product[] found = mm.searchBy("Apple");
        Product[] expected = new Product[]{phone02};
        assertArrayEquals(expected, found);
    }


    @Test
    public void shouldAddSearchAndFindTwo() {
        MerchandiseManager mm = new MerchandiseManager(repository);

        mm.add(book01);
        mm.add(book02);
        mm.add(book03);
        mm.add(book04);
        mm.add(phone01);
        mm.add(phone02);
        mm.add(phone03);

        Product[] found = mm.searchBy("Uncle");
        Product[] expected = new Product[]{book01, phone03};
        assertArrayEquals(expected, found);
    }

    @Test
    public void shouldAddAndSearchCaseInsensitive() {
        MerchandiseManager mm = new MerchandiseManager(repository);

        mm.add(book01);
        mm.add(book02);
        mm.add(book03);
        mm.add(book04);
        mm.add(phone01);
        mm.add(phone02);
        mm.add(phone03);

        Product[] found = mm.searchBy("gAlaXy");
        Product[] expected = new Product[]{book04, phone01};
        assertArrayEquals(expected, found);
    }
}
