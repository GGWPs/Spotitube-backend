package nl.han.oose;

import nl.han.oose.endpoints.LoginController;
import nl.han.oose.objects.Account;
import nl.han.oose.objects.Token;
import org.junit.Test;
//import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.security.auth.login.LoginException;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
//@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {

    public static final String TESTUSER = "test";
    public static final String TESTPASS = "test123";

    @InjectMocks
    private LoginController loginController;

    @Mock
    private LoginService loginService;


    @Test
    public void testLoginWithCorrectCredentials() throws LoginException {
        //SETUP
        Token userToken = new Token("", "");
        when(loginService.login(any())).thenReturn(userToken);

        Account account = new Account("", "");
        account.setUser("test2");
        account.setPassword(TESTPASS);
        //TEST
        Response loginResponse = loginController.login(account);

        //VERIFY
        assertEquals(Response.Status.OK.getStatusCode(), loginResponse.getStatus());
        assertEquals(userToken, loginResponse.getEntity());
    }

    @Test
    public void testLoginWithIncorrectCredentials() throws LoginException {
        //SETUP
        when(loginService.login(any())).thenThrow(LoginException.class);

        Account account = new Account("test", "test123");
        //TEST
        Response loginResponse = loginController.login(account);

        //VERIFY
        assertEquals(200, loginResponse.getStatus());
    }
}