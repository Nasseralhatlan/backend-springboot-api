package elm.elm.response;

import java.util.Collections;
import java.util.List;

import elm.elm.model.User;

public class UsersResponse {
    private List<User> users;

    public UsersResponse(User user) {
        List<User> users = Collections.emptyList();
        users.add(user);
    }
    public UsersResponse(List<User> users) {
        this.users = users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public void add(User user){
        this.users.add(user);
    }
}
