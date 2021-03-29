package uz.zako.OnlineZakoServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.zako.OnlineZakoServer.entity.Attachment;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    Attachment findByHashCode(String hashCode);
    Attachment findByName(String name);
    @Query(value = "SELECT U.user_id FROM payment U where U.id IN \n" +
            "(SELECT M.payment_id FROM payment_module M WHERE M.module_id IN \n" +
            "(SELECT P.module_id from part P WHERE P.id IN \n" +
            "(SELECT L.part_id FROM lesson L WHERE \n" +
            "attachment_id = (SELECT A.id FROM attachment A WHERE hash_code = :hashCode))))", nativeQuery = true)
    List<Long> userIds(@Param("hashCode") String hashCode);
}