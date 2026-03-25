package userProfile.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import userProfile.demo.model.Profile;
import userProfile.demo.model.Users;


@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Profile findByUsersId(Long id);
    Profile findByUsers(Users users);
    
}