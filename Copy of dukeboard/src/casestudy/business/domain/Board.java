package casestudy.business.domain;

public class Board {
	/** �� ��ȣ */
	private int num;
	/** �ۼ��� */
	private String writer;
	/** ���� */
	private String title;
	/** ���� */
	private String contents;
	/** ������ */
	private String ip;
	/** ��ȸ�� */
	private int readCount;
	/** ��� �Ͻ� */
	private String regDate;
	/** ���� �Ͻ� */
	private String modDate;
	
	// �Խñ� �� ��ȸ��
	public Board(int num, String writer, String title, String contents,
			String ip, int readCount, String regDate, String modDate) {
		super();
		this.num = num;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.ip = ip;
		this.readCount = readCount;
		this.regDate = regDate;
		this.modDate = modDate;
	}

	// ����Ʈ ��ȸ��
	public Board(int num, String writer, String title, int readCount, String regDate) {
		super();
		this.num = num;
		this.writer = writer;
		this.title = title;
		this.readCount = readCount;
		this.regDate = regDate;
	}

	// �Խñ� �����
	public Board(String writer, String title, String contents, String ip) {
		super();
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.ip = ip;
	}

	// �Խñ� ������
	public Board(int num, String writer, String title, String contents, String ip) {
		super();
		this.num = num;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.ip = ip;
	}

	
	public int getNum() {
		return num;
	}
	

	public String getWriter() {
		return writer;
	}
	

	public String getTitle() {
		return title;
	}
	

	public String getContents() {
		return contents;
	}
	

	public String getIp() {
		return ip;
	}
	

	public int getReadCount() {
		return readCount;
	}
	

	public String getRegDate() {	 // "2014-09-18" ����
		return regDate.substring(0, 10);
	}
	
	public String getRegDateTime() {  // "2014-09-18 14:14:30" ����
		return regDate.substring(0, 19);
	}
	

	public String getModDate() {  // "2014-09-18" ����
		return modDate.substring(0, 10);
	}
	
	public String getModDateTime() {  // "2014-09-18 14:14:30" ����
		return modDate.substring(0, 19);
	}
	

	public void setNum(int num) {
		this.num = num;
	}
	

	public void setWriter(String writer) {
		this.writer = writer;
	}
	

	public void setTitle(String title) {
		this.title = title;
	}
	

	public void setContents(String contents) {
		this.contents = contents;
	}
	

	public void setIp(String ip) {
		this.ip = ip;
	}
	

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	

	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	@Override
	public String toString() {
		return "Board [num=" + num + ", writer=" + writer + ", title=" + title
				+ ", contents=" + contents + ", ip=" + ip + ", readCount="
				+ readCount + ", regDate=" + regDate + ", modDate=" + modDate
				+ "]";
	}
}
