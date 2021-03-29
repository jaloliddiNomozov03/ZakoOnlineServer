package uz.zako.OnlineZakoServer.service;

import org.springframework.data.domain.Page;
import uz.zako.OnlineZakoServer.entity.Part;
import uz.zako.OnlineZakoServer.model.Result;
import uz.zako.OnlineZakoServer.payloads.request.PartReq;

import java.util.List;

public interface PartService {
    Result save(PartReq partReq);
    Result edit(PartReq partReq, Long id);
    Result delete(Long id);
    Part findById(Long id);
    Page<Part> findPartPage(int page, int size, Long moduleId);
    List<Part> findAllByModuleId(Long moduleId);
}
