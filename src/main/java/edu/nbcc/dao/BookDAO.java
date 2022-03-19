package edu.nbcc.dao;

import edu.nbcc.model.Book;

import java.util.List;

public interface BookDAO {

    public int delete(int d);

    public int insert(Book book);

    public int update (Book book);

    public List<Book> findAll();

    public Book findByName(String name);

    public Book findById(int id);
}
