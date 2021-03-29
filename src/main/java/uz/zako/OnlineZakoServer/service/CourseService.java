package uz.zako.OnlineZakoServer.service;

import org.springframework.data.domain.Page;
import uz.zako.OnlineZakoServer.entity.Course;
import uz.zako.OnlineZakoServer.model.Result;
import uz.zako.OnlineZakoServer.payloads.request.CourseReq;

import java.util.List;

public interface CourseService {
    Result save(CourseReq courseReq);
    Result edit(CourseReq courseReq, Long id);
    Result delete(Long id);
    Course findById(Long id);
    List<Course> findAll();
    List<Course> findAllByCategoryId(Long id);
    Page<Course> findCoursePage(int page, int size);
}
