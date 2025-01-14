package vn.duantn.sominamshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.duantn.sominamshop.model.Address;
import vn.duantn.sominamshop.model.User;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("SELECT a FROM Address a WHERE a.user = :user ORDER BY a.status DESC")
    List<Address> findAllAddressByUser(@Param("user") User user);

    // List<Address> findAllAddressByUserOrderByStatusDesc(User user);
    @Query("SELECT a FROM Address a WHERE a.user.id = :id")
    List<Address> findAllAddressByIdUser(@Param("id") Long id);
}
