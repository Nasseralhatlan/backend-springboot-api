package elm.elm.controller;

import elm.elm.response.StringResponse;
import elm.elm.response.UsersResponse;
import elm.elm.model.User;
import elm.elm.service.MyUserDetailsService;
import elm.elm.service.UserService;
import elm.elm.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@RestController
public class userController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @CrossOrigin
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@RequestHeader(value="Authorization") String token) {
        String jwt = token.substring(7);
        String username = jwtUtil.extractUsername(jwt);
        User user = userService.findByUsername(username);
        return ResponseEntity.ok(user);
    }
    @CrossOrigin
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<UsersResponse> getUsers() {
        List<User> Users = userService.all();
        UsersResponse response = new UsersResponse(Users);
        return ResponseEntity.ok(response);
    }
    @CrossOrigin
    @RequestMapping(value = "/users/search/{name}", method = RequestMethod.GET)
    public ResponseEntity<UsersResponse> searchUser(@PathVariable String name) {
        List<User> Users = userService.search(name);
        UsersResponse response = new UsersResponse(Users);
        return ResponseEntity.ok(response);
    }
    @CrossOrigin
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable int id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @CrossOrigin
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public ResponseEntity<StringResponse> updateUser(HttpServletRequest request,@RequestHeader(value="Authorization") String token,@RequestBody User user) {
        StringResponse response = new StringResponse();
        if(user.getPassword() == null){
            response.add("code","UP2");
            response.add("message","Password can't be empty");
            return ResponseEntity.ok(response);
        }
        String jwt = token.substring(7);
        String username = jwtUtil.extractUsername(jwt);
        User userFromDB = userService.findByUsername(username);
        user.setUsername(userFromDB.getUsername());
        user.setId(userFromDB.getId());
        userService.save(user);
        UserDetails newuser = myUserDetailsService.loadUserByUsername(userFromDB.getUsername());
        UsernamePasswordAuthenticationToken newtoken = new UsernamePasswordAuthenticationToken(user,null,newuser.getAuthorities());
        newtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(newtoken);
        response.add("code","UP1");
        response.add("message","Update successfully");
        return ResponseEntity.ok(response);
    }
    @CrossOrigin
    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    public ResponseEntity<StringResponse> deleteUser(@RequestHeader(value="Authorization") String token) {
        String jwt = token.substring(7);
        String username = jwtUtil.extractUsername(jwt);
        User userFromDB = userService.findByUsername(username);
        userService.deleteById(userFromDB.getId());
        StringResponse response = new StringResponse();
        response.add("code","UD1");
        response.add("message","Deleted successfully");
        return ResponseEntity.ok(response);
    }

}
