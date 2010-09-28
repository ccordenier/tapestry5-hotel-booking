package com.tap5.hotelbooking.services;

import java.util.List;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Startup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tap5.hotelbooking.domain.CrudServiceDAO;
import com.tap5.hotelbooking.domain.entities.User;

/**
 * This class will import demo initial data
 * 
 * @author karesti
 */
public class DataModule
{

    private static final Logger LOGGER = LoggerFactory.getLogger(DataModule.class);

    @Startup
    @CommitAfter
    public void initialize(CrudServiceDAO dao)
    {
        LOGGER.info("-- Loading initial demo data");
        User user = new User();
        user.setEmail("katiaaresti@gmail.com");
        user.setFullname("Katia Aresti");
        user.setPassword("123456");
        user.setUsername("karesti");
        dao.create(user);

        user = new User();
        user.setEmail("ccordenier@gmail.com");
        user.setFullname("Chirstophe Cordenier");
        user.setPassword("123456");
        user.setUsername("kordenier");
        dao.create(user);

        List<User> userList = dao.findWithNamedQuery(User.ALL);

        LOGGER.info("Users " + userList);
        LOGGER.info("-- Data Loaded. Exit");
    }
}