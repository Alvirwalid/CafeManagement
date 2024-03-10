package com.inn.cafe.Controller;
import com.inn.cafe.service.ProductService;
import com.inn.cafe.utils.BaseResponse;
import com.inn.cafe.utils.CafeUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    @Autowired
    ProductService service;

    private  final CafeUtils cafeUtils;
    @PostMapping("/add")
    public ResponseEntity<BaseResponse>addNewProduct(@RequestBody Map<String,String>requestMap){

        return service.addNewProduct(requestMap);
    }

    @GetMapping("/all")

    public ResponseEntity<BaseResponse> getAllProducts(){
        return  service.getAllProduct();
    }


    @GetMapping("/get-by-id")
    public  ResponseEntity<BaseResponse> getProductbyId(@RequestParam Integer id){


        return  service.getProductById(id);
    }

    @PutMapping("/update")
    public  ResponseEntity<BaseResponse>update (@RequestBody Map<String,String>requestMap){

        return  service.update(requestMap);
    }


    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<BaseResponse>delete(@PathVariable("id") Integer id){

//        System.out.println("iddddddddd : "+id);
        return  service.delete(id);
    }

    @PutMapping("/updateStatus")
    public  ResponseEntity<BaseResponse>updateStatus (@RequestBody Map<String,String>requestMap){
        return  service.updateStatus(requestMap);

    }

}
