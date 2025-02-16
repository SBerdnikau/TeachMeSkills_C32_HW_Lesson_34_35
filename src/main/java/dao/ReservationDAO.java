package dao;

import connection.ConnectionDB;
import exception.ReservationDAOException;
import model.Reservation;
import util.SQLQuery;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservationDAO {
    private final static Logger LOGGER = Logger.getLogger(ReservationDAO.class.getName());

    public void insertReservation(Reservation reservation) throws ReservationDAOException {
        try(Connection connection = ConnectionDB.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            try(PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.INSERT_RESERVATION)) {
             preparedStatement.setLong(1, reservation.getBookId());
             preparedStatement.setLong(2,reservation.getUserId());
             preparedStatement.setDate(3,reservation.getReservationDate());
             preparedStatement.setDate(4,reservation.getReturnDate());
             preparedStatement.executeUpdate();

             LOGGER.log(Level.INFO, "Inserting reservation with id: " + reservation.getId());
             connection.commit();
            }catch (SQLException e){
                connection.rollback();
                LOGGER.log(Level.SEVERE, "Reservation is not inserting -> " + e.getMessage());
                throw new ReservationDAOException("Reservation is not inserting -> " + e.getMessage());
            }
        }catch (SQLException | ReservationDAOException e) {
            LOGGER.log(Level.SEVERE, "Database connect error ", e);
            throw new ReservationDAOException("Database connect error " + e.getMessage());
        }
    }

    public Reservation getReservationById(long id) throws ReservationDAOException {
        try(Connection connection = ConnectionDB.getInstance().getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.SELECT_RESERVATION_BY_ID)) {
                preparedStatement.setLong(1, id);
                ResultSet result = preparedStatement.executeQuery();
                if(result.next()) {
                    return new Reservation(result.getLong("id"),result.getLong("book_id"), result.getLong("user_id"), result.getDate("reservation_date"), result.getDate("return_date"));
                }
            }
        }catch (SQLException e){
            LOGGER.log(Level.SEVERE, "Database connect error ", e);
            throw new ReservationDAOException("Database connect error " + e.getMessage());
        }
        return null;
    }

    public String getLastBorrowedBook() throws ReservationDAOException {
        try(Connection connection = ConnectionDB.getInstance().getConnection()) {
            try(CallableStatement callableStatement = connection.prepareCall(SQLQuery.SELECT_FUNCTION_RESERVATION_LAST_BOOK)) {
               ResultSet result = callableStatement.executeQuery();
               if(result.next()) {
                  String bookTitle = result.getString("book_title");
                  int publicationYear = result.getInt("publication_year");
                  String username = result.getString("username");

                  if (!bookTitle.isEmpty() && publicationYear > 0 && username != null) {
                      return "The last borrowed book is " + bookTitle + ", year publication " + publicationYear + " and reader " + username;
                  }else {
                      return "Not books borrowed";
                  }

               }
            }
        }catch (SQLException e){
            LOGGER.log(Level.SEVERE, "Database connect error ", e);
            throw new ReservationDAOException("Database connect error " + e.getMessage());
        }
        return "Not books borrowed";
    }
}
