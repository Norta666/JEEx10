package edu.nbcc.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.nbcc.model.Book;
import edu.nbcc.model.BookModel;
import edu.nbcc.model.ErrorModel;
import edu.nbcc.util.ValidationUtil;

/**
 * Servlet implementation class BookController
 */
public class BookController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String BOOK_VIEW = "/books.jsp";
    private static final String CREATE_VIEW = "/book.jsp";
    private static final String BOOK_SUMMARY = "/bookSummary.jsp";

    private RequestDispatcher view;

    public RequestDispatcher getView() {
        return view;
    }

    public void setView(HttpServletRequest request, String viewPath) {
        view = request.getRequestDispatcher(viewPath);
    }

    public BookController() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Book book = new Book();
        String path = request.getPathInfo();
        if (path == null) {
            request.setAttribute("vm", book.getBooks());
            setView(request, BOOK_VIEW);
        } else {
            String[] destination = path.split("/");
            if (destination[1].equalsIgnoreCase("create") || ValidationUtil.isNumeric(destination[1])) {
                setView(request, CREATE_VIEW);
                int id = ValidationUtil.getInteger(destination[1]);
                if (id != 0) {
                    Book bk = book.getBook(id);
                    if (bk != null) {
                        BookModel vm = new BookModel();
                        vm.setBook(bk);
                        request.setAttribute("vm", vm);
                    } else {
                        request.setAttribute("error", new ErrorModel("Book not found"));
                    }
                } else {
                    request.setAttribute("vm", new BookModel());
                }

            }
        }
        getView().forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Book book = new Book();
        List<String> errors = new ArrayList<>();
        int bookId = 0;
        double bookPrice = 0;
        int termNum = 0;

        setView(request, BOOK_SUMMARY);

        try {
            if (request.getParameter("bookPrice") == null) {
                errors.add("Book price is required");
            }
            if (request.getParameter("bookName") == null) {
                errors.add("Book name is required");
            }
            if (request.getParameter("bookTerm") == null) {
                errors.add("Book term is required");
            }
            if (ValidationUtil.isNumeric(request.getParameter("hdnBookId"))) {
                bookId = ValidationUtil.getInteger(request.getParameter("hdnBookId"));
            } else {
                errors.add("Wrong Book Id");
            }

            String name = request.getParameter("bookName");
            if (name == null || name.equals("")) {
                errors.add("Name field cannot be null or blank");
            }

            bookPrice = ValidationUtil.getDouble(request, "bookPrice", errors);
            if (bookPrice == 0.0) {
                errors.add("Price must be greater than 0");
            }

            if (request.getParameter("bookTerm") != null) {
                if (ValidationUtil.isNumeric(request.getParameter("bookTerm"))) {
                    termNum = ValidationUtil.getInteger(request.getParameter("bookTerm"));
                } else {
                    errors.add("Invalid term");
                }
            } else {
                errors.add("Please select a term");
            }

            if (errors.size() == 0) {
                book.setId(bookId);
                book.setName(name);
                book.setPrice(bookPrice);
                book.setTerm(termNum);

                String action = request.getParameter("action");

                switch (action) {
                    case "create":
                        book.create();
                        request.setAttribute("createdBook", book);
                        break;
                    case "save":
                        if (book.saveBook() > 0) {
                            request.setAttribute("savedBook", book);
                        } else {
                            BookModel vm = new BookModel();
                            vm.setBook(book);
                            request.setAttribute("vm", vm);
                            request.setAttribute("error", new ErrorModel("Book does not exist"));
                            setView(request, CREATE_VIEW);
                        }

                        break;
                    case "delete":
                        if (book.deleteBook() > 0) {
                            request.setAttribute("deletedBook", book);
                        } else {
                            BookModel vm = new BookModel();
                            vm.setBook(book);
                            request.setAttribute("vm", vm);
                            request.setAttribute("error", new ErrorModel("Book does not exist"));
                            setView(request, CREATE_VIEW);
                        }
                }
            } else {
                BookModel vm = new BookModel();
                vm.setBook(book);
                setView(request, CREATE_VIEW);
                ErrorModel errorModel = new ErrorModel();
                errorModel.setErrors(errors);
                request.setAttribute("error", errorModel);
                request.setAttribute("vm", vm);
            }

        } catch (Exception e) {
            request.setAttribute("error", new ErrorModel("Something went wrong"));
            setView(request, CREATE_VIEW);
        }
        getView().forward(request, response);
    }

}
