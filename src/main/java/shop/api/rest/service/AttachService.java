package shop.api.rest.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import shop.api.rest.dao.CustomDao;
import shop.api.rest.dto.attach.AttachDto;
import shop.api.rest.exception.BadRequestException;
import shop.api.rest.exception.ResourceNotFoundException;
import shop.api.rest.util.FileUtil;

@Service
@RequiredArgsConstructor
public class AttachService {
    
	private final CustomDao dao;
	
	@Value("${file.upload.location}")
    private String uploadPath;
	
    public Map<String, Object> getAttaches(Map<String, Object> requestMap) {
    	Map<String, Object> responseMap = new HashMap<>();        
        List<Map<String, Object>> attaches = dao.dbDetails("attach.getAttaches", requestMap);
        responseMap.put("attaches", attaches);
        return responseMap;
    }
    
	@Transactional
    public Map<String, Object> addAttach(AttachDto.AddAttachReq req) throws IOException {
		Map<String, Object> responsesMap = new HashMap<>();
		ArrayList attached = new ArrayList<>();
		for(MultipartFile maps : req.getFiles()) {            
            String calPath = FileUtil.calcPath(uploadPath);
            String saveName = FileUtil.uploadFile(calPath, maps.getOriginalFilename(), maps.getBytes());
            Map<String, Object> requestMap = new HashMap<>();

            requestMap.put("realName",          maps.getOriginalFilename());
            requestMap.put("uuidName",      	saveName.substring(saveName.lastIndexOf("/") + 1));
            requestMap.put("fileType",          maps.getOriginalFilename().substring(maps.getOriginalFilename().lastIndexOf(".") + 1));
            requestMap.put("fileSize",          maps.getBytes().length);
            requestMap.put("filePath",      	calPath);
            requestMap.put("tbName", 			req.getTbName());
            requestMap.put("tbSeq",         	req.getTbSeq());
            requestMap.put("tbType",         	req.getTbType());
            requestMap.put("memberSeq",         req.getMemberSeq());
            
            int effectRow = dao.dbInsert("attach.addAttach", requestMap);
            
            if (effectRow < 0) {
            	throw new BadRequestException("Invalid Error");
            } else {
            	Map<String, Object> responseMap = new HashMap<>();
            	responseMap.put("attachSeq",	requestMap.get("attachSeq"));
            	responseMap.put("fullPath", 	(String)requestMap.get("filePath") + (String)requestMap.get("uuidName"));
            	attached.add(responseMap);
            }       
        }
		responsesMap.put("attached", attached);
		return responsesMap;
    }

    public void physicalRemoveAttach(int attachSeq) {
    	Map<String, Object> requestMap = new HashMap<>();        
    	requestMap.put("attachSeq", attachSeq);
    	List<Map<String, Object>> attaches = dao.dbDetails("attach.getAttaches", requestMap);
    	if(!CollectionUtils.isEmpty(attaches)) {
    	    if(attaches.get(0).get("attachSeq").equals(attachSeq)) {
    	        if(dao.dbDelete("attach.removeAttach", requestMap) < 0) {
    	            throw new BadRequestException("Invalid Error"); 
    	        } else {
    	            try {
                        FileUtil.removeFiles(attaches);
                    } catch (IOException e) {
                        throw new ResourceNotFoundException("Invalid Error");
                    }
    	        }
    	    }
    	} else {
    	    throw new ResourceNotFoundException("Invalid Error");
    	}
    }
}
