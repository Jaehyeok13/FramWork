package com.kh.spring.member.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.spring.member.model.vo.Member;

@Repository("mDAO")
public class MemberDAO {

	// 원래 템플릿 만들어야 하는데 스프링에서는 DB 연결 하던가 미리 프리 어저구를 미리 지정만 해놓으면 이름만 불러오면 사용이 가능하다. 일단
	@Autowired
	private SqlSessionTemplate sqlSession; // root-context.xml 에서 작성한 bean 으로 생성
	
	public Member selectmember(Member m) {
		return (Member)sqlSession.selectOne("memberMapper.selectOne",m);
	}

	
	
}
