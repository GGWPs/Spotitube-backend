package nl.han.oose;

import nl.han.oose.DAO.AccountDAO;
import nl.han.oose.DAO.TokenDAO;
import nl.han.oose.dto.Account;
import nl.han.oose.dto.Token;
import nl.han.oose.services.LoginService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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
    public void testRandomTokenIsCreatedWhenLoginIsCorrect() throws LoginException {
        //SETUP
        Account account = new Account("test", "test123");
        Token userToken = new Token("1234-1234-1234", "test");
        when(accountDAO.accountValidation(any(),any())).thenReturn(true);
        when(tokenDAO.createNewToken(any(), any())).thenReturn(userToken);

        //TEST
        Token token = sut.login(account);

        //VERIFY
        assertEquals("1234-1234-1234", token.getToken());
        assertEquals("test", token.getUser());
    }


    @Test
    public void testExceptionIsThrownWhenLoginIsIncorrect() throws LoginException{
        //SETUP
        thrown.expect(LoginException.class);
        thrown.expectMessage("Uw logingegevens zijn onjuist!");

        Account badAccount = new Account("test", "123test");

        //TEST
        when(accountDAO.accountValidation(any(),any())).thenReturn(false);

        //VERIFY
        sut.login(badAccount);

    }
}
