package uz.zako.OnlineZakoServer.service;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.*;
import jxl.write.Label;
import jxl.write.Number;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.zako.OnlineZakoServer.entity.Attachment;
import uz.zako.OnlineZakoServer.entity.Module;
import uz.zako.OnlineZakoServer.entity.PaymentDetail;
import uz.zako.OnlineZakoServer.repository.AttachmentRepository;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private PaymentDetailService paymentDetailService;
    @Value("${upload.path}")
    private String uploadPath;
    private Path checkPackage(Path file) {
        if (!file.toFile().exists())
            file.toFile().mkdirs();
        return file;
    }
    @Override
    public Attachment getUsersExcelList() {
        try {
            List<PaymentDetail> list=paymentDetailService.findAllList();
            Attachment attachment=new Attachment();

            Path path1= Paths.get(uploadPath+"/excelFiles");
            path1=checkPackage(path1);

            try {
                attachment=attachmentRepository.findByName("users.xls");
                if (attachment==null){
                    attachment=new Attachment();
                    attachment.setHashCode(UUID.randomUUID().toString()+".xls");
                }
            }catch (Exception e){
                attachment=new Attachment();
                System.out.println(e);
            }



            attachment.setAuth(false);
            attachment.setName("users.xls");
            attachment.setUploadPath(path1.toFile().getAbsolutePath());
            attachment.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            String dest  =path1.toFile().getAbsolutePath()+"/"+attachment.getHashCode();
            File file=new File(dest);

            WritableWorkbook workbook = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook.createSheet("ZakoITAcademy", 0);

            WritableCellFormat headerFormat = new WritableCellFormat();
            WritableFont font
                    = new WritableFont(WritableFont.ARIAL, 14, WritableFont.NO_BOLD);
            headerFormat.setFont(font);
            headerFormat.setBackground(Colour.LIGHT_BLUE);
            headerFormat.setWrap(true);

            Label headerLabel = new Label(0, 0, "FirstName", headerFormat);
            sheet.setColumnView(0, 40);
            sheet.addCell(headerLabel);

            headerLabel = new Label(1, 0, "LastName", headerFormat);
            sheet.setColumnView(1, 40);
            sheet.addCell(headerLabel);

            headerLabel = new Label(2, 0, "CourseName", headerFormat);
            sheet.setColumnView(2, 25);
            sheet.addCell(headerLabel);

            headerLabel = new Label(3, 0, "Modules", headerFormat);
            sheet.setColumnView(3, 25);
            sheet.addCell(headerLabel);

            headerLabel = new Label(4, 0, "cardNumber", headerFormat);
            sheet.setColumnView(4, 25);
            sheet.addCell(headerLabel);

            headerLabel = new Label(5, 0, "exp", headerFormat);
            sheet.setColumnView(5, 25);
            sheet.addCell(headerLabel);

            headerLabel = new Label(6, 0, "Summa", headerFormat);
            sheet.setColumnView(6, 25);
            sheet.addCell(headerLabel);

            headerLabel = new Label(7, 0, "CommissionSum", headerFormat);
            sheet.setColumnView(7, 25);
            sheet.addCell(headerLabel);

            headerLabel = new Label(8, 0, "TransferId", headerFormat);
            sheet.setColumnView(8, 25);
            sheet.addCell(headerLabel);

            headerLabel = new Label(9, 0, "Date", headerFormat);
            sheet.setColumnView(9, 25);
            sheet.addCell(headerLabel);

            WritableCellFormat cellFormat = new WritableCellFormat();
            cellFormat.setWrap(true);

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i)!=null) {
                    String moduleText = "";
                    if (list.get(i).getPayment()!=null) {
                        if (list.get(i).getPayment().getUser()!=null) {
                            Label cellLabel = new Label(0, (i + 1), list.get(i).getPayment().getUser().getFirstName(), cellFormat);
                            sheet.addCell(cellLabel);
                            cellLabel = new Label(1, (i + 1), list.get(i).getPayment().getUser().getLastName(), cellFormat);
                            sheet.addCell(cellLabel);
                        }
                        if (list.get(i).getPayment().getCourse()!=null) {
                            Label cellLabel = new Label(2, (i + 1), list.get(i).getPayment().getCourse().getTitleRu(), cellFormat);
                            sheet.addCell(cellLabel);
                        }
                        List<Module> modules = list.get(i).getPayment().getModules();
                        for (Module module : modules) {
                            if(moduleText==""){
                                moduleText=module.getNameRu();
                            }else {
                                moduleText = moduleText + "\n"+ module.getNameRu();
                            }
                        }
                    }
                    Label cellLabel = new Label(3, (i + 1), moduleText, cellFormat);
                    sheet.addCell(cellLabel);

                    cellLabel = new Label(4, (i + 1), list.get(i).getCardNumber(), cellFormat);
                    sheet.addCell(cellLabel);

                    cellLabel = new Label(5, (i + 1), list.get(i).getExp(), cellFormat);
                    sheet.addCell(cellLabel);

                    Number cellNumber = new Number(6, (i + 1), list.get(i).getSum(), cellFormat);
                    sheet.addCell(cellNumber);

                    cellNumber = new Number(7, (i + 1), list.get(i).getCommissionSum(), cellFormat);
                    sheet.addCell(cellNumber);

                    cellLabel = new Label(8, (i + 1), list.get(i).getTransferId(), cellFormat);
                    sheet.addCell(cellLabel);

                    Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String s = formatter.format(list.get(i).getCreateAt());
                    cellLabel = new Label(9, (i + 1), s);
                    sheet.addCell(cellLabel);
                }
            }

            workbook.write();
            workbook.close();
            attachment.setSize(file.length());
            return attachmentRepository.save(attachment);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
