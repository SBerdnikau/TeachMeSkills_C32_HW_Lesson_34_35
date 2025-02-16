package dao;

import connection.ConnectionDB;
import exception.BookDAOException;
import model.Book;
import util.SQLQuery;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookDAO {
    private static final Logger LOGGER = Logger.getLogger(BookDAO.class.getName());

    public void insertBook(Book book) throws BookDAOException {
        try(Connection connection = ConnectionDB.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            try(PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.INSET_BOOK)) {
                preparedStatement.setString(1, book.getTitle());
                preparedStatement.setString(2, book.getAuthor());
                preparedStatement.setInt(3, book.getPublicationYear());
                preparedStatement.executeUpdate();

                LOGGER.log(Level.INFO, "Book is added successfully -> ", book.getTitle());
                connection.commit();
            }catch (SQLException e) {
                connection.rollback();
                LOGGER.log(Level.SEVERE,"Error in inserting book",e.getMessage());
                throw new BookDAOException("Error in inserting book");
            }
        } catch (SQLException e) {
            throw new BookDAOException("Database connect error" + e.getMessage());
        }
    }

    public Book getBookById(long id) throws BookDAOException {
        try(Connection connection = ConnectionDB.getInstance().getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.SELECT_BOOK_BY_ID)){
                preparedStatement.setLong(1, id);
                ResultSet result = preparedStatement.executeQuery();
                if(result.next()) {
                    return new Book(result.getLong("id"), result.getString("title"), result.getString("author"), result.getInt("publication_year"));
                }
                LOGGER.log(Level.INFO, "Book is found -> ", id);
            }
        } catch (SQLException e) {
            throw new BookDAOException("Database connect error" + e.getMessage());
        }
        return null;
    }

    public List<Book> getAllBooks() throws BookDAOException {
        List<Book> books = new ArrayList<Book>();
        try(Connection connection = ConnectionDB.getInstance().getConnection()) {
            try(CallableStatement callableStatement = connection.prepareCall(SQLQuery.SELECT_FUNCTION_ALL_BOOKS)) {
                ResultSet result = callableStatement.executeQuery();
                while(result.next()) {
                    books.add(new Book(result.getLong("id"),
                            result.getString("title"),
                            result.getString("author"),
                            result.getInt("publication_year")));
                }
            }
        }catch (SQLException e){
            throw new BookDAOException("Database connect error" + e.getMessage());
        }
        return books;
    }
}
