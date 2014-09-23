/**
 * 파일명 : BoardServiceImpl.java
 * 작성일 : 2014. 6. 12.
 * 파일설명 : 
 */
package casestudy.business.service;

import java.util.Map;

import casestudy.business.domain.Board;

/**
 * 게시판 관련 비즈니스 로직을 구현한 서비스 클래스로 
 * 데이터 액세스 처리는 MemberDao 객체에게 위임하여 수행한다.
 * 
 * @author 고범석(kidmania@hotmail.com)
 *
 */
public class BoardServiceImpl implements BoardService {

    private BoardDao boardDataAccess;
    
    /* 
     * BoardDaoImpl 객체를 생성하여 boardDataAccess 인스턴스 변수 초기화  
     */
    public BoardServiceImpl() {
    	boardDataAccess = new casestudy.dataaccess.BoardDaoImpl();
    }	
	/* (non-Javadoc)
	 * @see casestudy.business.service.BoardService#getBoardList(Map)
	 * 
	 * BoardDao 객체를 사용해 조건에 맞는 모든 게시물 정보를 구해서 리턴한다.
	 */
	@Override
	public Board[] getBoardList(Map<String, Object> searchInfo) {
		return boardDataAccess.selectBoardList(searchInfo).toArray(new Board[0]);
	}	
	/* (non-Javadoc)
	 * @see casestudy.business.service.BoardService#getBoardCount(Map)
	 * 
	 * BoardDao 객체를 사용해 조건에 맞는 모든 게시글 개수를 구해서 리턴한다.
	 */
	@Override
	public int getBoardCount(Map<String, Object> searchInfo) {
		return boardDataAccess.selectBoardCount(searchInfo);
	}
	/* (non-Javadoc)
	 * @see casestudy.business.service.BoardService#readBoard(int)
	 * 
	 * BoardDao 객체를 사용해 해당 번호의 게시글의 조회수를 늘리고 게시글 정보를 찾아 리턴한다.
	 */
	@Override
	public Board readBoard(int num) throws DataNotFoundException {
		boardDataAccess.addReadCount(num);
		return findBoard(num);
	}
	/* (non-Javadoc)
	 * @see casestudy.business.service.BoardService#findBoard(int)
	 * 
	 * BoardDao 객체를 사용해 해당 번호의 게시글 정보를 구해서 리턴한다.
	 */
	@Override
	public Board findBoard(int num) throws DataNotFoundException {
		if (!boardDataAccess.boardNumExists(num)) {
			throw new DataNotFoundException("존재하지 않는 게시글입니다.(" + num + ")");
		}
		return boardDataAccess.selectBoard(num);
	}	
	/* (non-Javadoc)
	 * @see casestudy.business.service.BoardService#writeBoard(Board)
	 * 
	 * BoardDao 객체를 사용해 새로운 게시글 정보를 등록한다.
	 */
	@Override
	public void writeBoard(Board board) {
		boardDataAccess.insertBoard(board);
	}
	/* (non-Javadoc)
	 * @see casestudy.business.service.BoardService#updateBoard(Board)
	 * 
	 * BoardDao 객체를 사용해 게시글 정보를 수정한다.
	 */
	@Override
	public void updateBoard(Board board) throws DataNotFoundException {
		if (!boardDataAccess.boardNumExists(board.getNum())) {
			throw new DataNotFoundException("존재하지 않는 게시글입니다.(" + board.getNum() + ")");
		}
		boardDataAccess.updateBoard(board);		
	}
	/* (non-Javadoc)
	 * @see casestudy.business.service.BoardService#removeBoard(int)
	 * 
	 * BoardDao 객체를 사용해 해당 게시글을 삭제한다.
	 */
	@Override
	public void removeBoard(int num) throws DataNotFoundException {
		if (!boardDataAccess.boardNumExists(num)) {
			throw new DataNotFoundException("존재하지 않는 게시글입니다.(" + num + ")");
		}
		boardDataAccess.deleteBoard(num);
	}
}
