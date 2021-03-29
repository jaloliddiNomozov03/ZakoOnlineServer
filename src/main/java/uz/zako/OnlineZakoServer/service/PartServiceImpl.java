package uz.zako.OnlineZakoServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.zako.OnlineZakoServer.entity.Part;
import uz.zako.OnlineZakoServer.model.Result;
import uz.zako.OnlineZakoServer.payloads.request.PartReq;
import uz.zako.OnlineZakoServer.repository.PartRepository;

import java.util.List;

@Service
public class PartServiceImpl implements PartService {
    @Autowired
    private PartRepository partRepository;

    @Override
    public Part findById(Long id) {
        try {
            return partRepository.findById(id).get();
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public List<Part> findAllByModuleId(Long moduleId) {
        return partRepository.findAllByModuleId(moduleId);
    }

    @Autowired
    private ModuleService moduleService;
    @Override
    public Result save(PartReq partReq) {
        try {
            Part part=new Part();
            part.setModule(moduleService.findById(partReq.getModuleId()));
            part.setNameUz(partReq.getNameUz());
            part.setNameRu(partReq.getNameRu());
            partRepository.save(part);
            return new Result(true, "save successful");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false, "save failed");
    }

    @Override
    public Result edit(PartReq partReq, Long id) {
        try {
            Part part=partRepository.findById(id).get();
            part.setModule(moduleService.findById(partReq.getModuleId()));
            part.setNameUz(partReq.getNameUz());
            part.setNameRu(partReq.getNameRu());
            partRepository.save(part);
            return new Result(true, "save successful");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false, "save failed");
    }

    @Override
    public Result delete(Long id) {
        try {
            partRepository.deleteById(id);
            return new Result(true, "deleting successful");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false, "deleting failed");
    }

    @Override
    public Page<Part> findPartPage(int page, int size, Long moduleId) {
        try {
            Pageable pageable= PageRequest.of(page,size, Sort.by("createAt").descending());
            return partRepository.findAllByModuleId(moduleId, pageable);
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}
