package uz.zako.OnlineZakoServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.zako.OnlineZakoServer.entity.Lesson;
import uz.zako.OnlineZakoServer.entity.Part;
import uz.zako.OnlineZakoServer.model.Result;
import uz.zako.OnlineZakoServer.payloads.request.LessonReq;
import uz.zako.OnlineZakoServer.repository.LessonRepository;

import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private PartService partService;
    @Autowired
    private AttachmentService attachmentService;
    @Override
    public Result save(LessonReq lessonReq) {
        try {
            Lesson lesson=new Lesson();
            lesson.setPart(partService.findById(lessonReq.getPartId()));
            lesson.setTitleUz(lessonReq.getTitleUz());
            lesson.setTitleRu(lessonReq.getTitleRu());
            lesson.setDescriptionUz(lessonReq.getDescriptionUz());
            lesson.setDescriptionRu(lessonReq.getDescriptionRu());
            lesson.setAttachment(attachmentService.findByHashCode(lessonReq.getHashCode()));
            lessonRepository.save(lesson);
            return new Result(true, "save successful");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false,"save failed");
    }

    @Override
    public Result edit(LessonReq lessonReq, Long id) {
        try {
            Lesson lesson=lessonRepository.findById(id).get();
            lesson.setPart(partService.findById(lessonReq.getPartId()));
            lesson.setTitleUz(lessonReq.getTitleUz());
            lesson.setTitleRu(lessonReq.getTitleRu());
            lesson.setDescriptionUz(lessonReq.getDescriptionUz());
            lesson.setDescriptionRu(lessonReq.getDescriptionRu());
            lesson.setAttachment(attachmentService.findByHashCode(lessonReq.getHashCode()));
            lessonRepository.save(lesson);
            return new Result(true, "editing successful");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false,"editing failed");
    }

    @Override
    public Result delete(Long id) {
        try {
            lessonRepository.deleteById(id);
            return new Result(true, "deleting successful");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false, "deleting failed");
    }

    @Override
    public Page<Lesson> findLessonPage(Long partId, int page, int size) {
        try {
            Pageable pageable= PageRequest.of(page, size, Sort.by("createAt").ascending());
            Part part = partService.findById(partId);
            return lessonRepository.findAllByPart(part, pageable);
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public List<Lesson> getAllLessonByPartId(Long partId) {
        return lessonRepository.findAllByPartId(partId);
    }

    @Override
    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    @Override
    public LessonReq getLessonById(Long id) {
        Lesson lesson = lessonRepository.findById(id).get();
        LessonReq lessonReq = new LessonReq();
        lessonReq.setDescriptionRu(lesson.getDescriptionRu());
        lessonReq.setDescriptionUz(lesson.getDescriptionUz());
        lessonReq.setHashCode(lesson.getAttachment().getHashCode());
        lessonReq.setId(lesson.getId());
        lessonReq.setPartId(lesson.getPart().getId());
        lessonReq.setTitleRu(lesson.getTitleRu());
        lessonReq.setTitleUz(lesson.getTitleUz());
        return lessonReq;
    }
}
