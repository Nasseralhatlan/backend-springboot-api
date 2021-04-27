package elm.elm.model;

import javax.persistence.*;

@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="USERNAME", length=100)
    private String username;

    @Column(name="NAME", length=100)
    private String  name;

    @Column(name="AVATAR", length=1)
    private int avatar;

    @Column(name="PASSWORD", length=100)
    private String password;


    public User(String username,String name,String password,int avatar) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.avatar = avatar;
    }
    public User(int id,String username,String name,String password,int avatar) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.password = password;
        this.avatar = avatar;
    }

    public User() {}

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) {
        this.password = password;
    }

}
