package edu.nbcc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Book {
    private int id;
    private String name;
    private double price;
    private int term;

    public Book() {
        super();
    }

    public Book(int id, String name, double price, int term) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
        this.term = term;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    private List<Book> mockData = new ArrayList<Book>();

    private void buildMockData() {
        mockData.add(new Book(1, "Book 1", 1.00, 1));
        mockData.add(new Book(2, "Book 2", 5.77, 2));
        mockData.add(new Book(3, "Book 3", 66.66, 3));
        mockData.add(new Book(4, "Book 4", 29.99, 4));
        mockData.add(new Book(5, "Book 5", 69.69, 5));
    }

    public List<Book> getBooks() {
        buildMockData();
        return this.mockData;
    }

    public Book getBook(int id) {
        List<Book> book = getBooks().stream().filter(b -> b.getId() == id).collect(Collectors.toList());
        if (book.size() > 0) {
            return book.get(0);
        }
        return null;
    }

    public Book create() {
        this.id = this.mockData.size() + 1;
        return this;
    }

    public int saveBook() {
        if (getBook(this.id) != null) {
            return 1;
        }
        return 0;
    }

    public int deleteBook() {
        if (getBook(this.id) != null) {
            return 1;
        }
        return 0;
    }

}
