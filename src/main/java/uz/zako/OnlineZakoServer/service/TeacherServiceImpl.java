package uz.zako.OnlineZakoServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.zako.OnlineZakoServer.entity.Address;
import uz.zako.OnlineZakoServer.entity.Role;
import uz.zako.OnlineZakoServer.entity.Teacher;
import uz.zako.OnlineZakoServer.model.Result;
import uz.zako.OnlineZakoServer.payloads.request.UserReq;
import uz.zako.OnlineZakoServer.repository.RoleRepository;
import uz.zako.OnlineZakoServer.repository.TeacherRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService{
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Page<Teacher> findTeacherPage(int page, int size) {
        try {
            Pageable pageable= PageRequest.of(page, size, Sort.by("createAt").descending());
            return teacherRepository.findAll(pageable);
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    @Override
    public List<Teacher> getTeacherList() {
        return teacherRepository.findAll();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Result save(UserReq userReq) {
        try {
            Teacher teacher=new Teacher();
            teacher.setAttachment(attachmentService.findByHashCode(userReq.getHashCode()));
            teacher.setPhoneNumber(userReq.getPhoneNumber());
            teacher.setUsername(userReq.getUsername());
            List<Role> list = new ArrayList<>();
            list.add(roleRepository.findByName("ROLE_TEACHER"));
            teacher.setRoles(list);
            teacher.setPassword(passwordEncoder.encode(userReq.getPassword()));
            teacher.setLastName(userReq.getLastName());
            teacher.setFirstName(userReq.getFirstName());
            teacher.setTelegram(userReq.getTelegram());
            teacher.setInstagram(userReq.getInstagram());
            teacher.setFacebook(userReq.getFacebook());
            teacher.setAboutUz(userReq.getAboutUz());
            teacher.setAboutRu(userReq.getAboutRu());
            teacherRepository.save(teacher);
            return new Result(true,"save successful");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false, "save failed");
    }

    @Override
    public Result edit(UserReq userReq, Long id) {
        try {
            Teacher teacher=teacherRepository.findById(id).get();
            teacher.setAttachment(attachmentService.findByHashCode(userReq.getHashCode()));
            teacher.setPhoneNumber(userReq.getPhoneNumber());
            teacher.setUsername(userReq.getUsername());
            if (userReq.getPassword() != null)
                teacher.setPassword(passwordEncoder.encode(userReq.getPassword()));
            teacher.setLastName(userReq.getLastName());
            teacher.setFirstName(userReq.getFirstName());
            teacher.setTelegram(userReq.getTelegram());
            teacher.setInstagram(userReq.getInstagram());
            teacher.setFacebook(userReq.getFacebook());
            teacher.setAboutUz(userReq.getAboutUz());
            teacher.setAboutRu(userReq.getAboutRu());
            teacher.setAccountNonLocked(userReq.getAccountNonLocked());
            teacherRepository.save(teacher);
            return new Result(true,"editing successful");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false, "editing failed");
    }

    @Override
    public Result delete(Long id) {
        try {
            teacherRepository.deleteById(id);
            return new Result(true, "deleting successful");
        }catch (Exception e){
            System.out.println(e);
        }
        return new Result(false,"deleting failed");
    }

    @Override
    public Teacher findById(Long id) {
        try {
            return teacherRepository.findById(id).get();
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}
