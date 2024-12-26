package com.foryou.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.foryou.webapp.entity.BestOfferProduct;
import com.foryou.webapp.entity.Image;
import com.foryou.webapp.repository.BestOfferRepository;
import com.foryou.webapp.service.BestOfferProductService;
import com.foryou.webapp.service.ProductService;

@CrossOrigin(origins =  "http://localhost:3000")
@Controller
public class ImageController {
    @Autowired
    private ProductService productService;
    @Autowired
    private BestOfferProductService bestOfferProductService;
    
    @Autowired
    private BestOfferRepository bestOfferRepository;
    
    

//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    // get all employees
//    @GetMapping("/employees")
//    public List<Employee> getAllEmployees(){
//        return employeeRepository.findAll();
//    }
//
//    // create employee rest api
//    @PostMapping("/employees")
//    public Employee createEmployee(@RequestBody Employee employee) {
//        return employeeRepository.save(employee);
//    }
//
//    // get employee by id rest api
//    @GetMapping("/employees/{id}")
//    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
//        Employee employee = employeeRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
//        return ResponseEntity.ok(employee);
//    }
    @GetMapping
    public ResponseEntity<List<Image>> getAllProducts() {
        List<Image> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    

    @GetMapping({"/home","/index"})
	public String HomePage(Model model) {
		List<BestOfferProduct> bestofferproduct = bestOfferRepository.findAll();
    	model.addAttribute("bestofferproduct",bestofferproduct);

		return "navigation";
	}
    

    @GetMapping("/furniture-store/product/upload")
    public String FormToAddProduct( Model model){
    	Image image = new Image();
    	model.addAttribute("image",image);
    	return "form-for-add-product";
    }
    @PostMapping("/upload")
    public String uploadImage(@RequestParam("category") String category,
    						  @RequestParam("productName") String productName,
    						  @RequestParam("productPath") String productpath,
    						  @RequestParam("originalPrice") String originalPrice,
    						  @RequestParam("extraOfferPrice") String extraOfferPrice,
    						  @RequestParam("productPrice") String productPrice,
                              @RequestParam("productDescription") String description,
                              RedirectAttributes redirectAttributes) {
        try {
            // Save product details to the database
            Image image = new Image();
            
            image.setProductCategory(category);
            image.setProductName(productName);
            image.setProductPath(productpath);
            image.setProductPrice(originalPrice); 
            image.setProductPrice(extraOfferPrice); 
            image.setProductPrice(productPrice); 
            image.setProductDescription(description);
            
            productService.saveImage(image);

            redirectAttributes.addFlashAttribute("message", "Image URL saved successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Failed to save image URL!");
        }
        return "redirect:/furniture-store/product/upload";
    }

    @GetMapping("/images/{category}")
    public String getImagesByCategory(@PathVariable("category") String category, Model model) {
        // Retrieve images from the database based on the category
        List<Image> images = productService.getImagesByProductCategory(category);
        model.addAttribute("images", images);
        return "images";
    }
   
    @GetMapping("/product/{id}")
    public String getProductById( @PathVariable Long id, Model model) {
    	Image product = productService.getProductById(id);
    	model.addAttribute("product",product);
    	return "product-details";
    	
    }
    
    @GetMapping("/cart") 
    public String cartPage() {
    	return "productCart";
    }

    
    
    @GetMapping("/")
    public String getAllBestOffers(Model model) {
    	List<BestOfferProduct> bestofferproduct = bestOfferRepository.findAll();
    	model.addAttribute("bestofferproduct",bestofferproduct);
    	return "navigation";
    	
    }
    
    
    
    
    
    
}