package dataAccess;

import model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * the PersonDAO class connects our SQL database with our Model Event class
 * it has a private connection as a data member that allows it to connect with the server
 * through this class we will add, find and delete particular persons
 */

public class PersonDAO {
    /**
     * for connecting to our SQL database
     */
    private final Connection conn;
    /**
     * constructor for our PersonDAO class
     * we are initializing our connection with the SQL database
     * @param conn
     */
    public PersonDAO(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * inserts the given person into our Database
     * @param person
     * @throws DataAccessException
     * if a SQL error encountered well inserting throw DataAccessException
     */
    public void insert(Person person) throws DataAccessException {
        String sql = "INSERT INTO person (personID, associatedUsername, firstName, lastName, gender, " +
                "fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql) ){
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());
            stmt.executeUpdate();
        }catch (SQLException e){
            throw new DataAccessException("Error encountered well inserting into database");
        }

    }
    /**
     * updates a user in our database
     * use the personID from the passed in person object and update the associated person
     * @param person
     * @throws DataAccessException
     */
    public void update(Person person) throws DataAccessException{
        deletePerson(person.getPersonID());
        String sql = "INSERT INTO person (personID, associatedUsername, firstName, lastName, gender, " +
                "fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql) ){
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
        }catch (SQLException e){
            throw new DataAccessException("Error encountered well inserting into database");
        }
    }

    /**
     * finds a particular person using the personID
     * @param personID
     * @return
     * @throws DataAccessException
     */
    public Person find(String personID) throws DataAccessException{
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM person WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt. setString(1, personID);
            rs = stmt.executeQuery();
            if(rs.next()){
                person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
                return person;
            }

        }catch (SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Invalid PersonID parameter");
            //throw new DataAccessException("Error encountered while finding person ");
        }
        finally {
            if(rs != null){
                try{
                    rs.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * finds all the people associated with the associatedUsername
     * @param associatedUsername
     * @return
     * @throws DataAccessException
     */
    public List<Person> findForUser(String associatedUsername) throws DataAccessException{
        List<Person> listOfPersonsToReturn = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT * FROM person WHERE associatedUsername = ?;";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, associatedUsername);
            rs = stmt.executeQuery();
            while(rs.next()){
                Person person = new Person(rs.getString("personID"), associatedUsername,
                        rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("fatherID"),
                        rs.getString("motherID"), rs.getString("spouseID"));
                listOfPersonsToReturn.add(person);
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Error encountered while getting list of persons for user");
        }finally{
            if(rs != null){
                try{
                    rs.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return listOfPersonsToReturn;
    }

    public void clearUserPersons(String associatedUsername) throws DataAccessException{
        String sql = "DELETE FROM person WHERE associatedUsername = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt. setString(1, associatedUsername);
            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing all people associated with user");
        }
    }

    /**
     * deletes all people
     * @throws DataAccessException
     */
    public void clear() throws DataAccessException{
        String sql = "DELETE FROM person";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the person table");
        }
    }

    /**
     * deletes a particular person
     * @param personID
     * @throws DataAccessException
     */
    private void deletePerson(String personID ) throws DataAccessException {
        String sql = "DELETE FROM person WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing particular person from person table");
        }
    }

}
