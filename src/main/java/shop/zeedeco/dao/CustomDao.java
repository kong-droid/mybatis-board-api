package shop.zeedeco.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
/**
 * @author KMH 
 * 2021.12.27
 * CustomDao를 통해 Map을 알맞은 mapper에 주입
 **/
@Repository
public class CustomDao {
	
	@Autowired
	SqlSession sqlSession;

	// 목록
	public List<Map<String, Object>> dbDetails(String mapper, Map<String, Object> map) {
		return sqlSession.selectList(mapper, map);
	}
	
	// 상세보기
	public Map<String, Object> dbDetail(String mapper, Map<String, Object> map) {
		return sqlSession.selectOne(mapper, map);
	}
	
	// 입력
	public int dbInsert(String mapper, Map<String, Object> map) {
		return sqlSession.insert(mapper, map);
	}
	
	// 수정
	public int dbUpdate(String mapper, Map<String, Object> map) {
		return sqlSession.update(mapper, map);
	}
		
	// 삭제
	public int dbDelete(String mapper, Map<String, Object> map) {
		return sqlSession.delete(mapper, map);
	}		
}
