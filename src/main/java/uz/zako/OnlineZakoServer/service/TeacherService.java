package uz.zako.OnlineZakoServer.service;

import org.springframework.data.domain.Page;
import uz.zako.OnlineZakoServer.entity.Teacher;
import uz.zako.OnlineZakoServer.model.Result;
import uz.zako.OnlineZakoServer.payloads.request.UserReq;

import java.util.List;

public interface TeacherService {
    Result save(UserReq userReq);
    Result edit(UserReq userReq, Long id);
    Result delete(Long id);
    Teacher findById(Long id);
    Page<Teacher> findTeacherPage(int page, int size);
    List<Teacher> getTeacherList();
}
