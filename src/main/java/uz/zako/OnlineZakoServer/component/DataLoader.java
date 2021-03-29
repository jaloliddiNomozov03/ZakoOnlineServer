package uz.zako.OnlineZakoServer.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.zako.OnlineZakoServer.entity.Role;
import uz.zako.OnlineZakoServer.entity.User;
import uz.zako.OnlineZakoServer.repository.RoleRepository;
import uz.zako.OnlineZakoServer.repository.UserRepository;
import uz.zako.OnlineZakoServer.service.FileServiceImpl;


@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileServiceImpl fileService;
    @Override
    public void run(String... args) throws Exception {
        Role roleSuperAdmin = new Role(1l, "ROLE_SUPER_ADMIN");
        Role roleAdmin = new Role(2l, "ROLE_ADMIN");
        Role roleStudent = new Role(3l, "ROLE_STUDENT");
        Role roleTeacher = new Role(3l, "ROLE_TEACHER");
        try {
            roleRepository.save(roleSuperAdmin);
            roleRepository.save(roleAdmin);
            roleRepository.save(roleStudent);
            roleRepository.save(roleTeacher);
        } catch (Exception e) {
            System.out.println(e);
        }

        User user = new User();
        user.setPassword(passwordEncoder.encode("12345"));
        user.setRoles(roleRepository.findAll());
        user.setUsername("webdeveloper0106@gmail.com");
        user.setFirstName("Shuxratjon (SuperAdmin)");
        user.setLastName("Yo'ldashev");
        user.setId(1l);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
