package uz.zako.OnlineZakoServer.payloads.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleReq {
    private Long id;
    private String nameUz;
    private String nameRu;
    private Double price;
    private Long courseId;
}
