package wns.configs;

/*
 *@author Skelorc
 */

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import wns.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User user =  (User)authentication.getPrincipal();
        String answer = "{\"isAuthorized\" : \"true\", \"role\" : \""+user.getRoles().getValue()+"\", \"msg\" : \"Create!\" , \"redirect\" : \"/\"}";
        response.getOutputStream()
                .println(answer);
    }
}
