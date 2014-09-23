/**
 * ���ϸ� : BoardService.java
 * �ۼ��� : 2014. 6. 12.
 * ���ϼ��� : 
 */
package casestudy.business.service;

import java.util.Map;

import casestudy.business.domain.Board;

/**
 * �Խ��ǰ� ������ ������ ó���� ������ ���񽺸� ����� ��ü�� �԰��� ������ �������̽�.<br/> 
 * ����Ͻ� ���� ���� �������̽��� ǥ���Ǵ� Ư�� ������ Ư�� �μ� ó���� ������ ���� �� ���������� �����ȴ�.<br/>
 * ����Ͻ� ���� ���� ���ø����̼��� �߽��� �Ǹ�, ���ø����̼��� ��� �߰��� �����̶� �ַ� ����Ͻ� ���� ���� �����̴�.
 * ���� ������ ��Ű��ó�� ���� ���ø����̼��� ����� ���ؼ��� ����Ͻ� ���� ���� �� ����� ���� �߿��ϴ�.
 *  
 * @author �����(kidmania@hotmail.com)
 *
 */
public interface BoardService {
	
    /**
     * Ư�� ��ȣ�� �Խñ��� �д´�. ��ȸ�� �Խñ��� ��ȸ���� �����Ǿ�� �Ѵ�.
     *
     * @param num �а��� �ϴ� �Խñ��� ��ȣ
     * @return ��ȸ�� �Խñ� ������ ��� �ִ� Board ��ü
     * @throws DataNotFoundException ��ȣ�� �ش��ϴ� �Խñ��� �������� ���� ��� �߻�
     */
	public Board readBoard(int num) throws DataNotFoundException;
	
    /**
     * Ư�� ��ȣ�� �Խñ��� ������ ã�´�.
     *
     * @param num �˻��ϰ��� �ϴ� �Խñ��� ��ȣ
     * @return �˻��� �Խñ� ������ ��� �ִ� Board ��ü
     * @throws DataNotFoundException ��ȣ�� �ش��ϴ� �Խñ��� �������� ���� ��� �߻�
     */
	public Board findBoard(int num) throws DataNotFoundException;
	
    /**
     * ���ǿ� �´� ��� �Խù� ����� ��ȸ�Ѵ�.
     * 
     * @param searchInfo �˻��� ���� ���� ������ ��� �ִ� Map ��ü
     * @return �˻��� ��� �Խù� ������ ��� �ִ� Board �迭
     */
	public Board[] getBoardList(Map<String, Object> searchInfo);

	/**
	 * ���ǿ� �´� ��� �Խñ� ���� ��ȸ�Ѵ�.
	 * 
     * @param searchInfo �˻��� ���� ���� ������ ��� �ִ� Map ��ü 
	 * @return �˻��� ��� �Խñ��� ����
	 */
	public int getBoardCount(Map<String, Object> searchInfo);
	
    /**
     * ���ο� �Խñ��� ����Ѵ�.
     *
     * @param board ����� �Խñ� ������ ��� �ִ� Board ��ü
     */
	public void writeBoard(Board board);
	
    /**
     * ���� �Խñ��� �����Ѵ�.
     *
     * @param board ������ �Խñ� ������ ��� �ִ� Board ��ü
     * @throws DataNotFoundException �ش��ϴ� �Խñ��� �������� ���� ��� �߻� 
     */
	public void updateBoard(Board board) throws DataNotFoundException;

    /**
     * Ư�� ��ȣ�� �Խñ��� �����Ѵ�.
     *
     * @param num �����ϰ��� �ϴ� �Խñ��� ��ȣ
     * @throws DataNotFoundException ��ȣ�� �ش��ϴ� �Խñ��� �������� ���� ��� �߻�
     */
	public void removeBoard(int num) throws DataNotFoundException;

}
