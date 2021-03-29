package uz.zako.OnlineZakoServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.zako.OnlineZakoServer.entity.Follower;
import uz.zako.OnlineZakoServer.model.Result;
import uz.zako.OnlineZakoServer.repository.FollowerRepository;

@Service
public class FollowerServiceImpl implements FollowerService{
    @Autowired
    private FollowerRepository followerRepository;
    @Override
    public Result save(Follower follower) {
        try {
            followerRepository.save(follower);
            return new Result(true,"save successful");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false, "save failed");
    }
    @Override
    public Result edit(Follower follower, Long id) {
        try {
            Follower updateFollower=followerRepository.findById(id).get();
            updateFollower.setPhoneNumber(follower.getPhoneNumber());
            updateFollower.setInterest(follower.getInterest());
            updateFollower.setFullName(follower.getFullName());
            updateFollower.setEmail(follower.getEmail());
            followerRepository.save(updateFollower);
            return new Result(true,"editing successful");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false, "editing failed");
    }

    @Override
    public Result delete(Long id) {
        try {
            followerRepository.deleteById(id);
            return new Result(true, "delelting successful");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false, "deleting failed");
    }
    @Override
    public Page<Follower> findFollowerPage(int page, int size) {
        try {
            Pageable pageable= PageRequest.of(page, size, Sort.by("createAt").descending());
            return followerRepository.findAll(pageable);
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}
