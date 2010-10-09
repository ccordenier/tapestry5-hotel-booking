package com.tap5.hotelbooking.services;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

import com.tap5.hotelbooking.domain.CrudServiceDAO;
import com.tap5.hotelbooking.domain.QueryParameters;
import com.tap5.hotelbooking.domain.entities.User;
import com.tap5.hotelbooking.security.AuthenticationException;

/**
 * Basic Security Realm implementation
 * 
 * @author karesti
 * @version 1.0
 */
public class BasicAuthenticator implements Authenticator
{

    public static final String AUTH_TOKEN = "authToken";

    @Inject
    private CrudServiceDAO crudService;

    @Inject
    private Request request;

    public void login(String username, String password) throws AuthenticationException
    {

        User user = crudService.findUniqueWithNamedQuery(
                User.BY_CREDENTIALS,
                QueryParameters.with("username", username).and("password", password).parameters());

        if (user == null) { throw new AuthenticationException("The user doesn't exist"); }

        request.getSession(true).setAttribute(AUTH_TOKEN, user);
    }

    public boolean isLoggedIn()
    {
        return request.getSession(true).getAttribute(AUTH_TOKEN) != null;
    }

    public void logout()
    {
        request.getSession(true).setAttribute(AUTH_TOKEN, null);
    }

    public User getLoggedUser()
    {
        User user = null;

        if (isLoggedIn())
        {
            user = (User) request.getSession(true).getAttribute(AUTH_TOKEN);
        }
        else
        {
            throw new IllegalStateException("The user is not logged ! ");
        }
        return user;
    }

}
