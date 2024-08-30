package edu.kh.jdbc.service;

import java.util.List;

import edu.kh.jdbc.dto.User;

public interface UserService {

	/** 사용자 등록
	 * 
	 * @param user
	 * 
	 * @return result : 1(INSERT 성공) || 0(INSERT 실패)
	 * 
	 * @throws Exception
	 */
	int insertUser(User user) throws Exception;

	/** 아이디 중복 여부 확인
	 * 
	 * @param userId
	 * 
	 * @return result : 1(중복 O) || 0(중복 X)
	 * 
	 * @throws Exception
	 */
	int idCheck(String userId) throws Exception;

	/** 로그인
	 * 
	 * @param userId
	 * @param userPw
	 * 
	 * @return loginUser
	 * 
	 * @throws Exception
	 */
	User login(String userId, String userPw) throws Exception;

	/** 사용자 목록 조회
	 * 
	 * @return userList
	 * 
	 * @throws Exception
	 */
	List<User> selectAll() throws Exception;

	/** 검색어가 아이디에 포함된 사용자 조회
	 * 
	 * @param searchId
	 * 
	 * @return userList
	 * 
	 * @throws Exception
	 */
	List<User> search(String searchId) throws Exception;

	/** 아이디 클릭 시 사용자 상세 조회
	 * 
	 * @param userNo
	 * 
	 * @return user
	 * 
	 * @throws Exception
	 */
	User selectUser(int userNo) throws Exception;

	/** 삭제 버튼 클릭 시 사용자 삭제
	 * 
	 * @param userNo
	 * 
	 * @return result
	 * 
	 * @throws Exception
	 */
	int deleteUser(int userNo) throws Exception;

	/** 수정 버튼 클릭 시 비밀번호, 이름 수정
	 * 
	 * @param user
	 * 
	 * @return result
	 * 
	 * @throws Exception
	 */
	int updateUser(User user) throws Exception;

}
