package com.model2.mvc.web.review;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.Review;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.review.ReviewService;

@Controller
@RequestMapping("/review/*")
public class ReviewController {
	
	//Field
	@Autowired
	@Qualifier("reviewServiceImpl")
	private ReviewService reviewService;
	@Autowired
	private PurchaseService purchaseService;
	@Autowired
	private ProductService prodService;
	//@Autowired
	//private UserService userService;
	
	//Constructor
	public ReviewController(){
		System.out.println(this.getClass());
	}
	
//	@Value("#{commonProperties['pageUnit']}")
//	int pageUnit;
//	
//	@Value("#{commonProperties['pageSize']}")
//	int pageSize;
	
	@RequestMapping(value="addReview", method = RequestMethod.GET)
	public ModelAndView addReview(@RequestParam("tranNo") int tranNo, HttpSession session) throws Exception {
		
		System.out.println("/review/addReview : GET");
		
		User user = (User)session.getAttribute("user");
		session.setAttribute("user", user);
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/review/addReviewView.jsp");
		modelAndView.addObject("purchase", purchase);
		
		return modelAndView;
	}
	
	@RequestMapping(value="addReview", method = RequestMethod.POST)
	public ModelAndView addReview(@ModelAttribute("review") Review review) throws Exception {
		
		System.out.println("/review/addReview : POST");
		
		reviewService.addReview(review);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/review/addReviewView.jsp");
		modelAndView.addObject("review",review);
		
		return modelAndView;
	}
}
