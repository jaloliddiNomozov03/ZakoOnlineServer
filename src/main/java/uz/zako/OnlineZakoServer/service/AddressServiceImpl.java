package uz.zako.OnlineZakoServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.zako.OnlineZakoServer.entity.Address;
import uz.zako.OnlineZakoServer.model.Result;
import uz.zako.OnlineZakoServer.payloads.request.AddressReq;
import uz.zako.OnlineZakoServer.repository.AddressRepository;
import uz.zako.OnlineZakoServer.repository.UserRepository;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Override
    public Result save(AddressReq addressRequest) {
        try {
            Address address = new Address();
            address.setDistrict(addressRequest.getDistrict());
            address.setHome(addressRequest.getHome());
            address.setRegion(addressRequest.getRegion());
            address.setStreet(addressRequest.getStreet());
            address.setUser(userRepository.findById(addressRequest.getUserId()).get());
            addressRepository.save(address);
            return new Result(true, "save successful");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false,"save failed");
    }

    @Override
    public Result edit(AddressReq addressRequest, Long id) {
        try {
            Address updateAddress=addressRepository.findById(id).get();
            updateAddress.setDistrict(addressRequest.getDistrict());
            updateAddress.setStreet(addressRequest.getStreet());
            updateAddress.setRegion(addressRequest.getRegion());
            updateAddress.setHome(addressRequest.getHome());
            updateAddress.setUser(userRepository.findById(addressRequest.getUserId()).get());
            addressRepository.save(updateAddress);
            return new Result(true, "editing successful");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false, "editing failed");
    }

    @Override
    public Result delete(Long id) {
        try {
            addressRepository.deleteById(id);
            return new Result(true, "deleting successful");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false, "deleting failed");
    }

    @Override
    public Address findByUserId(Long userId) {
        try {
            return addressRepository.findByUser(userService.findById(userId));
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Address findById(Long id) {
        try {
            return addressRepository.findById(id).get();
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Address getAddressById(Long userId) {
        return addressRepository.getAddressByUserId(userId);
    }
}
