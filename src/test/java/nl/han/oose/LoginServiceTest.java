package nl.han.oose;

import nl.han.oose.DAO.AccountDAO;
import nl.han.oose.DAO.TokenDAO;
import nl.han.oose.objects.Account;
import nl.han.oose.objects.Token;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.security.auth.login.LoginException;

import static org.mockito.Mockito.*;
import static junit.framework.TestCase.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private AccountDAO accountDAO;

    @Mock
    private TokenDAO tokenDAO;

    @InjectMocks
    private LoginService sut;

    @Test
    public void testTokenIsCreatedWhenLoginIsCorrect() throws LoginException {
        //SETUP
        Account account = new Account("test", "test123");
        Token userToken = new Token("1234-1234-1234", "test");

        //TEST
        when(accountDAO.getAccount(any())).thenReturn(account);
        when(tokenDAO.createNewToken(any())).thenReturn(userToken);

        //VERIFY
        assertEquals("1234-1234-1234", userToken.getToken());
        assertEquals("test", userToken.getUser());
    }

    @Test
    public void testExceptionIsThrownWhenLoginIsIncorrect() throws LoginException {
        //SETUP
        thrown.expect(LoginException.class);
        thrown.expectMessage("Uw logingegevens zijn onjuist!");

        Account badAccount = new Account("test", "123test");
        Account goodAccount = new Account("test", "test123");

        //TEST
        when(accountDAO.getAccount(any())).thenReturn(goodAccount);

        //VERIFY
        sut.login(badAccount);
    }
}
