package util;

public interface SQLQuery {
    String INSERT_USER = "INSERT INTO users(name, email) VALUES(?,?)";
    String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    String SELECT_FUNCTION_LAST_REG_USER = "{CALL get_last_registered_user()}";
    String INSET_BOOK = "INSERT INTO books(title, author, publication_year) VALUES(?, ?, ?)";
    String SELECT_BOOK_BY_ID = "SELECT * FROM books WHERE id = ?";
    String SELECT_FUNCTION_ALL_BOOKS = "{CALL get_all_books()}";
    String INSERT_RESERVATION = "INSERT INTO reservations(book_id, user_id, reservation_date, return_date) VALUES(?, ?, ?, ?)";
    String SELECT_RESERVATION_BY_ID = "SELECT * FROM reservations WHERE id = ?";
    String SELECT_FUNCTION_RESERVATION_LAST_BOOK = "{CALL get_last_borrowed_book() }";
}
