package elm.elm.service;

import elm.elm.model.User;
import elm.elm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public  User save(User user){ return userRepository.save(user); }
    public void deleteById(int id){ userRepository.deleteById(id); }
    public  User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
    public  User findById(int id){
        return userRepository.findById(id);
    }
    public  List<User> search(String name){
        return userRepository.findByNameContaining(name);
    }
    public List<User> all(){ return userRepository.findAll();}

}
