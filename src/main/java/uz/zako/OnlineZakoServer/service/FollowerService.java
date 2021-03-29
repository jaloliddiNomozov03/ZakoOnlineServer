package uz.zako.OnlineZakoServer.service;

import org.springframework.data.domain.Page;
import uz.zako.OnlineZakoServer.entity.Follower;
import uz.zako.OnlineZakoServer.model.Result;

public interface FollowerService {
    Result save(Follower follower);
    Result edit(Follower follower, Long id);
    Result delete(Long id);
    Page<Follower> findFollowerPage(int page, int size);
}
