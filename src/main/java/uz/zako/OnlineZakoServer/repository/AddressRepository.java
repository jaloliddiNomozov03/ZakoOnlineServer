package uz.zako.OnlineZakoServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.zako.OnlineZakoServer.entity.Address;
import uz.zako.OnlineZakoServer.entity.User;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Address getAddressByUserId(Long userId);
    Address findByUser(User user);
}
