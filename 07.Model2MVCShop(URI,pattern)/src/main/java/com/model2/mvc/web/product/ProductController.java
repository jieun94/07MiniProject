package com.model2.mvc.web.product;

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;

@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	//Filed
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService prodService;
	
	//Constructor
	public ProductController(){
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	@RequestMapping(value="addProduct")
	public String addProduct(@ModelAttribute("prod") Product prod, HttpServletRequest request) throws Exception {

		System.out.println("/product/addProduct : GET / POST");
		
		if (FileUpload.isMultipartContent(request)) {
			String temDir="C:\\Users\\user\\git\\repository\\07MiniProject\\07.Model2MVCShop(URI,pattern)\\WebContent\\images\\uploadFiles\\";
			
			DiskFileUpload fileUpload = new DiskFileUpload();
			fileUpload.setRepositoryPath(temDir);
			fileUpload.setSizeThreshold(1024*100);
			
			if (request.getContentLength() < fileUpload.getSizeMax()) {
				StringTokenizer token = null;
				
				List fileItemList = fileUpload.parseRequest(request);
				
				//size 확인하고 지우기
				int Size = fileItemList.size();
				for (int i = 0; i < Size; i++) {
					FileItem fileItem = (FileItem) fileItemList.get(i);
					
					//파라미터면 true 파일형식이면 false
					if (fileItem.isFormField()) {
						//파라미터 형식이면
						if (fileItem.getFieldName().equals("manuDate")) {
							token = new StringTokenizer(fileItem.getString("euc-kr"),"-");
							String manuDate = token.nextToken() + token.nextToken() + token.nextToken();
							//prod.setManuDate(prod.getManuDate().replace("-", ""));
							prod.setManuDate(manuDate);
						}
						else if (fileItem.getFieldName().equals("prodName")) {
							prod.setProdName(fileItem.getString("euc-kr"));
						}
						else if (fileItem.getFieldName().equals("prodDetail")) {
							prod.setProdDetail(fileItem.getString("euc-kr"));
						}
						else if (fileItem.getFieldName().equals("price")) {
							prod.setPrice(Integer.parseInt(fileItem.getString("euc-kr")));
						}
					} else { //파일 형식이면
						if (fileItem.getSize()>0) {
							int idx = fileItem.getName().lastIndexOf("\\");
						}
					}
				}
			}
		}
		
		
		prodService.addProduct(prod);
		
		return "forward:/product/addProduct.jsp";
	}
	
	@RequestMapping(value="getProduct", method=RequestMethod.GET )
	public String getProduct(@RequestParam("prodNo") int prodNo, @RequestParam("menu") String menu, HttpSession session, Model model) throws Exception {

		System.out.println("/product/getProduct : GET");
		
		Product prod = prodService.getProduct(prodNo);
		
		model.addAttribute("prod", prod);
		User user = (User)session.getAttribute("user");
		
		if (user!=null) {
			String userId = ((User)session.getAttribute("user")).getUserId();
			
			if(userId.equals(user.getUserId())){
				user.setUserId(userId);
				//session.setAttribute("user", user); 
			}

		}
		
		if (menu.contentEquals("manage")) {
			return "forward:/product/updateProductView.jsp";
		} else {
			return "forward:/product/getProduct.jsp";
		}
	}
	
	@RequestMapping(value="updateProduct", method=RequestMethod.POST )
	public String updateProduct(@ModelAttribute("prod") Product prod) throws Exception {
		
		System.out.println("/product/updateProduct : POST");
		
		prod.setManuDate(prod.getManuDate().replace("-", ""));
		prodService.updateProduct(prod);
		
		return "forward:/product/getProduct.jsp";
	}
	
	@RequestMapping(value="listProduct")
	public String listProduct( @ModelAttribute("search") Search search , Model model) throws Exception{
		
		System.out.println("/product/listProduct : GET / POST");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		
		// Business logic 수행
		Map<String , Object> map=prodService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("search", search);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		
		
		return "forward:/product/listProduct.jsp";
	}
}
