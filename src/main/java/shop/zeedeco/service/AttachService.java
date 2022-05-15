package shop.zeedeco.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import shop.zeedeco.dao.CustomDao;
import shop.zeedeco.dto.attach.AttachDto;
import shop.zeedeco.exception.BadRequestException;
import shop.zeedeco.util.FileUtil;

@Service
@RequiredArgsConstructor
public class AttachService {
    
	private final CustomDao dao;
	
	@Value("${file.upload.location}")
    private String uploadPath;
	
    public Map<String, Object> getAttaches(Map<String, Object> requestMap) throws Exception {
    	Map<String, Object> responseMap = new HashMap<>();        
        List<Map<String, Object>> attaches = dao.dbDetails("attach.getAttaches", requestMap);
        responseMap.put("attaches", attaches);
        return responseMap;
    }
    
	@Transactional
    public Map<String, Object> addAttach(AttachDto.AddAttachReq req) throws Exception {
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
            	throw new BadRequestException("저장에 실패했습니다.");
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

    public void physicalRemoveAttach(int attachSeq) throws Exception {
    	Map<String, Object> requestMap = new HashMap<>();        
    	requestMap.put("attachSeq", attachSeq);
    	int effectRow = dao.dbDelete("attach.removeAttach", requestMap);
    	if (effectRow < 0) throw new BadRequestException("삭제에 실패했습니다.");	
    }
}
