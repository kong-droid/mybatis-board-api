package site.kongdroid.api.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import site.kongdroid.api.dto.request.attach.AttachDto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public interface AttachService {
    Map<String, Object> getAttaches(Map<String, Object> requestMap);
    ResponseEntity<InputStreamResource> downloadFile(int attachSeq) throws FileNotFoundException;
    Map<String, Object> addAttach(Integer memberSeq, AttachDto.AddAttachReq req) throws IOException;
    Map<String, Object> physicalRemoveAttach(Integer memberSeq, int attachSeq);
}
