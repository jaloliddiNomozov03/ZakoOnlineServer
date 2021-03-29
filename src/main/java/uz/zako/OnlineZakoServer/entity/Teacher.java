package uz.zako.OnlineZakoServer.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Teacher extends User {
    private String aboutUz;
    private String aboutRu;
    private String telegram;
    private String instagram;
    private String facebook;
}
