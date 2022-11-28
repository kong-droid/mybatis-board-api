package shop.api.rest.dao;

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
 * CustomDao�� ���� Map�� �˸��� mapper�� ����
 **/
@Repository
public class CustomDao {
	
	@Autowired
	SqlSession sqlSession;

	// ���
	public List<Map<String, Object>> dbDetails(String mapper, Map<String, Object> map) {
		return sqlSession.selectList(mapper, map);
	}
	
	// �󼼺���
	public Map<String, Object> dbDetail(String mapper, Map<String, Object> map) {
		return sqlSession.selectOne(mapper, map);
	}
	
	// �Է�
	public int dbInsert(String mapper, Map<String, Object> map) {
		return sqlSession.insert(mapper, map);
	}
	
	// ����
	public int dbUpdate(String mapper, Map<String, Object> map) {
		return sqlSession.update(mapper, map);
	}
		
	// ����
	public int dbDelete(String mapper, Map<String, Object> map) {
		return sqlSession.delete(mapper, map);
	}		
}
