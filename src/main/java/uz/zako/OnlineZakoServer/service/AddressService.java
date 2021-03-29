package uz.zako.OnlineZakoServer.service;

import uz.zako.OnlineZakoServer.entity.Address;
import uz.zako.OnlineZakoServer.model.Result;
import uz.zako.OnlineZakoServer.payloads.request.AddressReq;

public interface AddressService {
    Result save(AddressReq address);
    Result edit(AddressReq address, Long id);
    Result delete(Long id);
    Address findByUserId(Long userId);
    Address findById(Long id);
    Address getAddressById(Long userId);
}
