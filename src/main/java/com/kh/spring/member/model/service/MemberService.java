package com.kh.spring.member.model.service;

import com.kh.spring.member.model.vo.Member;

public interface MemberService {

	Member memberLogin(Member m);

	int insertMember(Member m);

	int updateMember(Member m);

	int updatePwd(Member m);

	int deletMember(String id);

	int idCheck(String id);

	int checkIdDup(String id);

}
