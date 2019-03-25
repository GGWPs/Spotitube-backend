package nl.han.oose;

import nl.han.oose.objects.Account;
import nl.han.oose.objects.Token;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.security.auth.login.LoginException;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {

    public static final String TESTUSER = "test";
    public static final String TESTPASS = "test123";

    @Mock
    private LoginService loginService;

    @InjectMocks
    private LoginController logControl;

    @Test
    public void testSuccessfulLogin() throws LoginException {
        //SETUP
        Token userToken = new Token("", "");
        Mockito.when(loginService.login(Mockito.any())).thenReturn(userToken);

        Account account = new Account("", "");
        account.setUser(TESTUSER);
        account.setPassword(TESTPASS);
        //TEST
        Response loginResponse = logControl.login(account);

        //VERIFY
        assertEquals(200, loginResponse.getStatus());
        assertEquals(userToken, loginResponse.getEntity());
    }

    @Test
    public void testUnauthorizedOnFailedLogin() throws LoginException {
        //SETUP
        Mockito.when(loginService.login(Mockito.any())).thenThrow(LoginException.class);

        Account account = new Account("", "");
        //TEST
        Response loginResponse = logControl.login(account);

        //VERIFY
        assertEquals(401, loginResponse.getStatus());
    }
}