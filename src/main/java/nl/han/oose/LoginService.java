package nl.han.oose;

import nl.han.oose.DAO.AccountDAO;
import nl.han.oose.DAO.TokenDAO;
import nl.han.oose.objects.Account;
import nl.han.oose.objects.Token;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.security.auth.login.LoginException;

@Default
public class LoginService {

    @Inject
    private TokenDAO tokenDAO;
    @Inject
    private AccountDAO accountDAO;

    public Token login(Account user) throws LoginException {
        if (accountDAO.accountValidation(user.getUser(), user.getPassword())) {
            if(tokenDAO.checkToken(user.getUser())){
                return tokenDAO.retrieveToken(user.getUser());
            } else {
                return tokenDAO.createNewToken(user.getUser());
            }
        }
        throw new LoginException("Uw logingegevens zijn onjuist!");
    }

}
