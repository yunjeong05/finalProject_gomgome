package com.ezen.gomgome.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ezen.gomgome.common.CamelHashMap;
import com.ezen.gomgome.entity.Community;

@Transactional
public interface CommunityRepository extends JpaRepository<Community, Integer>{
	
	@Query(value="SELECT * FROM t_gomg_community WHERE COMMUNITY_TYPE=1;", nativeQuery=true)
	List<Community> getPrList();
	
	@Query(value="SELECT * FROM t_gomg_community WHERE COMMUNITY_TYPE=2;", nativeQuery=true)
	List<Community> getHowtoList();
	
	@Query(value="SELECT * FROM t_gomg_community WHERE COMMUNITY_TYPE=3;", nativeQuery=true)
	List<Community> getBulletinList();
	
	@Query(value="SELECT * "
			+ "	FROM T_gomg_COMMUNITY"
			+ " WHERE COMMUNITY_TYPE = :communityType", 
			nativeQuery=true)
	Page<Community> findAllByCommunityType(@Param("communityType") int i, Pageable pageable);
	
	@Modifying
	@Query(value="UPDATE T_GOMG_COMMUNITY"
			+ "	SET COMMUNITY_TITLE = :#{#community.communityTitle},"
			+ "	    COMMUNITY_CONTENT = :#{#community.communityContent}"
			+ "	WHERE COMMUNITY_NO = :#{#community.communityNo}", nativeQuery=true)
	void communityModify(@Param("community") Community community);
	
	
	
	@Query(value="SELECT *\r\n"
			+ "	FROM (\r\n"
			+ "			SELECT A.*\r\n"
			+ "				 , B.USER_NICKNAME\r\n"
			+ "				FROM T_GOMG_COMMUNITY A\r\n"
			+ "				   , T_GOMG_USER B\r\n"
			+ "				WHERE A.USER_ID = B.USER_ID\r\n"
			+ "		 ) C\r\n"
			+ "	WHERE 1 = 0    \r\n"
			+ "      OR C.COMMUNITY_CONTENT LIKE CONCAT('%', :searchKeyword, '%')\r\n"
			+ "      OR C.COMMUNITY_TITLE LIKE CONCAT('%', :searchKeyword, '%')", nativeQuery = true)
	List<CamelHashMap> getSearchCommunityList(@Param("searchKeyword") String searchKeyword);
		
}
	
	
	
	
	


