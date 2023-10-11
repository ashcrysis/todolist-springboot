package br.com.ashermartins.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.ashermartins.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Receive Login (username and password)
        var authorization = request.getHeader("Authorization");
        var auth_Encoded = authorization.substring("Basic".length()).trim();

        byte[] auth_Decoded = Base64.getDecoder().decode(auth_Encoded);
        var authString = new String(auth_Decoded);
        String[] credentials = authString.split(":");
        String username = credentials[0];
        String password = credentials[1];

        // Validate Username
        var user = this.userRepository.findByUsername(username);
        if (user == null) {

            response.sendError(401, "User or/and password incorrect.");

        } else {
            // Validate password
            var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword().toCharArray());
            if (passwordVerify.verified) {

                filterChain.doFilter(request, response);

            }

        }
    }

}
