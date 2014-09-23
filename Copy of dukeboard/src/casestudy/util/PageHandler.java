package casestudy.util;

public class PageHandler {
	// �� �������� ������ �Խñ� ���� 
	public static final int PAGE_LIST_SIZE = 5;
	// ������ ���� �ٿ� ������ ������ ���� 
	public static final int PAGE_GROUP_SIZE = 3;	
	
	// ��ü �Խñ� �����κ��� ��ü ������ ������ �����ִ� ���
	public static int getTotalPageCount(int totalBoardCount) {
		int totalPageCount = totalBoardCount / PAGE_LIST_SIZE;
		if (totalBoardCount % PAGE_LIST_SIZE != 0) {
			totalPageCount++;
		}
		return totalPageCount;
		//return (int) Math.ceil(totalBoardCount / (float) PAGE_LIST_SIZE);
	}
	
	// Ư�� �������� ������ ���� �ٿ� ǥ�õ� ���� ������ ��ȣ�� �����ִ� ���
	public static int getStartPageNumber(int pageNumber) {
		return (pageNumber - 1) / PAGE_GROUP_SIZE * PAGE_GROUP_SIZE + 1;
	}
	
	// Ư�� �������� ������ ���� �ٿ� ǥ�õ� �� ������ ��ȣ�� �����ִ� ���
	public static int getEndPageNumber(int pageNumber, int totalBoardCount) {
		int totalPageCount = getTotalPageCount(totalBoardCount);
		int startPageNumber = getStartPageNumber(pageNumber);
		
		int endPageNumber = startPageNumber + (PAGE_GROUP_SIZE - 1);
		if (endPageNumber > totalPageCount) {
			endPageNumber = totalPageCount;
		}
		
		return endPageNumber;		
	}
	
	// Ư�� �������� �Խñ� ��Ͽ��� ó�� ������ �Խñ��� �� ��ȣ�� �����ִ� ���
	public static int getStartRow(int pageNumber) {
		return (pageNumber - 1) * PAGE_LIST_SIZE + 1;
	}
	// Ư�� �������� �Խñ� ��Ͽ��� �������� ������ �Խñ��� �� ��ȣ�� �����ִ� ���
	public static int getEndRow(int pageNumber) {
		return pageNumber * PAGE_LIST_SIZE;
	}
}
