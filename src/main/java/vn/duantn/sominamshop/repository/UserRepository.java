package vn.duantn.sominamshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.duantn.sominamshop.model.Role;
import vn.duantn.sominamshop.model.User;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);


    @Query("SELECT u from User u " +
            "left join Address ad on u.id = ad.user.id " +
            "left join Role rl on u.role.id = rl.id " +
            "where u.fullName like concat('%', :fullName, '%') and rl.id = 2")
    Page<User> findByFullNameContainingAndRole(@Param("fullName") String fullName, Pageable pageable);

    boolean existsByPhoneNumber(String phone);

    List<User> findByPhoneNumberStartingWith(String prefix);
}
