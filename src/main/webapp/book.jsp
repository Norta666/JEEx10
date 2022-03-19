<%-- 
    Document   : book
    Created on : Jan 21, 2020, 7:47:23 AM
    Author     : Chris.Cusack


    This view supports a retrieved book or creation of a new book

--%>

<%@page import="edu.nbcc.model.ErrorModel" %>
<%@page import="edu.nbcc.model.BookModel" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <%@include file="WEB-INF/jspf/header.jspf" %>
    <title>Book</title>
</head>
<body class="p-4">
<%@include file="WEB-INF/jspf/navigation.jspf" %>
<h2>Book</h2>

<%
    if (request.getAttribute("vm") != null) {
        BookModel vm = (BookModel) request.getAttribute("vm");
        String bookName = "";
        int bookId = 0;
        double bookPrice = 0;

        if (vm.getBook().getName() != null) {
            bookName = vm.getBook().getName();
        }

        if (vm.getBook().getId() != 0) {
            bookId = vm.getBook().getId();
        }

        if (vm.getBook().getPrice() != 0) {
            bookPrice = vm.getBook().getPrice();
        }
%>
<form method="POST" action="save">
    <table class="table">
        <!-- Display details in view mode -->

        <tr>
            <td><label>Book Id:</label></td>
            <td><input type="hidden" value='<%= bookId %>' name="hdnBookId"/><%= bookId %>
            </td>
        </tr>


        <tr>
            <td>Book Name:</td>
            <td><input type="text" name="bookName" value='<%= bookName %>'/></td>
        </tr>
        <tr>
            <td>Book Price:</td>
            <td><input type="text" name="bookPrice" value='<%= bookPrice %>'/></td>
        </tr>
        <tr>
            <td>Term:</td>
            <td>
                <%
                    for (int i : vm.getTerms()) {
                        String chk = "";
                        if (vm.getBook() != null && vm.getBook().getTerm() == i) {
                            chk = "checked";
                        } %>
                <input type="radio" name="bookTerm" value="<%= i %>" <%= chk %>/> Term <%= i %>
                <% } %>
            </td>
        </tr>
    </table>

    <%
        if (vm.getBook() != null && vm.getBook().getId() > 0) {
    %>

    <input class="btn btn-primary" type="submit" value="delete" name="action"/>
    <input class="btn btn-primary" type="submit" value="save" name="action"/>
    <%
    } else {
    %>
    <input class="btn btn-primary" type="submit" value="create" name="action"/>
    <%
        }
    %>
</form>
<%
    }
%>
<!--Set up errors here -->

<%
    if (request.getAttribute("error") != null) {
        ErrorModel em = (ErrorModel) request.getAttribute("error");
        if (em.getErrors() != null && em.getErrors().size() > 0) {
%>

<ul class="alert alert-danger">

    <%
        for (String err : em.getErrors()) {
    %>

    <li><%= err %>
    </li>
    <% } %>

</ul>

<%
        }
    }
%>
</body>
</html>
