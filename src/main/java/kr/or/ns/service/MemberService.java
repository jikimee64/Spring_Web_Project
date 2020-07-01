package kr.or.ns.service;

import java.sql.SQLException;

import org.springframework.stereotype.Service;

import kr.or.ns.vo.Skill;
import kr.or.ns.vo.Users;

@Service
public interface MemberService {

	public int joininsert(Users users) throws Exception, SQLException;
	
}
