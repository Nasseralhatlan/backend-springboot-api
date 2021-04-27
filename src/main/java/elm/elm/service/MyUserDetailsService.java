package elm.elm.service;
import java.util.ArrayList;

import elm.elm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Service
@RestControllerAdvice
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        elm.elm.model.User USER = userRepository.findByUsername(username);
        if(USER == null){
            SecurityContextHolder.getContext().setAuthentication(null);
            throw new UsernameNotFoundException("User not found with username: " + username);
        }else{
            return new User(USER.getUsername(), USER.getPassword(), new ArrayList<>());
        }
    }
}