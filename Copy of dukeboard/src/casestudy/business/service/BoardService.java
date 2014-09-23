/**
 * 파일명 : BoardService.java
 * 작성일 : 2014. 6. 12.
 * 파일설명 : 
 */
package casestudy.business.service;

import java.util.Map;

import casestudy.business.domain.Board;

/**
 * 게시판과 관련한 업무나 처리에 관련한 서비스를 담당할 객체의 규격을 정의한 인터페이스.<br/> 
 * 비즈니스 로직 층은 유스케이스로 표현되는 특정 업무나 특정 부서 처리의 통합인 서비스 및 도메인으로 구성된다.<br/>
 * 비즈니스 로직 층은 어플리케이션의 중심이 되며, 어플리케이션의 기능 추가와 변경이란 주로 비즈니스 로직 층의 변경이다.
 * 따라서 유연한 아키텍처를 가진 어플리케이션을 만들기 위해서는 비즈니스 로직 층을 잘 만드는 것이 중요하다.
 *  
 * @author 고범석(kidmania@hotmail.com)
 *
 */
public interface BoardService {
	
    /**
     * 특정 번호의 게시글을 읽는다. 조회된 게시글의 조회수는 증가되어야 한다.
     *
     * @param num 읽고자 하는 게시글의 번호
     * @return 조회된 게시글 정보를 담고 있는 Board 객체
     * @throws DataNotFoundException 번호에 해당하는 게시글이 존재하지 않을 경우 발생
     */
	public Board readBoard(int num) throws DataNotFoundException;
	
    /**
     * 특정 번호의 게시글의 내용을 찾는다.
     *
     * @param num 검색하고자 하는 게시글의 번호
     * @return 검색된 게시글 정보를 담고 있는 Board 객체
     * @throws DataNotFoundException 번호에 해당하는 게시글이 존재하지 않을 경우 발생
     */
	public Board findBoard(int num) throws DataNotFoundException;
	
    /**
     * 조건에 맞는 모든 게시물 목록을 조회한다.
     * 
     * @param searchInfo 검색에 사용될 조건 정보를 담고 있는 Map 객체
     * @return 검색된 모든 게시물 정보를 담고 있는 Board 배열
     */
	public Board[] getBoardList(Map<String, Object> searchInfo);

	/**
	 * 조건에 맞는 모든 게시글 수를 조회한다.
	 * 
     * @param searchInfo 검색에 사용될 조건 정보를 담고 있는 Map 객체 
	 * @return 검색된 모든 게시글의 개수
	 */
	public int getBoardCount(Map<String, Object> searchInfo);
	
    /**
     * 새로운 게시글을 등록한다.
     *
     * @param board 등록할 게시글 정보를 담고 있는 Board 객체
     */
	public void writeBoard(Board board);
	
    /**
     * 기존 게시글을 수정한다.
     *
     * @param board 수정할 게시글 정보를 담고 있는 Board 객체
     * @throws DataNotFoundException 해당하는 게시글이 존재하지 않을 경우 발생 
     */
	public void updateBoard(Board board) throws DataNotFoundException;

    /**
     * 특정 번호의 게시글을 삭제한다.
     *
     * @param num 삭제하고자 하는 게시글의 번호
     * @throws DataNotFoundException 번호에 해당하는 게시글이 존재하지 않을 경우 발생
     */
	public void removeBoard(int num) throws DataNotFoundException;

}
