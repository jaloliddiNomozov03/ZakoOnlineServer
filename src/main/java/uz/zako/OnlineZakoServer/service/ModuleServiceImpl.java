package uz.zako.OnlineZakoServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.zako.OnlineZakoServer.entity.Module;
import uz.zako.OnlineZakoServer.model.Result;
import uz.zako.OnlineZakoServer.payloads.request.ModuleReq;
import uz.zako.OnlineZakoServer.repository.ModuleRepository;

import java.util.List;

@Service
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private CourseService courseService;
    @Override
    public Result save(ModuleReq moduleReq) {
        try {
            Module module=new Module();
            module.setCourse(courseService.findById(moduleReq.getCourseId()));
            module.setPrice(moduleReq.getPrice());
            module.setNameUz(moduleReq.getNameUz());
            module.setNameRu(moduleReq.getNameRu());
            moduleRepository.save(module);
            return new Result(true, "save successful");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false, "save failed");
    }

    @Override
    public Result edit(ModuleReq moduleReq, Long id) {
        try {
            Module module=moduleRepository.findById(id).get();
            module.setCourse(courseService.findById(moduleReq.getCourseId()));
            module.setPrice(moduleReq.getPrice());
            module.setNameUz(moduleReq.getNameUz());
            module.setNameRu(moduleReq.getNameRu());
            moduleRepository.save(module);
            return new Result(true, "editing successful");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false, "editing failed");
    }

    @Override
    public Result delete(Long id) {
        try {
            moduleRepository.deleteById(id);
            return new Result(true, "deleting successful");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false, "deleting failed");
    }

    @Override
    public Module findById(Long id) {
        try {
            return moduleRepository.findById(id).get();
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public List<Module> getAllByCourseId(Long courseId) {
        try {
            return moduleRepository.getAllByCourseId(courseId);
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Page<Module> findModulePage(int page, int size) {
        try {
            Pageable pageable= PageRequest.of(page, size, Sort.by("createAt"));
            return moduleRepository.findAll(pageable);
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}
