/**
 * ���ϸ� : BoardServiceImpl.java
 * �ۼ��� : 2014. 6. 12.
 * ���ϼ��� : 
 */
package casestudy.business.service;

import java.util.Map;

import casestudy.business.domain.Board;

/**
 * �Խ��� ���� ����Ͻ� ������ ������ ���� Ŭ������ 
 * ������ �׼��� ó���� MemberDao ��ü���� �����Ͽ� �����Ѵ�.
 * 
 * @author �����(kidmania@hotmail.com)
 *
 */
public class BoardServiceImpl implements BoardService {

    private BoardDao boardDataAccess;
    
    /* 
     * BoardDaoImpl ��ü�� �����Ͽ� boardDataAccess �ν��Ͻ� ���� �ʱ�ȭ  
     */
    public BoardServiceImpl() {
    	boardDataAccess = new casestudy.dataaccess.BoardDaoImpl();
    }	
	/* (non-Javadoc)
	 * @see casestudy.business.service.BoardService#getBoardList(Map)
	 * 
	 * BoardDao ��ü�� ����� ���ǿ� �´� ��� �Խù� ������ ���ؼ� �����Ѵ�.
	 */
	@Override
	public Board[] getBoardList(Map<String, Object> searchInfo) {
		return boardDataAccess.selectBoardList(searchInfo).toArray(new Board[0]);
	}	
	/* (non-Javadoc)
	 * @see casestudy.business.service.BoardService#getBoardCount(Map)
	 * 
	 * BoardDao ��ü�� ����� ���ǿ� �´� ��� �Խñ� ������ ���ؼ� �����Ѵ�.
	 */
	@Override
	public int getBoardCount(Map<String, Object> searchInfo) {
		return boardDataAccess.selectBoardCount(searchInfo);
	}
	/* (non-Javadoc)
	 * @see casestudy.business.service.BoardService#readBoard(int)
	 * 
	 * BoardDao ��ü�� ����� �ش� ��ȣ�� �Խñ��� ��ȸ���� �ø��� �Խñ� ������ ã�� �����Ѵ�.
	 */
	@Override
	public Board readBoard(int num) throws DataNotFoundException {
		boardDataAccess.addReadCount(num);
		return findBoard(num);
	}
	/* (non-Javadoc)
	 * @see casestudy.business.service.BoardService#findBoard(int)
	 * 
	 * BoardDao ��ü�� ����� �ش� ��ȣ�� �Խñ� ������ ���ؼ� �����Ѵ�.
	 */
	@Override
	public Board findBoard(int num) throws DataNotFoundException {
		if (!boardDataAccess.boardNumExists(num)) {
			throw new DataNotFoundException("�������� �ʴ� �Խñ��Դϴ�.(" + num + ")");
		}
		return boardDataAccess.selectBoard(num);
	}	
	/* (non-Javadoc)
	 * @see casestudy.business.service.BoardService#writeBoard(Board)
	 * 
	 * BoardDao ��ü�� ����� ���ο� �Խñ� ������ ����Ѵ�.
	 */
	@Override
	public void writeBoard(Board board) {
		boardDataAccess.insertBoard(board);
	}
	/* (non-Javadoc)
	 * @see casestudy.business.service.BoardService#updateBoard(Board)
	 * 
	 * BoardDao ��ü�� ����� �Խñ� ������ �����Ѵ�.
	 */
	@Override
	public void updateBoard(Board board) throws DataNotFoundException {
		if (!boardDataAccess.boardNumExists(board.getNum())) {
			throw new DataNotFoundException("�������� �ʴ� �Խñ��Դϴ�.(" + board.getNum() + ")");
		}
		boardDataAccess.updateBoard(board);		
	}
	/* (non-Javadoc)
	 * @see casestudy.business.service.BoardService#removeBoard(int)
	 * 
	 * BoardDao ��ü�� ����� �ش� �Խñ��� �����Ѵ�.
	 */
	@Override
	public void removeBoard(int num) throws DataNotFoundException {
		if (!boardDataAccess.boardNumExists(num)) {
			throw new DataNotFoundException("�������� �ʴ� �Խñ��Դϴ�.(" + num + ")");
		}
		boardDataAccess.deleteBoard(num);
	}
}
