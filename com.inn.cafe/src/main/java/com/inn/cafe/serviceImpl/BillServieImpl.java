package com.inn.cafe.serviceImpl;

import com.inn.cafe.POJO.Bill;
import com.inn.cafe.POJO.User;
import com.inn.cafe.jwt.filter.JwtFilter;
import com.inn.cafe.repository.BillRepository;
import com.inn.cafe.repository.UserRepository;
import com.inn.cafe.service.BillService;
import com.inn.cafe.utils.BaseResponse;
import com.inn.cafe.utils.CafeUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.io.IOUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Service
public class BillServieImpl implements BillService {
////////////// Json Data
//    {"fileName":"xyzzzxy","contactNumber": "1234567890","email": "test@gmail.com","name": "test","paymentMethod": "Cash","productDetails": "[{\"id\":18,\"name\":\"Doppio Coffee\",\"category\":\"Coffeeeeeee\",\"quantity\":\"1\",\"price\":120,\"total\":120},{\"id\":5,\"name\":\"Chocolate Frosted Doughnut\",\"category\":\"Doughnut\",\"quantity\":\"1\",\"price\":159,\"total\":159},{\"id\":18,\"name\":\"Doppio Coffee\",\"category\":\"Coffee\",\"quantity\":\"1\",\"price\":120,\"total\":120},{\"id\":5,\"name\":\"Chocolate Frosted Doughnut\",\"category\":\"Doughnut\",\"quantity\":\"1\",\"price\":159,\"total\":159}]","totalAmount": "279"}

    @Autowired
    CafeUtils cafeUtils;

    @Autowired
    JwtFilter jwtFilter;
    @Autowired
    BillRepository repo;
    @Autowired
    UserRepository userRepo;
    @Override
    public ResponseEntity<BaseResponse> generateReport(Map<String, String> requestMap) {

        try {
            String fileName;
            if(validateRequestMap(requestMap)){

                System.out.println("validateRequestMap");

                if (requestMap.containsKey("isGenerate")  && Boolean.parseBoolean(requestMap.get("isGenerate"))){
                      System.out.println("isGenerate");
                      fileName=(String) requestMap.get("uuid");
                }else {
                    System.out.println("Not  isGenerate");
                    fileName = CafeUtils.getUUID();
                    requestMap.put("uuid",fileName);
                    insertBill(requestMap);
                }

                String data="Name: "+requestMap.get("name")+"\n"+"Contact Number: "+requestMap.get("contactNumber")+"\n"+"Email: "+requestMap.get("email")+"\n"+ "Payment Method: "+requestMap.get("paymentMethod")+"\n";

                Document document=new com.itextpdf.text.Document();

                PdfWriter.getInstance(document,new FileOutputStream(STORE_LOCATION+"\\"+fileName+".pdf"));

                document.open();

                setRectangleInPdf(document);

                Paragraph chunk=new  Paragraph("Cafe Management System",getFont("Header"));
                chunk.setAlignment(Element.ALIGN_CENTER);
                document.add(chunk);

                Paragraph paragraph=new Paragraph(data+"\n\n",getFont("Data"));
                document.add(paragraph);
                PdfPTable table=new PdfPTable(5);
                table.setWidthPercentage(100);
                addTableHeader(table);



                JSONArray jsonArray = cafeUtils.getJsonArrayFromString(requestMap.get("productDetails"));
                for (int i=0;i<jsonArray.length();i++){
                    addRows(table,cafeUtils.getMapFromJson(jsonArray.getString(i)));
                }

                document.add(table);
                Paragraph footer=new Paragraph("Total  "+requestMap.get("totalAmount")+"\n"+"Thank you for visiting ,Please visit again.",getFont("Data"));
                 document.add(footer);
                 document.close();
                 return new ResponseEntity<>(cafeUtils.generateSuccessResponse("{\"uuid\":\""+fileName+"\"}","",""),HttpStatus.OK);
            }

            return  new ResponseEntity<>(cafeUtils.generateSuccessResponse(null,"Required data not found"),HttpStatus.BAD_REQUEST);

        }catch (Exception e){
            return  new ResponseEntity<>(cafeUtils.generateErrorResponse(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<BaseResponse> getAllBills() {
        try {

            if(jwtFilter.isAdmin()){
                return new ResponseEntity<>(cafeUtils.generateSuccessResponse(repo.getAllBills(),"",""),HttpStatus.OK);
            }else {

                Optional<User>user=userRepo.findByUsername(jwtFilter.getCurrentUsername());

                if (user.isPresent()){

                    return  new ResponseEntity<>(cafeUtils.generateSuccessResponse(repo.getBillByUsername(user.get().getUsername()),"",""),HttpStatus.OK);
                }
                return  new ResponseEntity<>(cafeUtils.generateSuccessResponse(null,"User not found",""),HttpStatus.BAD_REQUEST);
            }


        }catch (Exception e){

            return  new ResponseEntity<>(cafeUtils.generateErrorResponse(e),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<BaseResponse> getPdf(Map<String,String>request) {

        try {
            byte[] byteArray=new byte[0];

            if(!request.containsKey("uuid") && validateRequestMap(request)){
                return new ResponseEntity<>(cafeUtils.generateSuccessResponse(byteArray,"",""),HttpStatus.BAD_REQUEST);
            }

            String filePath= STORE_LOCATION+"\\"+(String) request.get("uuid")+".pdf";
            if(cafeUtils.isExistFile(filePath)){
                System.out.println("isFileExist");
                request.put("isGenerate","true");
                ResponseEntity res= generateReport(request);
                BaseResponse uuid = (BaseResponse) res.getBody();
                byteArray =getByteArray(STORE_LOCATION+"\\"+uuid.getData().toString().substring(9,26)+".pdf");
                return  new ResponseEntity<>(cafeUtils.generateSuccessResponse(byteArray,"",""),HttpStatus.OK);
            }else {
                request.put("isGenerate","false");
                ResponseEntity res= generateReport(request);
                System.out.println(res);
                BaseResponse uuid= (BaseResponse) res.getBody();
                System.out.println("uuid : "+uuid.getData().toString().substring(9,26));
                byteArray =getByteArray(STORE_LOCATION+"\\"+uuid.getData().toString().substring(9,26)+".pdf");
                return new ResponseEntity<>(cafeUtils.generateSuccessResponse(byteArray,"",""),HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(cafeUtils.generateErrorResponse(e),HttpStatus.OK);

        }

    }

    @Override
    public ResponseEntity<BaseResponse> deleteBill(Integer id) {
        try {
          Optional<Bill>  bill= repo.findById(id);
          if (bill.isPresent()){
              repo.deleteBill(bill.get().getId());
              return  new ResponseEntity<>(cafeUtils.generateSuccessResponse(null,DELETE_MESSAGE,DELETE_MESSAGE_BN),HttpStatus.OK);
          }
            return  new ResponseEntity<>(cafeUtils.generateSuccessResponse(null,ID_DOESNOT_EXIST,ID_DOESNOT_EXIST_BN),HttpStatus.OK);

        }catch (Exception e){
            return  new ResponseEntity<>(cafeUtils.generateErrorResponse(e),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private byte[] getByteArray(String filePath)throws  Exception {



        File initialFile =new File(filePath);
//        System.out.println("getByteArray"+targetStream);
        InputStream targetStream=new  FileInputStream(initialFile);

        System.out.println("getByteArray"+targetStream);

        byte[] byteArray = IOUtils.toByteArray(targetStream);
        targetStream.close();
        return  byteArray;
    }
    private void addRows(PdfPTable table, Map<String, Object> data) {
        String price = Double.toString((Double) data.get("price"));
        String total = Double.toString((Double) data.get("total"));
//        System.out.println("typeeeee : "+price.getClass().getSimpleName() +"::::::::"+total.getClass().getSimpleName());
        table.addCell((String) data.get("name"));
        table.addCell((String) data.get("category"));
        table.addCell((String) data.get("quantity"));
        table.addCell(price);
        table.addCell(total);
    }
    private void addTableHeader(PdfPTable table) {
        Stream.of("Name","Category","Quantity","Price","Sub Total")
                .forEach(columnTitle->{
                    PdfPCell header=new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorder(2);
                    header.setPhrase(new Phrase(columnTitle));
                    header.setBackgroundColor(BaseColor.YELLOW);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);

                });
    }
    private Font getFont(String type){
        log.info("Inside getFont");
        switch (type){

            case "Header":
                Font headerFont=FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE,18,BaseColor.BLACK);
                headerFont.setStyle(Font.BOLD);
                return headerFont;
            case "Data":

                Font dataFont=FontFactory.getFont(FontFactory.TIMES_ROMAN,11,BaseColor.BLACK);
                dataFont.setStyle(Font.BOLD);
                return  dataFont;
            default:
                return new  Font();
        }

    }
    private void setRectangleInPdf(Document document) throws DocumentException {
        log.info("Inside setrectangleInPdf");
        Rectangle rect=new Rectangle(577,825,18,15);

        rect.enableBorderSide(1);
        rect.enableBorderSide(2);
        rect.enableBorderSide(4);
        rect.enableBorderSide(8);
        rect.setBorderColor(BaseColor.BLACK);
        rect.setBorderWidth(1);
        document.add(rect);
    }
    private void insertBill(Map<String, String> requestMap) {

        try {

            Bill bill=new Bill();
            bill.setUuid((String) requestMap.get("uuid"));
            bill.setName(requestMap.get("name"));
            bill.setEmail(requestMap.get("email"));
            bill.setContactNumber(requestMap.get("contactNumber"));
            bill.setTotal(Integer.parseInt(requestMap.get("totalAmount")));
            bill.setPaymentMethod(requestMap.get("paymentMethod"));
            bill.setProductDetails(requestMap.get("productDetails"));
            bill.setCreatedBy(jwtFilter.getCurrentUsername());
            repo.save(bill);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private boolean validateRequestMap(Map<String, String> requestMap) {
        return  requestMap.containsKey("name")
                && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email")
                && requestMap.containsKey("paymentMethod")
                && requestMap.containsKey("productDetails")
                && requestMap.containsKey("totalAmount");
    }
}