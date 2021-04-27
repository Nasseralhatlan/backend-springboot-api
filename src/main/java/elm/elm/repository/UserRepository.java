package elm.elm.repository;

import elm.elm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(String username);
    User findById(int id);
    List<User> findAll();
    List<User> findByNameContaining(String name);
    void deleteById(int id);
}
