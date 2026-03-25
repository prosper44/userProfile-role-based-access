package userProfile.demo.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private String username;
    private String password;
    private String role;

    @OneToOne(mappedBy = "users")
    
    private Profile profile;

    
    
    public Users() {
    }



    public Users(String username, String password, String role, Profile profile) {
       
        this.username = username;
        this.password = password;
        this.role = role;
        this.profile = profile;
    }



    public Long getId() {
        return id;
    }



   



    public String getUsername() {
        return username;
    }



    public void setUsername(String username) {
        this.username = username;
    }



    public String getPassword() {
        return password;
    }



    public void setPassword(String password) {
        this.password = password;
    }



    public String getRole() {
        return role;
    }



    public void setRole(String role) {
        this.role = role;
    }

    public Profile getProfile()
    {
        return profile;
    }

    public void setProfile(Profile profile)
    {
        this.profile = profile;
    }
}