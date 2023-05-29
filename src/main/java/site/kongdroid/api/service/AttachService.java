package site.kongdroid.api.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import site.kongdroid.api.dto.request.attach.AttachDto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public interface AttachService {
    Map<String, Object> getAttaches(Map<String, Object> requestMap);
    Map<String, Object> addAttach(AttachDto.AddAttachReq req) throws IOException;
    ResponseEntity<InputStreamResource> downloadFile(int attachSeq) throws FileNotFoundException;
    int physicalRemoveAttach(int attachSeq);
}
