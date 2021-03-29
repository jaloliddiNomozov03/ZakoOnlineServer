package uz.zako.OnlineZakoServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.zako.OnlineZakoServer.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
