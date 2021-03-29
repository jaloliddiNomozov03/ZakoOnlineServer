package uz.zako.OnlineZakoServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.zako.OnlineZakoServer.entity.Course;
import uz.zako.OnlineZakoServer.model.Result;
import uz.zako.OnlineZakoServer.payloads.request.CourseReq;
import uz.zako.OnlineZakoServer.repository.CourseRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService{
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AttachmentService attachmentService;
    @Override
    public Result save(CourseReq courseReq) {
        try {
            Course course = new Course();
            course.setTitleUz(courseReq.getTitleUz());
            course.setTitleRu(courseReq.getTitleRu());
            course.setTeacher(teacherService.findById(courseReq.getTeacherId()));
            course.setStatus(courseReq.getStatus());
            course.setLectures(courseReq.getLectures());
            course.setDuration(courseReq.getDuration());
            course.setDescriptionUz(courseReq.getDescriptionUz());
            course.setDescriptionRu(courseReq.getDescriptionRu());
            course.setCategory(categoryService.findById(courseReq.getCategoryId()));
            course.setAttachement(attachmentService.findByHashCode(courseReq.getHashCode()));
            courseRepository.save(course);
            return new Result(true, "save successful");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false,"save failed");
    }
    @Override
    public Result edit(CourseReq courseReq, Long id) {
        try {
            Course course = courseRepository.findById(id).get();
            course.setTitleUz(courseReq.getTitleUz());
            course.setTitleRu(courseReq.getTitleRu());
            course.setTeacher(teacherService.findById(courseReq.getTeacherId()));
            course.setStatus(courseReq.getStatus());
            course.setLectures(courseReq.getLectures());
            course.setDuration(courseReq.getDuration());
            course.setDescriptionUz(courseReq.getDescriptionUz());
            course.setDescriptionRu(courseReq.getDescriptionRu());
            course.setCategory(categoryService.findById(courseReq.getCategoryId()));
            course.setAttachement(attachmentService.findByHashCode(courseReq.getHashCode()));
            courseRepository.save(course);
            return new Result(true, "editing successful");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false,"editing failed");
    }

    @Override
    public Result delete(Long id) {
        try {
            courseRepository.deleteById(id);
            return new Result(true,"deleting successful");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false, "deleting failed");
    }

    @Override
    public Course findById(Long id) {
        try {
            return courseRepository.findById(id).get();
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public List<Course> findAllByCategoryId(Long id) {
        try{
            return courseRepository.findAllByCategory(categoryService.findById(id));
        }catch (Exception e){
            System.out.println(e);
        }
        return new ArrayList<>();
    }

    @Override
    public List<Course> findAll() {
        try {
            return courseRepository.findAll();
        }catch (Exception e){
            System.out.println(e);
        }
        return new ArrayList<>();
    }

    @Override
    public Page<Course> findCoursePage(int page, int size) {
        try {
            Pageable pageable= PageRequest.of(page, size, Sort.by("createAt").descending());
            return courseRepository.findAll(pageable);
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}
