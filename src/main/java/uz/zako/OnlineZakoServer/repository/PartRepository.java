package uz.zako.OnlineZakoServer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.zako.OnlineZakoServer.entity.Part;

import java.util.List;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
    Page<Part> findAllByModuleId(Long moduleId, Pageable pageable);
    List<Part> findAllByModuleId(Long moduleId);
}
