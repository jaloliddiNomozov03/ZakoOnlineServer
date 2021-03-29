package uz.zako.OnlineZakoServer.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.zako.OnlineZakoServer.entity.*;
import uz.zako.OnlineZakoServer.entity.Module;
import uz.zako.OnlineZakoServer.model.Result;
import uz.zako.OnlineZakoServer.payloads.request.*;
import uz.zako.OnlineZakoServer.payloads.response.PaymentRes;
import uz.zako.OnlineZakoServer.repository.AttachmentRepository;
import uz.zako.OnlineZakoServer.service.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/client")
@CrossOrigin(origins = "*")
public class ClientController {
    private CourseService courseService;
    private TeacherService teacherService;
    private AttachmentService attachmentService;
    private ModuleService moduleService;
    private PartService partService;
    private LessonService lessonService;
    private FollowerService followerService;
    private UserService userService;
    private AddressService addressService;
    private FileService fileService;
    private PaymentService paymentService;
    private PaymentDetailService paymentDetailService;
    @Autowired
    public ClientController(CourseService courseService,
                            TeacherService teacherService,
                            AttachmentService attachmentService,
                            ModuleService moduleService,
                            PartService partService,
                            LessonService lessonService,
                            FollowerService followerService,
                            UserService userService,
                            AddressService addressService,
                            FileService fileService,
                            PaymentService paymentService,
                            PaymentDetailService paymentDetailService){
        this.courseService=courseService;
        this.teacherService=teacherService;
        this.attachmentService=attachmentService;
        this.moduleService=moduleService;
        this.partService=partService;
        this.lessonService=lessonService;
        this.followerService=followerService;
        this.userService=userService;
        this.addressService=addressService;
        this.fileService=fileService;
        this.paymentService=paymentService;
        this.paymentDetailService=paymentDetailService;
    }
    @Autowired
    private AttachmentRepository attachmentRepository;
    //region FileController
    @PostMapping("/file/save")
    public ResponseEntity<Result> fileSave(@RequestParam(name = "file") MultipartFile multipartFile,
                                                     @RequestParam Boolean auth){
        return ResponseEntity.ok(attachmentService.save(multipartFile, true));
    }
    @DeleteMapping("/file/delete/{hashCode}")
    public ResponseEntity<Result> fileDelete(@PathVariable String hashCode){
        return ResponseEntity.ok(attachmentService.delete(hashCode));
    }
    @GetMapping("/file/preview/{hashCode}")
    public ResponseEntity<InputStreamResource> clientFilePreview(@PathVariable String hashCode, HttpServletResponse response) throws IOException {
        assert hashCode != null;
        return attachmentService.getFile(hashCode, response);
    }
    @GetMapping("/excel/users")
    public ResponseEntity<InputStreamResource> superAdminExcelFilePreview(HttpServletResponse response) throws IOException {
        Attachment attachment=fileService.getUsersExcelList();
        return attachmentService.downloadFile(attachment.getHashCode(), response);
    }
    @GetMapping("/attachment/preview/{hashCode}")
    public ResponseEntity<InputStreamResource> getAttachmentWithSecurity(Authentication authentication,
            @PathVariable String hashCode,
            HttpServletResponse response){
        try {
            Attachment attachment=attachmentService.findByHashCode(hashCode);
            if (attachment.getAuth()){
                return attachmentService.getFile(hashCode, response);
            }else {
                User user= (User) authentication.getPrincipal();
                List<Long> userIds=attachmentRepository.userIds(hashCode);
                if (userIds.contains(user.getId())){
                    return attachmentService.getFile(hashCode, response);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    //endregion

    @GetMapping("/course/getAllCourse")
    public List<Course> getAllCourse(){
        return courseService.findAll();
    }
    @GetMapping("/course/get/{courseId}")
    public ResponseEntity<Course> clientCourseGetById(@PathVariable Long courseId){
        return ResponseEntity.ok(courseService.findById(courseId));
    }
    @GetMapping("/course/module/get/{courseId}")
    public ResponseEntity<List<Module>> clientgetModuleByCourseId(@PathVariable Long courseId){
        return ResponseEntity.ok(moduleService.getAllByCourseId(courseId));
    }
    @GetMapping("/course/list/get")
    public ResponseEntity<List<Course>> clientGetCourseList(){
        return ResponseEntity.ok(courseService.findAll());
    }
    @GetMapping("/teachers/getList")
    public ResponseEntity<List<Teacher>> getAllTeachersList(){
        return ResponseEntity.ok(teacherService.getTeacherList());
    }

    //Module Controller code go here
    @GetMapping("/module/parts/get/{moduleId}")
    public ResponseEntity<List<Part>> getPartsByModuleId(@PathVariable Long moduleId){
        return ResponseEntity.ok(partService.findAllByModuleId(moduleId));
    }
    // Lesson Code go here
    @GetMapping("/parts/lesson/getList")
    public ResponseEntity<List<Lesson>> getAllListLessons(){
        return ResponseEntity.ok(lessonService.getAllLessons());
    }
    @GetMapping("/lesson/getById/{lessonId}")
    public ResponseEntity<LessonReq> getLessonById(@PathVariable Long lessonId){
        return ResponseEntity.ok(lessonService.getLessonById(lessonId));
    }
    @GetMapping("/parts/lesson/getByPartId/{partId}")
    public ResponseEntity<List<Lesson>> getAllLessonByPartId(@PathVariable Long partId){
        return ResponseEntity.ok(lessonService.getAllLessonByPartId(partId));
    }
    // Follower code go here
    @PostMapping("/follower/save")
    public ResponseEntity<Result> followerSave(@RequestBody Follower follower){
        return ResponseEntity.ok(followerService.save(follower));
    }
    // User controller code go here
    @PostMapping("/register")
    public ResponseEntity<Result> saveUserRegister(@RequestBody UserReq userReq){
        if (!userService.existsByUsername(userReq.getUsername())) {
            return ResponseEntity.ok(userService.save(userReq));
        }else {
            return ResponseEntity.ok(new Result(false,"Email allaqachon mavjud"));
        }
    }
    @PutMapping("/user/edit/{id}")
    public ResponseEntity<Result> userEdit(@RequestBody UserReq user,
                                           Authentication authentication,
                                           @PathVariable Long id){
        UserReq userReq=userService.getMe((User) authentication.getPrincipal());
        if (userReq.getId().equals(id)) {
            return ResponseEntity.ok(userService.edit(id, user));
        }else {
            return ResponseEntity.ok(new Result(false,"Update failed"));
        }
    }
    @PutMapping("/user/editPassword/{userId}")
    public ResponseEntity<Result> editUserPassword(@PathVariable Long userId,
                                                   Authentication authentication,
                                                   @RequestBody UserPasswordReq userPasswordReq){
        UserReq userReq=userService.getMe((User) authentication.getPrincipal());
        if (userReq.getId().equals(userId)) {
            return ResponseEntity.ok(userService.editPasswordClient(userPasswordReq, userId));
        }else {
            return ResponseEntity.ok(new Result(false,"Update failed"));
        }
    }
    @GetMapping("/user/getPaymentCourse/{userId}")
    public ResponseEntity<List<Payment>> getPaymentCourseByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getAllPaymentCourseByUserId(userId));
    }

    //region AddressController
    @PostMapping("/address/save")
    public ResponseEntity<Result> addressSave(@RequestBody AddressReq address){
        return ResponseEntity.ok(addressService.save(address));
    }
    @PutMapping("/address/edit/{id}")
    public ResponseEntity<Result> addressEdit(@PathVariable Long id, @RequestBody AddressReq address){
        return ResponseEntity.ok(addressService.edit(address, id));
    }
    @GetMapping("/address/{id}")
    public ResponseEntity<Address> addressGetOne(@PathVariable Long id){
        return ResponseEntity.ok(addressService.findById(id));
    }
    @GetMapping("/address/get/{userId}")
    public ResponseEntity<AddressReq> getAddressByUserId(@PathVariable Long userId){
        try {
            Address address = addressService.getAddressById(userId);
            AddressReq addressReq = new AddressReq();
            addressReq.setId(address.getId());
            addressReq.setDistrict(address.getDistrict());
            addressReq.setHome(address.getHome());
            addressReq.setRegion(address.getRegion());
            addressReq.setStreet(address.getStreet());
            addressReq.setUserId(userId);
            return ResponseEntity.ok(addressReq);
        }catch (Exception e){
            return null;
        }
    }
    //endregion
    //region PaymentController
    @PostMapping("/payment/save")
    public ResponseEntity<Result> paymentSave(@RequestBody PaymentReq paymentReq){
        return ResponseEntity.ok(paymentService.save(paymentReq));
    }
    @PutMapping("/payment/edit/{id}")
    public ResponseEntity<Result> paymentEdit(@RequestBody PaymentReq paymentReq, @PathVariable Long id){
        return ResponseEntity.ok(paymentService.edit(paymentReq, id));
    }
    @DeleteMapping("/payment/delete/{id}")
    public ResponseEntity<Result> paymentDelete(@PathVariable Long id){
        return ResponseEntity.ok(paymentService.delete(id));
    }
    @GetMapping("/payment/getPage/{userId}")
    public ResponseEntity<Page<Payment>> paymentGetPage(@PathVariable Long userId, @RequestParam int page, @RequestParam int size){
        return ResponseEntity.ok(paymentService.findPaymentPage(userId, page, size));
    }
    @GetMapping("/payment/getList/{userId}")
    public ResponseEntity<List<PaymentRes>> paymentGetAll(@PathVariable Long userId){
        return ResponseEntity.ok(paymentService.findAll(userId));
    }
    @GetMapping("/payment/{id}")
    public ResponseEntity<Payment> paymentGetById(@PathVariable Long id){
        return ResponseEntity.ok(paymentService.findById(id));
    }
    //endregion

    //region PaymentDetailController
    @PostMapping("/paymentDetail/save")
    public ResponseEntity<Result> paymentDetailSave(@RequestBody PaymentDetailReq paymentDetail){
        return ResponseEntity.ok(paymentDetailService.save(paymentDetail));
    }
    @PutMapping("/paymentDetail/edit/{id}")
    public ResponseEntity<Result> paymentDetailEdit(@RequestBody PaymentDetailReq paymentDetail, @PathVariable long id){
        return ResponseEntity.ok(paymentDetailService.edit(paymentDetail, id));
    }
    @DeleteMapping("/paymentDetail/delete/{id}")
    public ResponseEntity<Result> paymentDetailDelete(@PathVariable long id){
        return ResponseEntity.ok(paymentDetailService.delete(id));
    }
    @GetMapping("/paymentDetail/{id}")
    public ResponseEntity<PaymentDetail> paymentDetailGetOne(@PathVariable long id){
        return ResponseEntity.ok(paymentDetailService.findById(id));
    }
    //endregion

}
