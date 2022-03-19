package edu.nbcc.dao;

import edu.nbcc.model.Book;

import java.sql.*;
import java.util.List;

public class BookDAOImpl implements BookDAO {

    private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/javaee10";
    private static final String USER_ID = "root";
    private static final String PASSWORD = "123456";

    private static final String DELETE = "DELETE FROM javaee10.book WHERE id=?";
    private static final String INSERT = "INSERT INTO javaee10.book (name, term, price) VALUES(?,?,?)";
    private static final String UPDATE = "UPDATE javaee10.book SET name=?, term=?, price=? WHERE id=?";
    private static final String FIND_ALL = "SELECT * FROM javaee10.book ORDER BY id";
    private static final String FIND_BY_ID = "SELECT * FROM javaee10.book WHERE id=?";
    private static final String FIND_BY_NAME = "SELECT * FROM javaee10.book WHERE name=?";

    private Connection getConnection() {
        try {
            Class.forName(DRIVER_NAME);
            return DriverManager.getConnection(DB_URL, USER_ID, PASSWORD);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public int delete(int id) {
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = getConnection();
            statement = conn.prepareStatement(DELETE);
            statement.setInt(1, id);
            return statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        } finally {
            close(conn);
            close(statement);
        }
    }

    @Override
    public int insert(Book book) {
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = getConnection();
            statement = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, book.getName());
            statement.setInt(2, book.getTerm());
            statement.setDouble(3, book.getPrice());
            int result = statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                book.setId(rs.getInt(1));
            }
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        } finally {
            close(conn);
            close(statement);
        }
    }

    @Override
    public int update(Book book) {
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = getConnection();
            statement = conn.prepareStatement(UPDATE);
            statement.setString(1, book.getName());
            statement.setInt(2, book.getTerm());
            statement.setDouble(3, book.getPrice());
            statement.setInt(4, book.getId());
            return statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        } finally {
            close(conn);
            close(statement);
        }
    }

    @Override
    public List<Book> findAll() {
        return null;
    }

    @Override
    public Book findByName(String name) {
        return null;
    }

    @Override
    public Book findById(int id) {
        return null;
    }
}
