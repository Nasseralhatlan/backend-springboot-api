package elm.elm.controller;

import elm.elm.response.StringResponse;
import elm.elm.model.User;
import elm.elm.service.MyUserDetailsService;
import elm.elm.service.UserService;
import elm.elm.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
@RestController
public class authConroller {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;



    @CrossOrigin
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<StringResponse> register(@RequestBody User user){

        StringResponse response = new StringResponse();
        User USER_EXIST = userService.findByUsername(user.getUsername());
        if(USER_EXIST != null){
            response.add("code","AR2");
            response.add("message","User already exist");
        }else{
            response.add("code","AR1");
            response.add("username",user.getUsername());
            Random rand = new Random();
            int avatar = rand.nextInt(8);
            user.setAvatar(avatar);
            userService.save(user);
            response.add("message","User registerd successfully");
        }
        return ResponseEntity.ok(response);
    }



    @CrossOrigin
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<StringResponse> login(@RequestBody User userFromReq) throws Exception{
        String username = userFromReq.getUsername();
        String password = userFromReq.getPassword();

        StringResponse response = new StringResponse();
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username,password)
            );
        }catch (BadCredentialsException e){
            response.add("message","Wrong username or password");
            response.add("code","AL2");
            return ResponseEntity.ok(response);
        }

        User USER = userService.findByUsername(username);
        if(USER != null) {
            final UserDetails user = myUserDetailsService.loadUserByUsername(username);
            final String jwt = jwtUtil.generateToken(user);
            response.add("token",jwt);
            response.add("message","Logged in successfully");
            response.add("code","AL1");
            return ResponseEntity.ok(response);
        }else{
            response.add("message","Wrong username or password !");
            response.add("code","AL2");
            return ResponseEntity.ok(response);
        }
    }
}
