package vn.duantn.sominamshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.duantn.sominamshop.model.Address;
import vn.duantn.sominamshop.model.User;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAllAddressByUser(User user);

}
