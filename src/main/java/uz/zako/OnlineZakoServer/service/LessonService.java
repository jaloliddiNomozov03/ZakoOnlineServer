package uz.zako.OnlineZakoServer.service;

import org.springframework.data.domain.Page;
import uz.zako.OnlineZakoServer.entity.Lesson;
import uz.zako.OnlineZakoServer.model.Result;
import uz.zako.OnlineZakoServer.payloads.request.LessonReq;

import java.util.List;

public interface LessonService {
    Result save(LessonReq lessonReq);
    Result edit(LessonReq lessonReq, Long id);
    Result delete(Long id);
    Page<Lesson> findLessonPage(Long partId, int page, int size);
    List<Lesson> getAllLessonByPartId(Long partId);
    List<Lesson> getAllLessons();
    LessonReq getLessonById(Long id);
}
