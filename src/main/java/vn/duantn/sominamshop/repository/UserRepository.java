package vn.duantn.sominamshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.duantn.sominamshop.model.Role;
import vn.duantn.sominamshop.model.User;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    Page<User> findByFullNameContainingAndRole(String fullName, Role role, Pageable pageable);
}
