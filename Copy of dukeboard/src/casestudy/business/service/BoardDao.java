/**
 * ���ϸ� : BoardDao.java
 * �ۼ��� : 2014. 6. 12.
 * ���ϼ��� : 
 */
package casestudy.business.service;

import java.util.List;
import java.util.Map;

import casestudy.business.domain.Board;

/**
 * �Խ��� ���� ������ �׼��� ó���� ����� ��ü�� �԰��� ������ �������̽�.<br/> 
 * ������ �׼��� ���� �и������ν� ������ �׼��� �� �̿� ����̳� ������ ����Ǿ 
 * ����Ͻ� ���� ���� ������ ���� �ʴ´�.
 *  
 * @author �����(kidmania@hotmail.com)
 *
 */
public interface BoardDao {
	
	/**
	 * ���ǿ� �´� ��� �Խù� ����� ��ȸ�Ѵ�.
	 * 
     * @param searchInfo �˻��� ���� ���� ������ ��� �ִ� Map ��ü 
	 * @return �˻��� �Խù� ����� ��� �ִ� List ��ü
	 */
	public List<Board> selectBoardList(Map<String, Object> searchInfo);
	
	/**
	 * ���ǿ� �´� ��� �Խñ� ������ ��ȸ�Ѵ�.
	 * 
     * @param searchInfo �˻��� ���� ���� ������ ��� �ִ� Map ��ü 
	 * @return �˻��� ��� �Խñ��� ����
	 */
	public int selectBoardCount(Map<String, Object> searchInfo);
	
	/**
	 * ������ ��ȣ�� �ش��ϴ� �Խñ��� �˻��Ѵ�.
	 * @param num �˻��ϰ��� �ϴ� �Խñ��� ��ȣ
	 * @return
	 */
	public Board selectBoard(int num);
	
	/**
	 * ������ ��ȣ�� �ش��ϴ� �Խñ��� ��ȸ���� ������Ų��.
	 * @param num ��ȸ���� ������ų �Խñ��� ��ȣ
	 */
	public void addReadCount(int num);

    /**
     * �μ��� �־��� ��ȣ�� �ش��ϴ� �Խñ��� �ִ��� Ȯ���Ѵ�.
     * 
     * @param num ���翩�θ� Ȯ���Ϸ��� �Խñ��� ��ȣ
     * @return �ش��ϴ� �Խñ��� �����ϸ� true, �������� ������ false
     */
	public boolean boardNumExists(int num);

    /**
     * �μ��� �־��� Board ��ü�� ������ ���ο� �Խñ��� ����Ѵ�.
     * 
     * @param board ����� �Խñ� ������ ��� �ִ� Board ��ü
     */
	public void insertBoard(Board board);

    /**
     * �μ��� �־��� Board ��ü�� ������ ���� �Խñ��� �����Ѵ�.
     * 
     * @param board ������ �Խñ� ������ ��� �ִ� Board ��ü
     */
	public void updateBoard(Board board);
	
    /**
     * �μ��� �־��� ��ȣ�� �ش��ϴ� �Խñ��� �����Ѵ�.
     * 
     * @param num �����Ϸ��� �Խñ��� ��ȣ
     */
	public void deleteBoard(int num);
	
}
