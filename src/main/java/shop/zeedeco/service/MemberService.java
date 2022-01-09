package shop.zeedeco.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import lombok.RequiredArgsConstructor;
import shop.zeedeco.dao.CustomDao;
import shop.zeedeco.exception.BadRequestException;

@Service
@RequiredArgsConstructor
public class MemberService {
	
	private final CustomDao dao;
	
    public Map<String, Object> getMembers(Map<String, Object> requestMap, Integer page, Integer size) throws Exception {
        
    	Map<String, Object> responseMap = new HashMap<>();
        
        if(page != null && size != null) {
        	requestMap.put("startRow", page * size);
            requestMap.put("size", size);
        }
        
        List<Map<String, Object>> members = dao.dbDetails("member.getMembers", requestMap);
        Map<String, Object> listCount = dao.dbDetail("member.getMembersCnt", requestMap);
        Integer totalCount = Integer.parseInt(String.valueOf(listCount.get("cnt")));
        if(members != null) {
            responseMap.put("members", members);
            responseMap.put("totalCount", totalCount);
        } else {
        	new ResourceAccessException("데이터가 없습니다.");
        }
        return responseMap;
    }

    public Map<String, Object> getMember(int memberSeq) throws Exception {
    	Map<String, Object> requestMap = new HashMap<>();
    	requestMap.put("memberSeq", memberSeq);
    	Map<String, Object> responseMap = dao.dbDetail("member.getMembers", requestMap);
    	List<Map<String, Object>> details = null;
    	
        if(responseMap != null) {
        	details = dao.dbDetails("member.getMemberDetails", requestMap);
        	responseMap.put("details", details);
        } else {
        	new ResourceAccessException("데이터가 없습니다.");
        }
             
        return responseMap;
    }

    public void addMember(Map<String, Object> requestMap) throws Exception {
    	int effectRow = this.dao.dbInsert("member.addMember", requestMap);
    	System.err.println(requestMap);
    	if(requestMap.get("details") != null) {
    		List<Map<String, Object>> details = (List<Map<String, Object>>) requestMap.get("details");
    		for(Map<String, Object> map : details) {
    			map.put("memberSeq",  requestMap.get("memberSeq"));
    			this.dao.dbInsert("member.addMemberDetail", map);
    		}
    	}
    	if(effectRow < 0) throw new BadRequestException("회원가입을 실패했습니다.");
    }
    
    public void setMember(Map<String, Object> requestMap) throws Exception {
    	int effectRow = this.dao.dbUpdate("member.setMember", requestMap);
    	if(requestMap.get("details") != null) {
    		int detailRow;
    		List<Map<String, Object>> details = (List<Map<String, Object>>) requestMap.get("details");
			detailRow = this.dao.dbDelete("member.removeMemberDetail", requestMap);
    		for(Map<String, Object> map : details) {
    			map.put("memberSeq",  requestMap.get("memberSeq"));
				detailRow = this.dao.dbInsert("member.addMemberDetail", map);
				if(detailRow < 0) throw new BadRequestException("상세정보 수정을 실패했습니다.");

    		}
    	}
    	if(effectRow < 0) throw new BadRequestException("정보수정을 실패했습니다.");
    }
    
    public void logicalRemoveMember(Map<String, Object> requestMap) throws Exception {
    	int effectRow = this.dao.dbUpdate("member.setMember", requestMap);
    	if(effectRow < 0) throw new BadRequestException("논리적 삭제를 실패했습니다.");
    }
    
    public void physicalRemoveMember(int memberSeq) throws Exception {
    	Map<String, Object> requestMap = new HashMap<>();
    	requestMap.put("memberSeq", memberSeq);
    	int detailRow = this.dao.dbDelete("member.removeMemberDetail", requestMap);
    	int effectRow = this.dao.dbDelete("member.removeMember", requestMap);
    	if(effectRow < 0) throw new BadRequestException("물리적 삭제를 실패했습니다.");
    }
}
