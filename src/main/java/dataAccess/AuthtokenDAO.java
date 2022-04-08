package dataAccess;

import model.Authtoken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * our AuthtokenDAO class connects our SQL database with our Model Authtoken class
 * It will have a private Connection object as a data member
 * It will be with these functions we will add, find, and delete particular authtokens in our SQL database
 */
public class AuthtokenDAO {

    /**
     * for connecting to our SQL database
     */
    private final Connection conn;

    /**
     * constructor for our AuthtokenDAO class
     * we are initializing our connection with the SQL database
     * @param conn
     */
    public AuthtokenDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     *
     * @param authtoken
     * inserts an authtoken into the SQL table
     * @throws DataAccessException
     */
    public void insert(Authtoken authtoken) throws DataAccessException {
        String sql = "INSERT INTO authtoken (token, username) VALUES(?,?)";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, authtoken.getAuthtoken());
            stmt.setString(2, authtoken.getUsername());
            stmt.executeUpdate();
        }catch (SQLException e){
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }
    //returns username based off authtoken
    public String findUser(String token) throws DataAccessException{
        ResultSet rs;
        String sql = "SELECT username FROM authtoken WHERE token = ?;";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, token);
            rs = stmt.executeQuery();
            if(rs != null){
                return rs.getString("username");
            }else{
                return null;
            }
        }catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Invalid Authtoken");
//            throw new DataAccessException("Error encountered while finding associated username in authtoken database");
        }
    }

    /**
     * deletes all authtokens associated with a userName
     * @throws DataAccessException
     */
    public void clear() throws DataAccessException{
        String sql = "DELETE FROM authtoken";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the authtoken table");
        }
    }
}
