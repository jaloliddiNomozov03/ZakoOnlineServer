package uz.zako.OnlineZakoServer.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.zako.OnlineZakoServer.entity.User;


import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
     Optional<User> findByUsername(String username);
     @Query(value = "select * from users u where u.dtype='User' order by u.create_at desc", nativeQuery = true)
     Page<User> findAllOnlyUsers(Pageable pageable);
     Boolean existsUserByUsername(String username);

}
