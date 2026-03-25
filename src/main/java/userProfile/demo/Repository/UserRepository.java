package userProfile.demo.Repository;




import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import userProfile.demo.model.Users;



@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username); 
    boolean existsByUsername(String username); 

    List<Users> findByProfileIsNull();
  

  

 
}
