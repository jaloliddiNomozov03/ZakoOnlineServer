package uz.zako.OnlineZakoServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.zako.OnlineZakoServer.entity.Category;
import uz.zako.OnlineZakoServer.entity.Course;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
List<Course> findAllByCategory(Category category);
}
