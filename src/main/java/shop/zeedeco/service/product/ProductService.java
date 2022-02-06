package shop.zeedeco.service.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.zeedeco.dao.CustomDao;
import shop.zeedeco.exception.BadRequestException;
import shop.zeedeco.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final CustomDao dao;
	
	public Map<String, Object> getProducts(Map<String, Object> requestMap, Integer page, Integer size) throws Exception {
    	Map<String, Object> responseMap = new HashMap<>();
        
        if(page != null && size != null) {
        	requestMap.put("startRow", page * size);
            requestMap.put("size", size);
        }
        
        List<Map<String, Object>> products = dao.dbDetails("product.getProducts", requestMap);
        Map<String, Object> listCount = dao.dbDetail("product.getProductsCnt", requestMap);
        Integer totalCount = Integer.parseInt(String.valueOf(listCount.get("cnt")));
        if(products != null) {
        	for(Map<String, Object> map : products) {
        		Map<String, Object> resultMap = new HashMap<>();
        		resultMap.put("productSeq", map.get("productSeq"));
        		List<Map<String, Object>> details = dao.dbDetails("product.getProductDetails", resultMap);
        		map.put("details", details);
        	}
            responseMap.put("products", products);
            responseMap.put("totalCount", totalCount);
            
            
        } else {
        	new ResourceNotFoundException("데이터가 없습니다.");
        }
        return responseMap;	
	}
	
	public Map<String, Object> getProduct(int productSeq) throws Exception {
		Map<String, Object> requestMap = new HashMap<>();
		requestMap.put("productSeq", productSeq);
    	Map<String, Object> responseMap = dao.dbDetail("product.getProducts", requestMap);
        
        if(responseMap != null) {
        	List<Map<String, Object>> details = dao.dbDetails("product.getProductDetails", requestMap);
        	responseMap.put("details", details);
        } else {
        	new ResourceNotFoundException("데이터가 없습니다.");
        }
        return responseMap;	
	}
	
	public void addProduct(Map<String, Object> requestMap) throws Exception {
		int effectRow = this.dao.dbInsert("product.addProduct", requestMap);
		if ( effectRow < 0 ) new BadRequestException("저장에 실패했습니다."); 
	}
	
	public void setProduct(Map<String, Object> requestMap) throws Exception {
		int effectRow = this.dao.dbUpdate("product.setProduct", requestMap);
		if ( effectRow < 0 ) new BadRequestException("저장에 실패했습니다."); 
	}
	
	public void logicalRemoveProduct(Map<String, Object> requestMap) throws Exception {
		int effectRow = this.dao.dbUpdate("product.setProduct", requestMap);
		if ( effectRow < 0 ) new BadRequestException("논리적 삭제에 실패했습니다."); 
	}
	
	public void physicalRemoveProduct(int productSeq) throws Exception {
		Map<String, Object> requestMap = new HashMap<>();
		requestMap.put("productSeq", productSeq);
		int effectRow = this.dao.dbInsert("product.removeProduct", requestMap);
		if ( effectRow < 0 ) new BadRequestException("물리적 삭제에 실패했습니다."); 
	}
	
	public void addProductDetail(Map<String, Object> requestMap) throws Exception {
		int effectRow = this.dao.dbInsert("product.addProductDetail", requestMap);
		if ( effectRow < 0 ) new BadRequestException("저장에 실패했습니다."); 
	}
	
	public void setProductDetail(Map<String, Object> requestMap) throws Exception {
		int effectRow = this.dao.dbUpdate("product.setProductDetail", requestMap);
		if ( effectRow < 0 ) new BadRequestException("저장에 실패했습니다."); 
	}
	
	public void logicalRemoveProductDetail(Map<String, Object> requestMap) throws Exception {
		int effectRow = this.dao.dbUpdate("product.setProductDetail", requestMap);
		if ( effectRow < 0 ) new BadRequestException("논리적 삭제에 실패했습니다."); 
	}
	
	public void physicalRemoveProductDetail(int productDetailSeq) throws Exception {
		Map<String, Object> requestMap = new HashMap<>();
		requestMap.put("productDetailSeq", productDetailSeq);
		int effectRow = this.dao.dbInsert("product.removeProductDetail", requestMap);
		if ( effectRow < 0 ) new BadRequestException("물리적 삭제에 실패했습니다."); 
	}
}
