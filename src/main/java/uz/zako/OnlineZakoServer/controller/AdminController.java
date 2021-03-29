package uz.zako.OnlineZakoServer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.zako.OnlineZakoServer.entity.*;
import uz.zako.OnlineZakoServer.entity.Module;
import uz.zako.OnlineZakoServer.model.ProfilSettings;
import uz.zako.OnlineZakoServer.model.Result;
import uz.zako.OnlineZakoServer.payloads.request.*;
import uz.zako.OnlineZakoServer.payloads.response.PaymentRes;
import uz.zako.OnlineZakoServer.service.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/superAdmin")
public class AdminController {
    //region ServicesList

    private AttachmentService attachmentService;
    private AddressService addressService;
    private CategoryService categoryService;
    private CourseService courseService;
    private FollowerService followerService;
    private LessonService lessonService;
    private MessageService messageService;
    private ModuleService moduleService;
    private  PartService partService;
    private PaymentService paymentService;
    private PaymentDetailService paymentDetailService;
    private TeacherService teacherService;
    private UserService userService;
    private FileService fileService;
    @Autowired
    public AdminController(AttachmentService attachmentService,
                           AddressService addressService,
                           CategoryService categoryService,
                           CourseService courseService,
                           FollowerService followerService,
                           LessonService lessonService,
                           MessageService messageService,
                           ModuleService moduleService,
                           PartService partService,
                           PaymentService paymentService,
                           PaymentDetailService paymentDetailService,
                           TeacherService teacherService,
                           UserService userService,
                           FileService fileService){
        this.attachmentService=attachmentService;
        this.addressService=addressService;
        this.categoryService=categoryService;
        this.courseService=courseService;
        this.followerService=followerService;
        this.lessonService=lessonService;
        this.messageService=messageService;
        this.moduleService=moduleService;
        this.partService=partService;
        this.paymentService=paymentService;
        this.paymentDetailService=paymentDetailService;
        this.teacherService=teacherService;
        this.userService=userService;
        this.fileService=fileService;
    }
    //endregion

    //region FileController
    @PostMapping("/file/save")
    public ResponseEntity<Result> superAdminFileSave(@RequestParam(name = "file") MultipartFile multipartFile,
                                                         @RequestParam Boolean auth){
        return ResponseEntity.ok(attachmentService.save(multipartFile, auth));
    }
    @DeleteMapping("/file/delete/{hashCode}")
    public ResponseEntity<Result> superAdminFileDelete(@PathVariable String hashCode){
        return ResponseEntity.ok(attachmentService.delete(hashCode));
    }
    @GetMapping("/file/preview/{hashCode}")
    public ResponseEntity<InputStreamResource> superAdminFilePreview(@PathVariable String hashCode, HttpServletResponse response) throws IOException {
        assert hashCode != null;
        return attachmentService.getFile(hashCode, response);
    }
    @GetMapping("/file/getPage")
    public ResponseEntity<Page<Attachment>> superAdminFileGetPage(@RequestParam int page, @RequestParam int size){
        return ResponseEntity.ok(attachmentService.findFilePage(page, size));
    }
    //endregion

    //region AddressController
    @PostMapping("/address/save")
    public ResponseEntity<Result> superAdminAddressSave(@RequestBody AddressReq address){
        return ResponseEntity.ok(addressService.save(address));
    }
    @PutMapping("/address/edit/{id}")
    public ResponseEntity<Result> superAdminAddressEdit(@PathVariable Long id, @RequestBody AddressReq address){
        return ResponseEntity.ok(addressService.edit(address, id));
    }
    @DeleteMapping("/address/delete/{id}")
    public ResponseEntity<Result> superAdminAddressDelete(@PathVariable Long id){
        return ResponseEntity.ok(addressService.delete(id));
    }
    @GetMapping("/address/{userId}")
    public ResponseEntity<Address> superAdminAddressGetOne(@PathVariable Long userId){
        return ResponseEntity.ok(addressService.findByUserId(userId));
    }
    //endregion

    //region CategoryController
    @PostMapping("/category/save")
    public ResponseEntity<Result> superAdminCategorySave(@RequestBody Category category){
        return ResponseEntity.ok(categoryService.save(category));
    }
    @PutMapping("/category/edit/{id}")
    public ResponseEntity<Result> superAdminCategoryEdit(@RequestBody Category category, @PathVariable Long id){
        return ResponseEntity.ok(categoryService.edit(category, id));
    }
    @DeleteMapping("/category/delete/{id}")
    public ResponseEntity<Result> superAdminCategoryDelete(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.delete(id));
    }
    @GetMapping("/category/{id}")
    public ResponseEntity<Category> superAdminCategoryGetOne(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.findById(id));
    }
    @GetMapping("/category/getPage")
    public ResponseEntity<Page<Category>> superAdminCategoryGetPage(@RequestParam int page, @RequestParam int size){
        return ResponseEntity.ok(categoryService.findCategoryPage(page, size));
    }
    @GetMapping("/category/getAll")
    public ResponseEntity<List<Category>> superAdminCategoryGetPage(){
        return ResponseEntity.ok(categoryService.findAll());
    }
    //endregion

    //region CourseController
    @PostMapping("/course/save")
    public ResponseEntity<Result> superAdminCourseSave(@RequestBody CourseReq course){
        return ResponseEntity.ok(courseService.save(course));
    }
    @PutMapping("/course/edit/{id}")
    public ResponseEntity<Result> superAdminCourseEdit(@RequestBody CourseReq course, @PathVariable Long id){
        return ResponseEntity.ok(courseService.edit(course, id));
    }
    @DeleteMapping("/course/delete/{id}")
    public ResponseEntity<Result> superAdminCourseDelete(@PathVariable Long id){
        return ResponseEntity.ok(courseService.delete(id));
    }
    @GetMapping("/course/getAll")
    public ResponseEntity<List<Course>> superAdminCourseGetAll(){
        return ResponseEntity.ok(courseService.findAll());
    }
    @GetMapping("/course/getAll/{categoryId}")
    public ResponseEntity<List<Course>> superAdminCourseGetAllWithId(@PathVariable Long categoryId){
        return ResponseEntity.ok(courseService.findAllByCategoryId(categoryId));
    }
    @GetMapping("/course/getPage")
    public ResponseEntity<Page<Course>> superAdminCourseGetPage(@RequestParam int page, @RequestParam int size){
        return ResponseEntity.ok(courseService.findCoursePage(page, size));
    }
    //endregion

    //region FollowerController
    @PostMapping("/follower/save")
    public ResponseEntity<Result> superAdminFollowerSave(@RequestBody Follower follower){
        return ResponseEntity.ok(followerService.save(follower));
    }
    @PutMapping("/follower/edit/{id}")
    public ResponseEntity<Result> superAdminFollowerEdit(@RequestBody Follower follower, @PathVariable Long id){
        return ResponseEntity.ok(followerService.edit(follower, id));
    }
    @DeleteMapping("/follower/delete/{id}")
    public ResponseEntity<Result> superAdminFollowerDelete(@PathVariable Long id){
        return ResponseEntity.ok(followerService.delete(id));
    }
    @GetMapping("/follower/getPage")
    public ResponseEntity<Page<Follower>> superAdminFolloweGetPage(@RequestParam int page, @RequestParam int size){
        return ResponseEntity.ok(followerService.findFollowerPage(page, size));
    }
    //endregion

    //region LessonController
    @PostMapping("/lesson/save")
    public ResponseEntity<Result> superAdminLessonSave(@RequestBody LessonReq lessonReq){
        return ResponseEntity.ok(lessonService.save(lessonReq));
    }
    @PutMapping("/lesson/edit/{id}")
    public ResponseEntity<Result> superAdminLessonEdit(@RequestBody LessonReq lessonReq, @PathVariable Long id){
        return ResponseEntity.ok(lessonService.edit(lessonReq, id));
    }
    @DeleteMapping("/lesson/delete/{id}")
    public ResponseEntity<Result> superAdminLessonDelete(@PathVariable Long id){
        return ResponseEntity.ok(lessonService.delete(id));
    }
    @GetMapping("/lesson/getPage/{partId}")
    public ResponseEntity<Page<Lesson>> superAdminLessonGetPage(@PathVariable Long partId,
                                                                @RequestParam int page,
                                                                @RequestParam int size){
        return ResponseEntity.ok(lessonService.findLessonPage(partId, page, size));
    }
    //endregion

    //region MessageController
    @PostMapping("/message/save")
    public ResponseEntity<Result> superAdminMessageSave(@RequestBody Message message){
        return ResponseEntity.ok(messageService.save(message));
    }
    @DeleteMapping("/message/delete/{id}")
    public ResponseEntity<Result> superAdminMessageEdit(@PathVariable Long id){
        return ResponseEntity.ok(messageService.delete(id));
    }
    @GetMapping("/message/getPage")
    public ResponseEntity<Page<Message>> superAdminMessageGetPage(@RequestParam int page, @RequestParam int size){
        return ResponseEntity.ok(messageService.findMessagePage(page, size));
    }
    //endregion

    //region ModuleController
    @PostMapping("/module/save")
    public ResponseEntity<Result> superAdminModuleSave(@RequestBody ModuleReq moduleReq){
        return ResponseEntity.ok(moduleService.save(moduleReq));
    }
    @PutMapping("/module/edit/{id}")
    public ResponseEntity<Result> superAdminModuleEdit(@RequestBody ModuleReq moduleReq, @PathVariable Long id){
        return ResponseEntity.ok(moduleService.edit(moduleReq, id));
    }
    @DeleteMapping("/module/delete/{id}")
    public ResponseEntity<Result> superAdminModuleDelete(@PathVariable Long id){
        return ResponseEntity.ok(moduleService.delete(id));
    }
    @GetMapping("/module/getPage")
    public ResponseEntity<Page<Module>> superAdminModuleGetPage(@RequestParam int page, @RequestParam int size){
        return ResponseEntity.ok(moduleService.findModulePage(page, size));
    }
    @GetMapping("/module/getAll/{courseId}")
    public ResponseEntity<List<Module>> superAdminModuleGetList(@PathVariable Long courseId){
        return ResponseEntity.ok(moduleService.getAllByCourseId(courseId));
    }
    //endregion

    //region PartController
    @PostMapping("/part/save")
    public ResponseEntity<Result> superAdminPartSave(@RequestBody PartReq partReq){
        return ResponseEntity.ok(partService.save(partReq));
    }
    @PutMapping("/part/edit/{id}")
    public ResponseEntity<Result> superAdminPartEdit(@RequestBody PartReq partReq, @PathVariable Long id){
        return ResponseEntity.ok(partService.edit(partReq, id));
    }
    @DeleteMapping("/part/delete/{id}")
    public ResponseEntity<Result> superAdminPartDelete(@PathVariable Long id){
        return ResponseEntity.ok(partService.delete(id));
    }
    @GetMapping("/part/{id}")
    public ResponseEntity<Part> superAdminPartGetOne(@PathVariable Long id){
        return ResponseEntity.ok(partService.findById(id));
    }
    @GetMapping("/part/getPage/{moduleId}")
    public ResponseEntity<Page<Part>> superAdminPartGetPage(@PathVariable Long moduleId, @RequestParam int page, @RequestParam int size){
        return ResponseEntity.ok(partService.findPartPage(page, size, moduleId));
    }
    //endregion

    //region PaymentDetailController
    @PostMapping("/paymentDetail/save")
    public ResponseEntity<Result> superAdminPaymentDetailSave(@RequestBody PaymentDetailReq paymentDetail){
        return ResponseEntity.ok(paymentDetailService.save(paymentDetail));
    }
    @PutMapping("/paymentDetail/edit/{id}")
    public ResponseEntity<Result> superAdminPaymentDetailEdit(@RequestBody PaymentDetailReq paymentDetail, @PathVariable long id){
        return ResponseEntity.ok(paymentDetailService.edit(paymentDetail, id));
    }
    @DeleteMapping("/paymentDetail/delete/{id}")
    public ResponseEntity<Result> superAdminPaymentDetailDelete(@PathVariable long id){
        return ResponseEntity.ok(paymentDetailService.delete(id));
    }
    @GetMapping("/paymentDetail/{id}")
    public ResponseEntity<PaymentDetail> superAdminPaymentDetailGetOne(@PathVariable long id){
        return ResponseEntity.ok(paymentDetailService.findById(id));
    }
    //endregion

    //region PaymentController
    @PostMapping("/payment/save")
    public ResponseEntity<Result> superAdminPaymentSave(@RequestBody PaymentReq paymentReq){
        return ResponseEntity.ok(paymentService.save(paymentReq));
    }
    @PutMapping("/payment/edit/{id}")
    public ResponseEntity<Result> superAdminPaymentEdit(@RequestBody PaymentReq paymentReq, @PathVariable Long id){
        return ResponseEntity.ok(paymentService.edit(paymentReq, id));
    }
    @DeleteMapping("/payment/delete/{id}")
    public ResponseEntity<Result> superAdminPaymentDelete(@PathVariable Long id){
        return ResponseEntity.ok(paymentService.delete(id));
    }
    @GetMapping("/payment/getPage/{userId}")
    public ResponseEntity<Page<Payment>> superAdminPaymentGetPage(@PathVariable Long userId, @RequestParam int page, @RequestParam int size){
        return ResponseEntity.ok(paymentService.findPaymentPage(userId, page, size));
    }
    @GetMapping("/payment/getList/{userId}")
    public ResponseEntity<List<PaymentRes>> superAdminPaymentGetAll(@PathVariable Long userId){
        return ResponseEntity.ok(paymentService.findAll(userId));
    }
    @GetMapping("/payment/{id}")
    public ResponseEntity<Payment> superAdminPaymentGetById(@PathVariable Long id){
        return ResponseEntity.ok(paymentService.findById(id));
    }
    //endregion

    //region TeacherController
    @PostMapping("/teacher/save")
    public ResponseEntity<Result> superAdminTeacherSave(@RequestBody UserReq userReq){
        return ResponseEntity.ok(teacherService.save(userReq));
    }
    @PutMapping("/teacher/edit/{id}")
    public ResponseEntity<Result> superAdminTeacherEdit(@RequestBody UserReq userReq, @PathVariable Long id){
        return ResponseEntity.ok(teacherService.edit(userReq, id));
    }
    @DeleteMapping("/teacher/delete/{id}")
    public ResponseEntity<Result> superAdminTeacherDelete(@PathVariable Long id){
        return ResponseEntity.ok(teacherService.delete(id));
    }
    @GetMapping("/teacher/getPage")
    public ResponseEntity<Page<Teacher>> superAdminTeacherGetPage(@RequestParam int page, @RequestParam int size){
        return ResponseEntity.ok(teacherService.findTeacherPage(page, size));
    }
    //endregion

    @PostMapping("/user/save")
    public ResponseEntity<Result> superAdminUserSave(@RequestBody UserReq user){
        return ResponseEntity.ok(userService.save(user));
    }
    @PostMapping("/user/rePassword")
    public ResponseEntity<Result> superAdminUserRePassword(@RequestBody ProfilSettings profilSettings, Authentication authentication){
        return ResponseEntity.ok(userService.editPassword(profilSettings, (User) authentication.getPrincipal()));
    }

    @PutMapping("/user/edit/{id}")
    public ResponseEntity<Result> superAdminUserSave(@RequestBody UserReq user, @PathVariable Long id){
        return ResponseEntity.ok(userService.edit(id, user));
    }
    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<Result> superAdminUserEdit(@PathVariable Long id){
        return ResponseEntity.ok(userService.delete(id));
    }
    @GetMapping("/user/getPage")
    public ResponseEntity<Page<User>> superAdminUserGetPage(@RequestParam int page, @RequestParam int size){
        return ResponseEntity.ok(userService.findAll(page, size));
    }
}
