package uz.zako.OnlineZakoServer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Attachment attachment;
    private String titleUz;
    private String titleRu;
    private String descriptionUz;
    private String descriptionRu;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Part part;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createAt;
}
