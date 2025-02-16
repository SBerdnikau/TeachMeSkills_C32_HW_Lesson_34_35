import dao.BookDAO;
import dao.ReservationDAO;
import dao.UserDAO;
import model.Book;
import model.Reservation;
import model.User;

import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        System.setProperty("java.util.logging.config.file", "src/main/resources/logging.properties");
        try {
            UserDAO userDAO = new UserDAO();
            User user = new User(1, "Mihail", "mahail@gmail.com");
            userDAO.insertUser(user);

            BookDAO bookDAO = new BookDAO();
            Book book = new Book( 1,"Чистый код. Создание, анализ и рефакторин", "Роберт Мартин", 2015);
            bookDAO.insertBook(book);

            ReservationDAO reservationDAO = new ReservationDAO();
            Reservation reservation = new Reservation( 1, book.getId(), user.getId(), Date.valueOf("2025-01-05"), Date.valueOf("2025-06-01"));
            reservationDAO.insertReservation(reservation);

            System.out.println("The last registered used is -> " + userDAO.getLastRegisteredUser());

            System.out.println("The borrowed book is -> " + reservationDAO.getLastBorrowedBook());

            List<Book> books = bookDAO.getAllBooks();
            System.out.println("The list all books");
            for (Book b : books) {
                System.out.println("Id -> " + b.getId() + "\tTitle -> " + b.getTitle() + "\tAuthor -> " + b.getAuthor() + "\tPublicationYear -> " + b.getPublicationYear());
            }

        }catch (Exception e){
            LOGGER.log(Level.SEVERE,"General exception -> " + e.getMessage());
        }

    }
}
