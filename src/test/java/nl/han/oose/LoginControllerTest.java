package nl.han.oose;

import nl.han.oose.controllers.LoginController;
import nl.han.oose.dto.Account;
import nl.han.oose.dto.Token;
import nl.han.oose.services.LoginService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.security.auth.login.LoginException;
import javax.ws.rs.core.Response;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

    public static final String TESTUSER = "test";
    public static final String TESTPASS = "test123";

    @Mock
    private LoginService loginService;

    @InjectMocks
    private LoginController loginController;

    @Test
    public void testLoginWithCorrectCredentials() throws LoginException {
        //SETUP
        Token token = new Token("", "");
        when(loginService.login(any())).thenReturn(token);
        Account account = new Account("", "");
        account.setUser(TESTUSER);
        account.setPassword(TESTPASS);

        //TEST
        Response loginResponse = loginController.login(account);

        //VERIFY
        assertEquals(Response.Status.OK.getStatusCode(), loginResponse.getStatus());
        assertEquals(token, loginResponse.getEntity());
    }

    @Test
    public void testLoginWithIncorrectCredentials() throws LoginException {
        //SETUP
        when(loginService.login(any())).thenThrow(LoginException.class);
        Account account = new Account("", "");
        //TEST
        Response loginResponse = loginController.login(account);

        //VERIFY
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), loginResponse.getStatus());
    }
}
