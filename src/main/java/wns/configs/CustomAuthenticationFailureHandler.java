package wns.configs;

/*
 *@author Skelorc
 */

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Calendar;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        String answer = "{\"isAuthorized\" : false, \"role\" : false, \"msg\" : \""+e.getMessage()+"\", \"redirect\" : false}";
        response.getOutputStream()
                .println(String.format(answer,
                        e.getMessage(),
                        LocalDateTime.now()));
    }
}
