package uz.zako.OnlineZakoServer.service;

import org.springframework.data.domain.Page;
import uz.zako.OnlineZakoServer.entity.Module;
import uz.zako.OnlineZakoServer.model.Result;
import uz.zako.OnlineZakoServer.payloads.request.ModuleReq;

import java.util.List;

public interface ModuleService {
    Result save(ModuleReq moduleReq);
    Result edit(ModuleReq moduleReq, Long id);
    Result delete(Long id);
    Module findById(Long id);
    List<Module> getAllByCourseId(Long courseId);
    Page<Module> findModulePage(int page, int size);
}
