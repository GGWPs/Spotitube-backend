package nl.han.oose.services;

import nl.han.oose.DAO.AccountDAO;
import nl.han.oose.DAO.TokenDAO;
import nl.han.oose.dto.Account;
import nl.han.oose.dto.Token;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.security.auth.login.LoginException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.stream.IntStream;

@Default
public class LoginService {

    @Inject
    private TokenDAO tokenDAO;
    @Inject
    private AccountDAO accountDAO;

    public Token login(Account user) throws LoginException {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(user.getPassword().getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            IntStream.range(0, bytes.length).forEach(i -> sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1)));
            user.setPassword(sb.toString());
            long startTime = System.nanoTime();
            if (accountDAO.accountValidation(user.getUser(), user.getPassword())) {
                long endTime = System.nanoTime();
                System.out.println((endTime - startTime) / 1000000);
                if (tokenDAO.checkToken(user.getUser())) {
                    return tokenDAO.retrieveToken(user.getUser());
                } else {
                    Random random = new Random();
                    String token = String.format("%04d", random.nextInt(10000));
                    for (int i = 0; i < 2; i++) {
                        token += "-" + String.format("%04d", random.nextInt(10000));
                    }
                    return tokenDAO.createNewToken(token, user.getUser());
                }
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        throw new LoginException("Uw logingegevens zijn onjuist!");
    }
}
