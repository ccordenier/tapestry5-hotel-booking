package com.tap5.hotelbooking.domain;

import org.apache.tapestry5.beanvalidator.BeanValidatorConfigurer;
import org.apache.tapestry5.hibernate.HibernateTransactionAdvisor;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Match;

import com.tap5.hotelbooking.domain.entities.Hotel;
import com.tap5.hotelbooking.domain.entities.User;

/**
 * This class should contain contribution to data stuff (hibernate configuration, beanvalidators...)
 */
public class HibernateModule
{
    public static void bind(ServiceBinder binder)
    {
        binder.bind(CrudServiceDAO.class, HibernateCrudServiceDAO.class);
    }

    public static void contributeHibernateEntityPackageManager(Configuration<String> configuration)
    {
        configuration.add("com.tap5.hotelbooking.domain.entities");
    }

    public static void contributeBeanValidatorSource(
            OrderedConfiguration<BeanValidatorConfigurer> configuration)
    {
        configuration.add("HotelBookingConfigurer", new BeanValidatorConfigurer()
        {
            public void configure(javax.validation.Configuration<?> configuration)
            {
                configuration.ignoreXmlConfiguration();
            }
        });
    }

    @Match("*DAO")
    public static void adviseTransactions(HibernateTransactionAdvisor advisor,
            MethodAdviceReceiver receiver)
    {
        advisor.addTransactionCommitAdvice(receiver);
    }

    public static void contributeSeedEntity(OrderedConfiguration<Object> configuration)
    {
        configuration.add("user1", new User("Christophe Cordenier", "cordenier",
                "ccordenier@example.com", "cordenier"));
        configuration.add("user2", new User("Katia Aresti", "karesti", "karesti@example.com",
                "karesti"));

        configuration.add("hotel1", new Hotel(129, 3, "Marriott Courtyard",
                "Tower Place, Buckhead", "Atlanta", "GA", "30305", "USA"));
        configuration.add("hotel2", new Hotel(84, 4, "Doubletree Atlanta-Buckhead",
                "3342 Peachtree Road NE", "Atlanta", "GA", "30326", "USA"));
        configuration.add("hotel3", new Hotel(289, 4, "W New York - Union Square",
                "201 Park Avenue South", "New York", "NY", "10003", "USA"));
        configuration.add("hotel4", new Hotel(219, 3, "W New York", "541 Lexington Avenue",
                "New York", "NY", "10022", "USA"));
        configuration.add("hotel5", new Hotel(250, 3, "Hotel Rouge", "1315 16th Street NW",
                "Washington", "DC", "20036", "USA"));
        configuration.add("hotel6", new Hotel(159, 4, "70 Park Avenue Hotel",
                "70 Park Avenue, 38th St", "New York", "NY", "10016", "USA"));
        configuration.add("hotel7", new Hotel(198, 4, "Parc 55", "55 Cyril Magnin Street",
                "San Francisco", "CA", "94102", "USA"));
        configuration.add("hotel8", new Hotel(189, 4, "Conrad Miami", "1395 Brickell Ave", "Miami",
                "FL", "33131", "USA"));
        configuration.add("hotel9", new Hotel(111, 4, "Grand Hyatt", "345 Stockton Street",
                "San Francisco", "CA", "94108", "USA"));
        configuration.add("hotel10", new Hotel(54, 1, "Super 8 Eau Claire Campus Area",
                "1151 W MacArthur Ave", "Eau Claire", "WI", "54701", "USA"));
        configuration.add("hotel11", new Hotel(199, 4, "San Francisco Marriott",
                "55 Fourth Street", "San Francisco", "CA", "94103", "USA"));
        configuration.add("hotel11", new Hotel(543, 4, "Hilton Diagonal Mar",
                "Passeig del Taulat 262-264", "Barcelona", "Catalunya", "08019", "ES"));
        configuration.add("hotel12", new Hotel(335, 5, "Hilton Tel Aviv", "Independence Park",
                "Tel Aviv", null, "63405", "IL"));
        configuration.add("hotel13", new Hotel(242, 5, "InterContinental Hotel Tokyo Bay",
                "1-15-2 Kaigan", "Tokyo", "Minato", "105", "JP"));
        configuration.add("hotel14", new Hotel(130, 4, "Hotel Beaulac",
                " Esplanade Léopold-Robert 2", "Neuchatel", null, "2000", "CH"));
        configuration.add("hotel15", new Hotel(266, 5, "Conrad Treasury Place",
                "130 William Street", "Brisbane", "QL", "4001", "AU"));
        configuration.add("hotel16", new Hotel(170, 4, "Ritz-Carlton Montreal",
                "1228 Sherbrooke St West", "Montreal", "Quebec", "H3G1H6", "CA"));
        configuration.add("hotel17", new Hotel(179, 4, "Ritz-Carlton Atlanta",
                "181 Peachtree St NE", "Atlanta", "GA", "30303", "USA"));
        configuration.add("hotel18", new Hotel(145, 4, "Swissotel Sydney", "68 Market Street",
                "Sydney", "NSW", "2000", "AU"));
        configuration.add("hotel19", new Hotel(178, 4, "Meliá White House",
                "Albany Street Regents Park", "London", null, "NW13UP", "GB"));
        configuration.add("hotel20", new Hotel(159, 3, "Hotel Allegro", "171 W Randolph Street",
                "Chicago", "IL", "60601", "USA"));
        configuration.add("hotel21", new Hotel(296, 5, "Caesars Palace", "3570 Las Vegas Blvd S",
                "Las Vegas", "NV", "89109", "USA"));
        configuration.add("hotel22", new Hotel(300, 4, "Mandalay Bay Resort & Casino",
                "3950 Las Vegas Blvd S", "Las Vegas", "NV", "89119", "USA"));
        configuration.add("hotel23", new Hotel(100, 2, "Hotel Cammerpoorte",
                "Nationalestraat 38-40", "Antwerp", null, "2000", "BE"));
    }
}
