package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDao;
import com.model2.mvc.service.purchase.PurchaseService;

@Service("purchaseServiceImpl")
public class PurchaseServiceImpl implements PurchaseService{
	
	@Autowired
	@Qualifier("purchaseDaoImpl")
	private PurchaseDao purchaseDao;
	public void setPurchase(PurchaseDao purchaseDao) {
		this.purchaseDao = purchaseDao;
	}
	public PurchaseServiceImpl() {
		System.out.println(this.getClass());
	}
	
	public void addPurchase(Purchase purchase) throws Exception{
		purchaseDao.insertPurchase(purchase);
	}
	
	public Purchase getPurchase(int tranNo) throws Exception {
		return purchaseDao.findPurchase(tranNo);
	}
	
	public Purchase getPurchase2(int prodNo) throws Exception {
		return purchaseDao.findPurchase2(prodNo);
	}
	
	
	public Map<String,Object> getPurchaseList(Search search,String buyerId) throws Exception { 
		
		int totalCount = purchaseDao.getTotalCount(buyerId);
		List<Purchase> list = (List<Purchase>) purchaseDao.getPurchaseList(search, buyerId);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list );
		map.put("search", search);
		map.put("totalCount", new Integer(totalCount));
		return map;
	 }
	
	
	/*
	 * public List<Purchase> getPurchaseList(String buyerId) throws Exception {
	 * List<Purchase> list= purchaseDao.getPurchaseList(buyerId);
	 * 
	 * Map<String, Object> map = new HashMap<String, Object>(); map.put("list",
	 * list); return list; }
	 */
	
	public Map<String,Object> getSaleList(Search search, int prodNo) throws Exception {
		
		int totalCount = purchaseDao.getProdCount(prodNo);
		List<Purchase> list = (List<Purchase>) purchaseDao.getSaleList(search, prodNo);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list );
		map.put("search", search);
		map.put("totalCount", new Integer(totalCount));
		return map;
	}
	
	public void updatePurcahse(Purchase purchase) throws Exception {
		purchaseDao.updatePurchase(purchase);
	}
	
	public void updateTranCode(Purchase purchase) throws Exception {
		purchaseDao.updateTranCode(purchase);
	}
	
	public void updateProdNum(Purchase purchase) throws Exception {
		purchaseDao.updateProdNum(purchase);
	}
	
	
}
