package uz.zako.OnlineZakoServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.zako.OnlineZakoServer.entity.Follower;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Long> {

}
