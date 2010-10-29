package com.tap5.hotelbooking.dal;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.ioc.annotations.Startup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tap5.hotelbooking.entities.Hotel;
import com.tap5.hotelbooking.entities.User;

/**
 * Demo Data Loader
 * 
 * @author karesti
 * @version 1.0
 */
public class DataModule
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DataModule.class);

    private final CrudServiceDAO dao;

    public DataModule(CrudServiceDAO dao)
    {
        super();
        this.dao = dao;
    }

    @Startup
    public void initialize()
    {
        List<Hotel> hotels = new ArrayList<Hotel>();
        List<User> users = new ArrayList<User>();

        users.add(new User("Christophe Cordenier", "cordenier", "ccordenier@example.com",
                "cordenier"));
        users.add(new User("Katia Aresti", "karesti", "karesti@example.com", "karesti"));

        hotels.add(new Hotel(129, 3, "Marriott Courtyard", "Tower Place, Buckhead", "Atlanta",
                "GA", "30305", "USA"));

        hotels.add(new Hotel(84, 4, "Doubletree Atlanta-Buckhead", "3342 Peachtree Road NE",
                "Atlanta", "GA", "30326", "USA"));

        hotels.add(new Hotel(289, 4, "W New York - Union Square", "201 Park Avenue South",
                "New York", "NY", "10003", "USA"));

        hotels.add(new Hotel(219, 3, "W New York", "541 Lexington Avenue", "New York", "NY",
                "10022", "USA"));

        hotels.add(new Hotel(250, 3, "Hotel Rouge", "1315 16th Street NW", "Washington", "DC",
                "20036", "USA"));

        hotels.add(new Hotel(159, 4, "70 Park Avenue Hotel", "70 Park Avenue, 38th St", "New York",
                "NY", "10016", "USA"));

        hotels.add(new Hotel(198, 4, "Parc 55", "55 Cyril Magnin Street", "San Francisco", "CA",
                "94102", "USA"));

        hotels.add(new Hotel(189, 4, "Conrad Miami", "1395 Brickell Ave", "Miami", "FL", "33131",
                "USA"));

        hotels.add(new Hotel(111, 4, "Grand Hyatt", "345 Stockton Street", "San Francisco", "CA",
                "94108", "USA"));

        hotels.add(new Hotel(54, 1, "Super 8 Eau Claire Campus Area", "1151 W MacArthur Ave",
                "Eau Claire", "WI", "54701", "USA"));

        hotels.add(new Hotel(199, 4, "San Francisco Marriott", "55 Fourth Street", "San Francisco",
                "CA", "94103", "USA"));

        hotels.add(new Hotel(543, 4, "Hilton Diagonal Mar", "Passeig del Taulat 262-264",
                "Barcelona", "Catalunya", "08019", "ES"));

        hotels.add(new Hotel(335, 5, "Hilton Tel Aviv", "Independence Park", "Tel Aviv", null,
                "63405", "IL"));

        hotels.add(new Hotel(242, 5, "InterContinental Hotel Tokyo Bay", "1-15-2 Kaigan", "Tokyo",
                "Minato", "105", "JP"));

        hotels.add(new Hotel(130, 4, "Hotel Beaulac", " Esplanade Léopold-Robert 2", "Neuchatel",
                null, "2000", "CH"));

        hotels.add(new Hotel(266, 5, "Conrad Treasury Place", "130 William Street", "Brisbane",
                "QL", "4001", "AU"));

        hotels.add(new Hotel(170, 4, "Ritz-Carlton Montreal", "1228 Sherbrooke St West",
                "Montreal", "Quebec", "H3G1H6", "CA"));

        hotels.add(new Hotel(179, 4, "Ritz-Carlton Atlanta", "181 Peachtree St NE", "Atlanta",
                "GA", "30303", "USA"));

        hotels.add(new Hotel(145, 4, "Swissotel Sydney", "68 Market Street", "Sydney", "NSW",
                "2000", "AU"));

        hotels.add(new Hotel(178, 4, "Melié White House", "Albany Street Regents Park", "London",
                null, "NW13UP", "GB"));

        hotels.add(new Hotel(159, 3, "Hotel Allegro", "171 W Randolph Street", "Chicago", "IL",
                "60601", "USA"));

        hotels.add(new Hotel(296, 5, "Caesars Palace", "3570 Las Vegas Blvd S", "Las Vegas", "NV",
                "89109", "USA"));

        hotels.add(new Hotel(300, 4, "Mandalay Bay Resort & Casino", "3950 Las Vegas Blvd S",
                "Las Vegas", "NV", "89119", "USA"));

        hotels.add(new Hotel(100, 2, "Hotel Cammerpoorte", "Nationalestraat 38-40", "Antwerp",
                null, "2000", "BE"));

        LOGGER.info("-- Loading initial demo data");
        create(users);
        create(hotels);

        List<User> userList = dao.findWithNamedQuery(User.ALL);
        LOGGER.info("Users " + userList);
        LOGGER.info("-- Data Loaded. Exit");
    }

    private void create(List<?> entities)
    {
        for (Object e : entities)
        {
            dao.create(e);
        }
    }

}
