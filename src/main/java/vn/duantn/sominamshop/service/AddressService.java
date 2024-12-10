package vn.duantn.sominamshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.duantn.sominamshop.model.Address;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.repository.AddressRepository;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> findAllAddressByUser(User user) {
        return this.addressRepository.findAllAddressByUser(user);
    }

    public Address findAddressById(Long id) {
        return this.addressRepository.findById(id).get();
    }

}
