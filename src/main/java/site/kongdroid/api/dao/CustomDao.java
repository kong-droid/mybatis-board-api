package site.kongdroid.api.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import lombok.val;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
/**
 * @author KMH 
 * 2021.12.27
 * CustomDao를 통해 Map을 알맞은 mapper에 주입
 **/
@Repository
@RequiredArgsConstructor
public class CustomDao {
	
	private final SqlSession sqlSession;

	// list
	public List<Map<String, Object>> dbDetails(String mapper, Map<String, Object> map) {
		return sqlSession.selectList(mapper, map);
	}

	// count
	public int dbCount(String mapper, Map<String, Object> map) {
		return sqlSession.selectOne(mapper, map);
	}

	// detail
	public Map<String, Object> dbDetail(String mapper, Map<String, Object> map) {
		return sqlSession.selectOne(mapper, map);
	}
	
	// insert
	public int dbInsert(String mapper, Map<String, Object> map) {
		return sqlSession.insert(mapper, map);
	}
	
	// update
	public int dbUpdate(String mapper, Map<String, Object> map) {
		return sqlSession.update(mapper, map);
	}
		
	// delete
	public int dbDelete(String mapper, Map<String, Object> map) {
		return sqlSession.delete(mapper, map);
	}		
}
