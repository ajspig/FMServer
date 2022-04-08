package request;

/**
 * request objects give what are service classes need
 * this gives our loadService class what it needs
 */

import com.google.gson.JsonObject;
import model.Event;
import model.Person;
import model.User;

public class LoadRequest {
    /**
     * these three objects from the loadRequest body will be added into our database
     * All three are JsonObject arrays and are everything our family tree needs.
     */
    User[] users;
    Person[] persons;
    Event[] events;

    /**
     * Constructor creates a Load Request object
     * This is made up of three Json object arrays of users, persons and events
     * @param users
     * @param persons
     * @param events
     */

    public LoadRequest(User[] users, Person[] persons, Event[] events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}
