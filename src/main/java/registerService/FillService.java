package registerService;

import com.google.gson.Gson;
import dataAccess.*;
import model.*;
import result.FillResult;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Objects;
import java.util.UUID;

/**
 * fill servers database with generated data
 */

public class FillService {
    private LocationData locData;
    private NameData mNames;
    private NameData sNames;
    private NameData fNames;
    private String username;
    private int generations;
    private int numAddedPersons;
    private int numAddedEvents;
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public void setGenerations(int generations) { this.generations = generations; }

    public FillService(String username) {
        this.username = username;
        this.generations = 0;
        this.numAddedPersons = 0;
        this.numAddedEvents = 0;
        this.user = null;
        try {
            Gson gson = new Gson();

            Reader readerForFNames = new FileReader("json/fnames.json");
            fNames = gson.fromJson(readerForFNames, NameData.class);

            Reader readerForLoc = new FileReader("json/locations.json");
            locData = gson.fromJson(readerForLoc, LocationData.class);

            Reader readerForMNames = new FileReader("json/mnames.json");
            mNames = gson.fromJson(readerForMNames, NameData.class);

            Reader readerForSNames = new FileReader("json/snames.json");
            sNames = gson.fromJson(readerForSNames, NameData.class);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * populates the server's database with generated data
     * for the specified username
     * username must be a user already registered with the server
     * if there is any data in the database associated with the given username, it is deleted
     * so just clear all the persons/events associated
     * the generations' parameter in the url can be used to specify number of generates of ancestors to be generated
     */
    public FillResult fill(int generations) {
        this.generations = generations + 1;
        try {
            Database db = new Database();
            db.openConnection();
            UserDAO userdao = new UserDAO(db.getConnection());
            User user = userdao.find(username);
            this.user = user;
            if ((user == null) || (generations < 0)) {
                db.closeConnection(false);
                return new FillResult(false, "Invalid username or generations parameter");
            }

            EventDAO eventDAO = new EventDAO(db.getConnection());
            eventDAO.clearUserEvents(username);

            PersonDAO personDAO = new PersonDAO(db.getConnection());
            personDAO.clearUserPersons(username);

            generatePerson(user.getGender(), generations + 1, 2000, db);
            FillResult result = new FillResult(true, "Successfully added " + numAddedPersons + " persons and " +
                    numAddedEvents + " events to the database.");
            db.closeConnection(true);
            return result;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new FillResult(false, e.getMessage());
        }
    }

    public Location generateLocation() {
        Location locations[] = locData.getData();
        return locations[(int) (Math.random() * locations.length)];
    }

    public String generateNames(NameData anyNames) {
        String names[] = anyNames.getData();
        return names[(int) (Math.random() * names.length)];
    }

    public Person generatePerson(String gender, int generations, int year, Database db) throws DataAccessException {
        EventDAO eventDAO = new EventDAO(db.getConnection());
        PersonDAO personDAO = new PersonDAO(db.getConnection());

        //I feel like maybe I should pass the database object into our generate person
        Person mother = null;
        Person father = null;
        if (generations > 1) {
            mother = generatePerson("f", generations - 1, year - 25, db);
            father = generatePerson("m", generations - 1, year - 25, db);
            mother.setSpouseID(father.getPersonID());
            father.setSpouseID(mother.getPersonID());
            //need to update person in personDAO
            personDAO.update(mother);
            personDAO.update(father);

            Location marriageLocation = generateLocation();
            Event MotherMarriageEvent = new Event(UUID.randomUUID().toString(), username, mother.getPersonID(),
                    marriageLocation.getLatitude(), marriageLocation.getLongitude(),
                    marriageLocation.getCountry(), marriageLocation.getCity(),
                    "marriage", year - 1);
            Event FatherMarriageEvent = new Event(UUID.randomUUID().toString(), username, father.getPersonID(),
                    marriageLocation.getLatitude(), marriageLocation.getLongitude(),
                    marriageLocation.getCountry(), marriageLocation.getCity(),
                    "marriage", year - 1);
            eventDAO.insert(MotherMarriageEvent);
            numAddedEvents++;
            eventDAO.insert(FatherMarriageEvent);
            numAddedEvents++;
        }
        String firstName;
        String lastName;
        String personID;
        Person person;

        if (generations == this.generations) {
            //this is for the user person
            firstName = user.getFirstName();
            lastName = user.getLastName();
            personID = user.getPersonID();
        } else if (Objects.equals(gender, "f")) {
            firstName = generateNames(fNames);
            lastName = generateNames(sNames);
            personID = UUID.randomUUID().toString();
        } else {
            firstName = generateNames(mNames);
            lastName = generateNames(sNames);
            personID = UUID.randomUUID().toString();

        }
        if (mother != null) {
            person = new Person(personID, username, firstName, lastName,
                    gender, father.getPersonID(), mother.getPersonID(), null);
        } else {
            person = new Person(personID, username, firstName, lastName,
                    gender, null, null, null);
        }
        //generate events for person
        Location eventLocation = generateLocation();
        Event birth = new Event(UUID.randomUUID().toString(), username, personID, eventLocation.getLatitude(),
                eventLocation.getLongitude(), eventLocation.getCountry(), eventLocation.getCity(), "birth", year);
        Event death = new Event(UUID.randomUUID().toString(), username, personID, eventLocation.getLatitude(),
                eventLocation.getLongitude(), eventLocation.getCountry(),
                eventLocation.getCity(), "death", year + 80);
        eventDAO.insert(birth);
        numAddedEvents++;
        eventDAO.insert(death);
        numAddedEvents++;

        personDAO.insert(person);
        numAddedPersons++;

//        db.closeConnection(true);
        return person;
    }



}
