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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameUz;
    private String nameRu;
    private Double price;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createAt;
}
