package uz.zako.OnlineZakoServer.payloads.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonReq {
    private Long id;
    private String hashCode;
    private String titleUz;
    private String titleRu;
    private String descriptionUz;
    private String descriptionRu;
    private Long partId;
}
