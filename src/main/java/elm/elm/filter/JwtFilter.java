package elm.elm.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import elm.elm.service.MyUserDetailsService;
import elm.elm.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;


    @Autowired
    private JwtUtil jwtUtil;

    private final ObjectMapper mapper;

    public JwtFilter(ObjectMapper mapper) {
        this.mapper = mapper;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        String jwt =null;
        String username =null;
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
           jwt = authorizationHeader.substring(7);
           username = jwtUtil.extractUsername(jwt);
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails user = myUserDetailsService.loadUserByUsername(username);
            if(jwtUtil.validateToken(jwt,user)){
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        }
        chain.doFilter(request,response);
    }

}
