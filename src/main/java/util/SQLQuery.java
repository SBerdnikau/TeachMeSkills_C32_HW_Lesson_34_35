package util;

public interface SQLQuery {
    String INSERT_USER = "INSERT INTO users(name, email) VALUES(?,?)";
    String SELECT_USER = "SELECT * FROM users WHERE name = ?";
}
