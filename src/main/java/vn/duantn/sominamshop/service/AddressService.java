package vn.duantn.sominamshop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import vn.duantn.sominamshop.model.Address;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.model.dto.AddressDTO;
import vn.duantn.sominamshop.repository.AddressRepository;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final UserService userService;

    public AddressService(AddressRepository addressRepository, UserService userService) {
        this.addressRepository = addressRepository;
        this.userService = userService;
    }

    public Address handleAddAddress(AddressDTO dto, User user) {
        Address addressNew = new Address();
        addressNew.setFullName(dto.getFullName());
        addressNew.setPhoneNumber(dto.getPhoneNumber());
        addressNew.setAddress(dto.getAddress());
        addressNew.setUser(user);
        addressNew.setStreetDetails(dto.getStreetDetails());
        if (dto.isStatus() == true) {

            List<Address> arrAddressByUser = this.findAllAddressByUser(user);
            for (Address address : arrAddressByUser) {
                if (address.isStatus() == true) {
                    address.setStatus(false);
                    this.addressRepository.save(address);
                }
            }
            addressNew.setStatus(true);
        } else {
            addressNew.setStatus(false);
        }
        return this.addressRepository.save(addressNew);
    }

    public List<Address> findAllAddressByUser(User user) {
        return this.addressRepository.findAllAddressByUser(user);
    }

    public Address findAddressById(Long id) {
        return this.addressRepository.findById(id).get();
    }

    public AddressDTO convertAddressToAddressDTO(String idAddress) {
        Address addressById = this.addressRepository.findById(Long.valueOf(idAddress)).get();
        AddressDTO dto = new AddressDTO();
        dto.setIdAddress(addressById.getId());
        dto.setFullName(addressById.getFullName());
        dto.setPhoneNumber(addressById.getPhoneNumber());
        dto.setAddress(addressById.getAddress());
        dto.setStreetDetails(addressById.getStreetDetails());
        dto.setStatus(dto.isStatus());
        return dto;
    }

    public Address updateAddress(AddressDTO dto, User user) {
        Address addressById = this.addressRepository.findById(Long.valueOf(dto.getIdAddress())).get();
        addressById.setAddress(dto.getAddress());
        addressById.setFullName(dto.getFullName());
        addressById.setPhoneNumber(dto.getPhoneNumber());
        addressById.setStreetDetails(dto.getStreetDetails());
        if (dto.isStatus() != addressById.isStatus()) {
            List<Address> arrAddressByUser = this.findAllAddressByUser(user);
            for (Address address : arrAddressByUser) {
                if (address.isStatus() == true) {
                    address.setStatus(false);
                    this.addressRepository.save(address);
                }
            }
            addressById.setStatus(true);
        } else {
            addressById.setStatus(false);
        }
        return this.addressRepository.save(addressById);
    }
}
