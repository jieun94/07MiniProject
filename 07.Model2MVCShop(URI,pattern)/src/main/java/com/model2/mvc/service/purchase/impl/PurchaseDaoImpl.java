package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDao;

@Repository("purchaseDaoImpl")
public class PurchaseDaoImpl implements PurchaseDao{
	
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public PurchaseDaoImpl() {
		System.out.println(this.getClass());
	}
	
	public void insertPurchase(Purchase purchase) throws Exception{
		sqlSession.insert("PurchaseMapper.insertPurchase", purchase);
		sqlSession.update("PurchaseMapper.updateProdNum", purchase);
	}
	
	public Purchase findPurchase(int tranNo) throws Exception {
		return sqlSession.selectOne("PurchaseMapper.getPurchase",tranNo);
	}
	
	public Purchase findPurchase2(int prodNo) throws Exception {
		return sqlSession.selectOne("PurchaseMapper.getPurchase",prodNo);
	}
	
	//public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception { 
	//	return sqlSession.selectMap("PurchaseMapper.getPcList", buyerId); 
	//}
	
	public List<Purchase> getPurchaseList(Search search, String buyerId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search", search);
		map.put("buyerId", buyerId);
		
		return sqlSession.selectList("PurchaseMapper.getPcList", map); 
	}
	
	public List<Purchase> getSaleList(Search search, int prodNo) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search", search);
		map.put("prodNo", prodNo);
		
		return sqlSession.selectList("PurchaseMapper.getSaleList", map); 
	}
	
	public void updatePurchase(Purchase purchase) throws Exception {
		sqlSession.update("PurchaseMapper.updatePurchase", purchase);
	}
	
	public void updateTranCode(Purchase purchase) throws Exception {
		sqlSession.update("PurchaseMapper.updateTranCode", purchase);
	}
	
	public int getTotalCount(String buyerId) throws Exception {
		return sqlSession.selectOne("PurchaseMapper.getTotalCount", buyerId);
	}
	
	public int getTotalCount(Search search) throws Exception {
		return sqlSession.selectOne("PurchaseMapper.getTotalCount", search);
	}
	
	public int getProdCount(int prodNo) throws Exception {
		return sqlSession.selectOne("PurchaseMapper.getProdCount", prodNo);
	}
	
	public void updateProdNum(Purchase purchase) throws Exception {
		sqlSession.update("ProductMapper.updateProdNum", purchase);
	}
	
	

}
