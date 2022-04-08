package dataAccess;

import model.Event;
import model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * our EventDAO class connects our SQL database with our Model Event class
 * It will have a private Connection object as a data member
 * It will be with these functions we will add, find, and delete particular events in our SQL database
 */

public class EventDAO {
    /**
     * for connecting to our SQL database
     */
    private final Connection conn;

    /**
     * constructor for our EventDAO class
     * we are initializing our connection with the SQL database
     * @param conn
     */
    public EventDAO(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * This is already pretty built out but here is a short explanation of what this function does
     * insert events into our SQL database
     * we pass the function an event object
     * we then grab each of the event objects parameters and add them to a statement we will add into
     * a string that we will add to our SQL db
     * throws a dataAccessException if an error occurs
     * @param event
     * @throws DataAccessException
     */

    public void insert(Event event) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO event (eventID, associatedUsername, personID, latitude, longitude, " +
                "country, city, eventType, year) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getAssociatedUsername());
            stmt.setString(3, event.getPersonID());
            stmt.setFloat(4, event.getLatitude());
            stmt.setFloat(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }


    /**
     * To find a particular event using the eventID
     * @param eventID
     * @return
     * @throws DataAccessException
     */
    public Event find(String eventID) throws DataAccessException {
        Event event;
        ResultSet rs = null;
        String sql = "SELECT * FROM event WHERE eventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("eventID"),
                        rs.getString("associatedUsername"),
                        rs.getString("personID"),
                        rs.getFloat("latitude"),
                        rs.getFloat("longitude"),
                        rs.getString("country"),
                        rs.getString("city"),
                        rs.getString("eventType"),
                        rs.getInt("year"));
                return event;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    /**
     * this will return a list of events for a particular user
     * uses the associatedUsername attribute of Event class
     * @param associatedUsername
     * @return
     */
    public List<Event> findForUser(String associatedUsername) throws DataAccessException {
        List<Event> listOfEventsToReturn = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT * FROM event WHERE associatedUsername = ?;";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, associatedUsername);
            rs = stmt.executeQuery();
            while(rs.next()){
                Event event = new Event(rs.getString("eventID"), rs.getString("associatedUsername"),
                        rs.getString("personID"), rs.getFloat("latitude"),
                        rs.getFloat("longitude"), rs.getString("country"),
                        rs.getString("city"), rs.getString("eventType"),
                        rs.getInt("year"));
                listOfEventsToReturn.add(event);
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
        return listOfEventsToReturn;
    }
    /**
     * deletes all events
     * @throws DataAccessException
     */
    public void clearUserEvents(String associatedUsername) throws DataAccessException{
        String sql = "DELETE FROM EVENT WHERE associatedUsername = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt. setString(1, associatedUsername);
            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing all events associated with user");
        }
    }

    public void clear() throws DataAccessException{
        String sql = "DELETE FROM event";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the event table");
        }
    }

}
