package uz.zako.OnlineZakoServer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.zako.OnlineZakoServer.entity.Attachment;
import uz.zako.OnlineZakoServer.entity.Lesson;
import uz.zako.OnlineZakoServer.entity.Part;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Page<Lesson> findAllByPart(Part part, Pageable pageable);
    List<Lesson> findAllByPartId(Long partId);
    List<Lesson> findAllByAttachment(Attachment attachment);
}
