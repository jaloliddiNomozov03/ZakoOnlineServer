package uz.zako.OnlineZakoServer.entity;

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
//@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }) })
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    @Column(unique = true)
    private String email;
    private String phoneNumber;
    private String interest;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createAt;
}
