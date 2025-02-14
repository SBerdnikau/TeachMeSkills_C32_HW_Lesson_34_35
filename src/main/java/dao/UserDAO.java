package dao;

import connection.ConnectionDB;
import exception.UserDAOException;
import model.User;
import util.SQLQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {
    private static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());

    public void insertUser(User user) throws UserDAOException {
        try(Connection connection = ConnectionDB.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            try(PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.INSERT_USER)) {
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getEmail());
                preparedStatement.executeUpdate();
                LOGGER.log(Level.INFO, "Insert user successfully");
                connection.commit();

            }catch (SQLException e){
                connection.rollback();
                LOGGER.log(Level.SEVERE, "Exception adding user ->" +  e.getMessage());
                throw new UserDAOException("Exception adding user ->" +  e.getMessage());
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database connect error ->" +  e.getMessage());
            throw new UserDAOException("Database connect error -> " + e);
        }
    }


    public User getUserByID(long id) throws UserDAOException {
        try(Connection connection = ConnectionDB.getInstance().getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.SELECT_USER)) {
                preparedStatement.setLong(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()) {
                    return new User(resultSet.getLong("id"), resultSet.getString("username"), resultSet.getString("email"));
                }
            }
        } catch (SQLException e) {
            throw new UserDAOException("Database connect error -> " +  e.getMessage());
        }
        return null;
    }

    public String getLastRegisteredUser() throws UserDAOException {
        try(Connection connection = ConnectionDB.getInstance().getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.SELECT_FUNCTION_USER)) {

            }
        } catch (SQLException e) {
            throw new UserDAOException("Database connect error -> " +  e.getMessage());
        }
        return null;
    }

}
