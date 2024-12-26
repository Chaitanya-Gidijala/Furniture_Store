package com.foryou.webapp.controller;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.foryou.webapp.entity.CartItem;
import com.foryou.webapp.entity.Image;
import com.foryou.webapp.entity.User;
import com.foryou.webapp.repository.CartItemRepository;
import com.foryou.webapp.repository.ImageRepository;
import com.foryou.webapp.service.CartService;
import com.foryou.webapp.service.ProductService;

@Controller
public class CartController {

	@Autowired
    private ImageRepository imageRepository;

	@Autowired
    private CartItemRepository cartItemRepository;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CartService cartService;


//	@GetMapping("/cart")
//    public String viewCart(Model model) {
//        model.addAttribute("cartItems", cartService.getCartItems());
//        return "productCart";
//    }

//    @GetMapping("/products")
//    public String listProducts(Model model) {
//        model.addAttribute("products", imageRepository.findAll());
//        return "images";
//    }
	
	//Adding product to cart
	@PostMapping("/addToCart/{productId}")
	public String addToCartItem(@PathVariable Long productId, @RequestParam int quantity,
			Long userId) {
		
		System.out.println("I am in post mapping in cart page....");
		Image product = imageRepository.findById(productId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid product id: " + productId));

		CartItem cartItem = new CartItem();
		
		User user = new User();
		
		cartItem.setImage(product);
		cartItem.setQuantity(quantity);
		
		//Set a user Id based on current session
		

		System.out.println("Before Saving cart page");
		cartItemRepository.save(cartItem);

		return "redirect:/product/{productId}/addedToCart";// Redirect to the product list page
	}

    @GetMapping("/product/{id}/addedToCart")
    public String getProductById( @PathVariable Long id, Model model) {
    	Image product = productService.getProductById(id);
    	model.addAttribute("product",product);
    	return "product-details";
    }

    
    //Shopping cart
	@GetMapping("/products/cart-items")
	public String viewCart(Model model) {
		List<CartItem> cartItems = cartService.getCartItems();
		
		Double totalPriceDouble = calculateTotalPrice1(cartItems);
        String.valueOf(totalPriceDouble);
        String totalAmount = formatPriceToIndianCurrency(totalPriceDouble);
        
        System.out.println("cart-items1");
		 for (CartItem item : cartItems) {
			 
	            // Check if the price is a valid number
	            if (isValidPrice(item.getImage().getProductPrice())) {
	                double price = Double.parseDouble(item.getImage().getProductPrice());
	                double total = price * item.getQuantity();
	                
	                String formattedPrice = formatPriceToIndianCurrency(total);
	                item.getImage().setProductPrice(formattedPrice); // Set the formatted price
	                
	            } else {
	                // Set default value if the price is not a valid number
	                item.getImage().getProductPrice();
	            }
	        }
                
        System.out.println("totalAMount======="+totalAmount);
		model.addAttribute("cartItems", cartItems);
	    model.addAttribute("totalAmount", totalAmount);

		return "productCart";
	}

	// Method to format price into Indian currency format
    private String formatPriceToIndianCurrency(double price) {
        // Format price into Indian currency format
        DecimalFormat indianCurrencyFormat = new DecimalFormat("\u20B9#,##,##0.00");
        return indianCurrencyFormat.format(price);
    }
    // Method to check if the price is a valid number
    private boolean isValidPrice(String price) {
        try {
            Double.parseDouble(price);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    
//	@GetMapping("/products")
//	public String listProducts(Model model) {
//		List<Image> products = ProductService.getAllProducts();
//		model.addAttribute("products", products);
//		return "product-details";
//	}
	
	//Delete product from cart
	@DeleteMapping("/cart/{productId}")
    public String deleteCartItem(@PathVariable("productId") Long productId) {
        cartService.deleteCartItem(productId);
        return "redirect:/products/cart-items"; // Redirect to the cart page after deletion
    }


//	    @PostMapping("/cart/add")
//	    public String addToCart(@RequestParam Long productId, @RequestParam int quantity) {
//	        Image product = productService.getProductById(productId);
//	        cartService.addToCart(product, quantity);
//	        return "redirect:/products";
//	    }
	
	//Calculating total cart amount..
	private double calculateTotalPrice(List<CartItem> cartItems) {
        double totalPrice = 0.0;
        for (CartItem item : cartItems) {
            try {
                double price = Double.parseDouble(item.getImage().getProductPrice());
                totalPrice += price;
            } catch (NumberFormatException e) {
                // Handle invalid price format
                System.err.println("Invalid price format for item: " + item.getImage().getProductName());
                System.err.println("Price value: " + item.getImage().getProductPrice());
            }
        }
        System.out.println("totalPrice"+totalPrice);
        return totalPrice;
    }
	private double calculateTotalPrice1(List<CartItem> cartItems) {
	    double totalPrice = 0.0;
	    for (CartItem item : cartItems) {
	        String priceString = item.getImage().getProductPrice();
	        System.out.println("Price string for item " + item.getImage().getProductName() + ": " + priceString);
	        try {
	            double price = Double.parseDouble(priceString.trim()); // Trim whitespace
	            System.out.println("Parsed price for item " + item.getImage().getProductName() + ": " + price);
	            totalPrice += price * item.getQuantity();
	        } catch (NumberFormatException e) {
	            // Log invalid price format and continue to the next item
	            System.err.println("Invalid price format for item: " + item.getImage().getProductName());
	            System.err.println("Price value: " + priceString);
	        }
	    }
	    System.out.println("Total price: " + totalPrice);
	    return totalPrice;
	}
	
}
