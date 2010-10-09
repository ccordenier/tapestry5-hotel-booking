package com.tap5.hotelbooking.pages;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.tap5.hotelbooking.annotations.RequiresLogin;
import com.tap5.hotelbooking.domain.CrudServiceDAO;
import com.tap5.hotelbooking.domain.entities.User;
import com.tap5.hotelbooking.services.Authenticator;

/**
 * Allows the user to modify password
 * 
 * @author karesti
 */
@RequiresLogin
public class Settings
{

    @Property
    private String password;

    @Property
    private String verifyPassword;

    @Inject
    private CrudServiceDAO crudServiceDAO;

    @Component
    private Form settingsForm;

    @Inject
    private Messages messages;

    @Inject
    private Authenticator authenticator;

    public Object onSuccess()
    {

        if (!verifyPassword.equals(password))
        {
            settingsForm.recordError(messages.get("error.verifypassword"));

            return null;
        }

        User user = authenticator.getLoggedUser();
        authenticator.logout();

        user.setPassword(password);

        crudServiceDAO.update(user);

        return Index.class;
    }
}
