package com.model2.mvc.web.purchase;

import java.util.Map;

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

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;

@Controller
@RequestMapping("/purchase/*")
public class PurchaseController {
	
	//Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	@Autowired
	private ProductService prodService;
	//@Autowired
	//private UserService userService;
	
	//Constructor
	public PurchaseController(){
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	//@RequestMapping("/addPurchaseView.do")
	@RequestMapping(value="addPurchase", method = RequestMethod.GET)
	public ModelAndView addPurchase(@RequestParam("prod_no") int prodNo, HttpSession session) throws Exception {
		
		System.out.println("/purchase/addPurchase : GET");
		
		User user = (User)session.getAttribute("user");
		Product prod = prodService.getProduct(prodNo);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/addPurchaseView.jsp");
		modelAndView.addObject("user",user);
		modelAndView.addObject("prod", prod);
		
		return modelAndView;
	}
	
	//@RequestMapping("/addPurchase.do")
	@RequestMapping(value="addPurchase", method = RequestMethod.POST)
	public ModelAndView addPurchase(HttpSession session, Product product, Purchase purchase) throws Exception {
		
		System.out.println("/purchase/addPurchase : POST");
		
		User user = (User)session.getAttribute("user");
		purchase.setPurchaseProd(product);
		purchase.setBuyer(user);
		
		purchaseService.addPurchase(purchase);
		//purchaseService.updateProdNum(purchase);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/addPurchase.jsp");
		modelAndView.addObject("purchase", purchase);
		
		return modelAndView;
	}
	
	//@RequestMapping("/listPurchase.do")
	@RequestMapping(value="listPurchase")
	public ModelAndView listPurchase(@ModelAttribute("search") Search search, HttpSession session) throws Exception {
		
		System.out.println("/purchase/listPurchase : GET / POST");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		String buyerId = ((User)session.getAttribute("user")).getUserId();
		
		
		// Business logic 수행
		Map<String , Object> map=purchaseService.getPurchaseList(search, buyerId);
		//purchase.setTranCode(purchase.getTranCode().trim());
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/listPurchase.jsp");
		modelAndView.addObject("search", search);
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		
		return modelAndView;
	}
	
	@RequestMapping(value="getPurchase", method = RequestMethod.GET)
	public ModelAndView getPurchase(@RequestParam("tranNo") int tranNo, Purchase purchase) throws Exception {
		
		System.out.println("/purchase/getPurchase : GET");
		
		purchase = purchaseService.getPurchase(tranNo);
		purchase.setPaymentOption(purchase.getPaymentOption().trim());
		purchase.setTranCode(purchase.getTranCode().trim());
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/getPurchase.jsp");
		modelAndView.addObject("purchase",purchase);
		
		return modelAndView;
	}
	
	//@RequestMapping("/updatePurchaseView.do")
	@RequestMapping(value="updatePurchase", method = RequestMethod.GET)
	public ModelAndView updatePurchase(@RequestParam("tranNo") int tranNo, Purchase purchase) throws Exception{
		
		System.out.println("/purchase/updatePurchase : GET");
		
		purchase = purchaseService.getPurchase(tranNo);
		purchase.setPaymentOption(purchase.getPaymentOption().trim());
		purchase.setTranCode(purchase.getTranCode().trim());
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/updatePurchaseView.jsp");
		modelAndView.addObject("purchase",purchase);
		
		return modelAndView;
	}
	
	//@RequestMapping("/updatePurchase.do")
	@RequestMapping(value="updatePurchase", method = RequestMethod.POST)
	public ModelAndView updatePurchase(Purchase purchase) throws Exception {
		
		System.out.println("/purchase/updatePurchase : POST");
		
		purchaseService.updatePurcahse(purchase);
		//purchase.setPaymentOption(purchase.getPaymentOption().trim());
		//purchase.setTranCode(purchase.getTranCode().trim());
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/purchase/getPurchase?tranNo="+purchase.getTranNo());
		modelAndView.addObject("purchase",purchase);
		
		return modelAndView;
	}
	
	@RequestMapping(value="updateTranCode", method = RequestMethod.GET)
	public ModelAndView updateTranCode( Purchase purchase ) throws Exception {
		
		System.out.println("/purchase/updateTranCode : GET");
		
		purchaseService.updateTranCode(purchase);
		
		ModelAndView modelAndView = new ModelAndView();
		if (purchase.getTranCode().contentEquals("3")) {
			modelAndView.setViewName("redirect:/purchase/listPurchase");
		} else {
			modelAndView.setViewName("redirect:/purchase/listProduct?menu=manage");
		}
		modelAndView.addObject("purchase",purchase);
		return modelAndView;
	}
	
	@RequestMapping(value="listSale")
	public ModelAndView listSale(@ModelAttribute("search") Search search, @RequestParam("prodNo") int prodNo, Purchase purchase) throws Exception {
		
		System.out.println("/purchase/listSale : GET / POST");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=purchaseService.getSaleList(search, prodNo);
		//purchase.setPaymentOption(purchase.getPaymentOption().trim());
		//purchase.setTranCode(purchase.getTranCode().trim());
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/purchase/listSale.jsp");
		modelAndView.addObject("search", search);
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		
		return modelAndView;
	}
	
}
