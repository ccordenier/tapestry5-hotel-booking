package com.tap5.hotelbooking.security;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.tap5.hotelbooking.domain.CrudServiceDAO;
import com.tap5.hotelbooking.domain.QueryParameters;
import com.tap5.hotelbooking.domain.entities.User;

/**
 * Custom Shiro Security Realm implementation
 * 
 * @author karesti
 */
public class BasicSecurityRealm extends AuthorizingRealm implements SecurityRealm
{

    @Inject
    private CrudServiceDAO crudService;

    @Override
    @Log
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
    {

        return new SimpleAuthorizationInfo();
    }

    @Override
    @Log
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException
    {

        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        List<User> users = crudService.findWithNamedQuery(
                User.BY_CREDENTIALS,
                QueryParameters.with("username", userToken.getUsername())
                        .and("password", String.copyValueOf(userToken.getPassword())).parameters());

        AuthenticationInfo authInfo = null;

        if (users.isEmpty())
        {
            throw new AuthenticationException("The user doesn't exist");
        }
        else
        {
            User user = users.iterator().next();
            authInfo = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), "basic");
        }

        return authInfo;
    }

}
