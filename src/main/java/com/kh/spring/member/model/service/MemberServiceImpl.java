package com.kh.spring.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.dao.MemberDAO;
import com.kh.spring.member.model.vo.Member;

@Service ("mService")
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberDAO mDAO;
	
	// 멤버 로그인
	@Override
	public Member memberLogin(Member m) {
		
		
		return mDAO.selectmember(m);
	}

	// 회원 가입
	@Override
	public int insertMember(Member m) {
		return mDAO.inertMember(m);
	}

	// 정보수정
	@Override
	public int updateMember(Member m) {
		return mDAO.updateMember(m);
	}

	@Override
	public int updatePwd(Member m) {
		return mDAO.updatePwd(m);
	}

	@Override
	public int deletMember(String id) {
		return mDAO.deletMember(id);
	}

	@Override
	public int idCheck(String id) {
		return 0;
	}

	@Override
	public int checkIdDup(String id) {
		return mDAO.checkIdDup(id);
	}

	
	
	
	
}
