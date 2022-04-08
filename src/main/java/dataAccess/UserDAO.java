package dataAccess;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * our UserDAO class connects our SQL database with our model user class
 * It will have a private Connection object as a data member
 * It will be with these functions we will add, find, and delete particular users in our SQL database
 */

public class UserDAO {
    /**
     * for connecting to our SQL database
     */
    private final Connection conn;


    /**
     * constructor for our UserDAO class
     * we are initializing our connection with the SQL database
     *
     * @param conn
     */
    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * insert users into our SQL database
     * throw a dataAccessException if an error occurs
     *
     * @param user
     * @throws DataAccessException
     */
    public void insert(User user) throws DataAccessException {
        //is users the right name for our username
        String sql = "INSERT INTO user (username, password, email, firstName, lastName, " +
                "gender, personID) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered well inserting into database, " +
                    "requested property missing or has invalid value");
        }
    }

    /**
     * when a user logs in we need to validate username and password
     * go to database nad check if there is a user with the name in the database and if its the right password
     *
     * @param username
     * @param password
     * @return
     */
    public boolean validate(String username, String password) throws DataAccessException {
        String sql = "SELECT * FROM user WHERE username = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.getString("password").toString().equals(password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("requested property missing or has invalid value");
        }
    }

    /**
     * query the database and find the user with the id
     * read the data out of the database
     * store the data in the usermodel object and return
     *
     * @param username
     * @return
     */
    public User find(String username) throws DataAccessException {
        User user;
        ResultSet rs = null;
        String sql = "SELECT * FROM user WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("username"), rs.getString("password"),
                        rs.getString("email"), rs.getString("firstName"),
                        rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("personID"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an user in the database");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new DataAccessException("Error, user is not in table ");
                }
            }
        }
        return null;
    }

    /**
     * clear the user table
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM user";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the user table");
        }
    }


}
