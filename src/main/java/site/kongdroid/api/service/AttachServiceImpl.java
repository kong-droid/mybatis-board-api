package site.kongdroid.api.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.val;
import lombok.RequiredArgsConstructor;
import site.kongdroid.api.dao.CustomDao;
import site.kongdroid.api.dto.request.attach.AttachDto;
import site.kongdroid.api.exception.BadRequestException;
import site.kongdroid.api.exception.ResourceNotFoundException;
import site.kongdroid.api.util.FileUtil;

@Service
@RequiredArgsConstructor
public class AttachServiceImpl implements AttachService {
    
	private final CustomDao dao;
	
	@Value("${file.upload.location}")
    private String uploadPath;

    public Map<String, Object> getAttaches(Map<String, Object> requestMap) {
    	val responseMap = new HashMap<String, Object>();
        responseMap.put("attaches", dao.dbDetails("attach.getAttaches", requestMap));
        return responseMap;
    }
    
	@Transactional
    public Map<String, Object> addAttach(AttachDto.AddAttachReq req) throws IOException {
		val responsesMap = new HashMap<String, Object>();
		val attached = new ArrayList<>();
		for(MultipartFile maps : req.getFiles()) {            
            String calPath = FileUtil.calcPath(uploadPath);
            String saveName = FileUtil.uploadFile(calPath, maps.getOriginalFilename(), maps.getBytes());
            val requestMap = new HashMap<String, Object>();

            requestMap.put("realName", maps.getOriginalFilename());
            requestMap.put("uuidName", saveName.substring(saveName.lastIndexOf("/") + 1));
            requestMap.put("fileType", maps.getOriginalFilename().substring(maps.getOriginalFilename().lastIndexOf(".") + 1));
            requestMap.put("fileSize", maps.getBytes().length);
            requestMap.put("filePath", calPath);
            requestMap.put("tbName", req.getTbName());
            requestMap.put("tbSeq", req.getTbSeq());
            requestMap.put("tbType", req.getTbType());
            requestMap.put("memberSeq", req.getMemberSeq());

            if (dao.dbInsert("attach.addAttach", requestMap) < 0) {
            	throw new BadRequestException("Invalid Error");
            } else {
            	val responseMap = new HashMap<String, Object>();
            	responseMap.put("attachSeq", requestMap.get("attachSeq"));
            	responseMap.put("realName", maps.getOriginalFilename());
            	responseMap.put("fullPath", requestMap.get("filePath") + "/" + requestMap.get("uuidName"));
            	attached.add(responseMap);
            }       
        }
		responsesMap.put("attached", attached);
		return responsesMap;
    }
	
    public ResponseEntity<InputStreamResource> downloadFile(int attachSeq) throws FileNotFoundException {
        val requestMap = new HashMap<String, Object>();

        requestMap.put("attachSeq", attachSeq);
        val attach = dao.dbDetail("attach.getAttaches", requestMap);

        if(CollectionUtils.isEmpty(attach)) {
            throw new ResourceNotFoundException("Not Found");
        } else {
            String fullPath = String.valueOf(attach.get("fullPath"));
            String originalFileName = FileUtil.transUtf8FileName(String.valueOf(attach.get("realPath")));
            File file = new File(fullPath);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + originalFileName + "\"")
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream;"))
                .body(resource);
        }
    }
	
    public int physicalRemoveAttach(int attachSeq) {
    	val requestMap = new HashMap<String, Object>();
    	requestMap.put("attachSeq", attachSeq);

        val attaches = dao.dbDetails("attach.getAttaches", requestMap);
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
    	    return attachSeq;
    	} else {
    	    throw new ResourceNotFoundException("Invalid Error");
    	}
    }
}
