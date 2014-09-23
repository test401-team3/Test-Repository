/**
 * 파일명 : BoardDaoImpl.java
 * 작성일 : 2014. 6. 12.
 * 파일설명 : 
 */
package casestudy.dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import casestudy.business.domain.Board;
import casestudy.business.service.BoardDao;

/**
 * 게시판 관련 데이터 액세스 처리를 JDBC API를 활용하여 구현한 클래스로 
 * 데이터베이스 접속과 Board 테이블을 사용하는 SQL 문의 수행을 통해 관련 처리를 수행한다.
 * 
 * @author 고범석(kidmania@hotmail.com)
 *
 */
public class BoardDaoImpl implements BoardDao {
	private DataSource dataSource;
	
    /*
     * JNDI API를 이용하여 네이밍 서비스에 바인딩(jdbc/dukeshopDB)된 DataSource를 검색한다.
     */
    public BoardDaoImpl() {
        try {
            Context context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/dukeshopDB");
        } catch(NamingException ne) {
            System.err.println("A JNDI error occured.");
            ne.printStackTrace(System.err);
            throw new RuntimeException("A JNDI error occurred. " + ne.getMessage());            
        }    	
    }
	
    /*
     * DataSource의 getConnection() 메소드를 통해 Connection을 만든다.
     */
    private Connection obtainConnection() throws SQLException {
    	return dataSource.getConnection();
    }

	/* (non-Javadoc)
	 * @see casestudy.business.service.BoardDao#selectBoardCount(Map)
	 */
	@Override
	public int selectBoardCount(Map<String, Object> searchInfo) {
		// 1. searchInfo으로부터 검색 조건을 구한다.
		String searchType = (String) searchInfo.get("searchType");
		String searchText = (String) searchInfo.get("searchText");
		
		// 2. searchType 값에 따라 사용될 조건절(WHERE)을 생성한다.
		String whereSQL = "";
		
		if ((searchType == null) || (searchType.length() == 0)) {
			whereSQL = "";
		} else if (searchType.equals("all")) {
			whereSQL = "WHERE title LIKE ? OR writer LIKE ? OR contents LIKE ?";
		} else if (searchType.equals("title") || searchType.equals("writer") || searchType.equals("contents")) {
			whereSQL = "WHERE " + searchType + " LIKE ?";
		}
		
		// 3. LIKE 절에 포함될 수 있도록 searchText 값 앞뒤에 % 기호를 붙인다.
		if (searchText != null) {
			searchText = "%" + searchText.trim() + "%";
		}
		
		// 4. SELECT 문에 생성된 WHERE 절을 붙인다.
		String query = "SELECT count(num) FROM board " + whereSQL;
        System.out.println("BoardDaoImpl selectBoardCount() query: " + query);
        
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        int count = 0;
        
        try {
            connection = obtainConnection();
            stmt = connection.prepareStatement(query);
            
            // 5. searchType 값에 따라 PreparedStatement의 파라미터 값을 설정한다.
    		if ((searchType == null) || (searchType.length() == 0)) {

    		} else if (searchType.equals("all")) {
    			stmt.setString(1, searchText);
    			stmt.setString(2, searchText);
    			stmt.setString(3, searchText);
    			
    		} else if (searchType.equals("title") || searchType.equals("writer") || searchType.equals("contents")) {
    			stmt.setString(1, searchText);
    		}
            
            rs = stmt.executeQuery();

            if (rs.next()) {
            	count = rs.getInt(1);
            }
            
        } catch(SQLException se) {
            System.err.println("BoardDaoImpl selectBoardCount() Error :" + se.getMessage());
            se.printStackTrace(System.err);  
            throw new RuntimeException("A database error occurred. " + se.getMessage());

        } finally {
            try { if (rs != null) rs.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
            try { if (stmt != null) stmt.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
            try { if (connection != null) connection.close(); } catch(SQLException ex){ ex.printStackTrace(System.err); }
        }

        return count;
	}
	
	/* (non-Javadoc)
	 * @see casestudy.business.service.BoardDao#selectBoardList(Map)
	 */
	@Override
	public List<Board> selectBoardList(Map<String, Object> searchInfo) {
		// 1.1 searchInfo Map으로부터 검색 조건을 구한다.
		String searchType = (String) searchInfo.get("searchType");
		String searchText = (String) searchInfo.get("searchText");
		// 1.2. searchInfo Map으로부터 현재 페이지에 보여질 게시글의 행 번호(startRow, endRow) 값을 구한다.
		int startRow = (Integer) searchInfo.get("startRow");
		int endRow = (Integer) searchInfo.get("endRow");
		
		// 2. searchType 값에 따라 사용될 조건절(WHERE)을 생성한다.
		String whereSQL = "";
		
		if ((searchType == null) || (searchType.length() == 0)) {
			whereSQL = "";
		} else if (searchType.equals("all")) {
			whereSQL = "WHERE title LIKE ? OR writer LIKE ? OR contents LIKE ?";
		} else if (searchType.equals("title") || searchType.equals("writer") || searchType.equals("contents")) {
			whereSQL = "WHERE " + searchType + " LIKE ?";
		}
		
		// 3. LIKE 절에 포함될 수 있도록 searchText 값 앞뒤에 % 기호를 붙인다.
		if (searchText != null) {
			searchText = "%" + searchText.trim() + "%";
		}
		
		// 4. SELECT 문에 생성된 WHERE 절을 붙인다.
        String query = "SELECT * FROM " 
        					+ "(SELECT ROWNUM r, num, writer, title, read_count, reg_date FROM "  
        					+	"(SELECT num, writer, title, read_count, reg_date FROM board " 
        					+ whereSQL + " ORDER BY num DESC)) WHERE r BETWEEN ? and ?";
        							
        System.out.println("BoardDaoImpl selectBoardList() query: " + query);
        
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Board> boardList = new ArrayList<Board>();
        Board board = null;
        
        try {
            connection = obtainConnection();
            stmt = connection.prepareStatement(query);
            
            // 5. searchType 값에 따라 PreparedStatement의 파라미터(startRow, endRow 포함) 값을 설정한다.
    		if ((searchType == null) || (searchType.length() == 0)) {
    			stmt.setInt(1, startRow);
    			stmt.setInt(2, endRow);

    		} else if (searchType.equals("all")) {
    			stmt.setString(1, searchText);
    			stmt.setString(2, searchText);
    			stmt.setString(3, searchText);
    			stmt.setInt(4, startRow);
    			stmt.setInt(5, endRow);
    			
    		} else if (searchType.equals("title") || searchType.equals("writer") || searchType.equals("contents")) {
    			stmt.setString(1, searchText);
    			stmt.setInt(2, startRow);
    			stmt.setInt(3, endRow);
    		}
    		
            rs = stmt.executeQuery();
            
            while (rs.next()) {
            	String title = rs.getString("title");
            	if (title.length() > 28) {
            		title = new StringBuilder(title.substring(0, 28)).append("...").toString();
            	}
                board = new Board(rs.getInt("num"), 
    								rs.getString("writer"),
    								title,
    								rs.getInt("read_count"),
    								rs.getString("reg_date"));
                boardList.add(board);
            }
            
        } catch(SQLException se) {
            System.err.println("BoardDaoImpl selectBoardList() Error :" + se.getMessage());
            se.printStackTrace(System.err);  
            throw new RuntimeException("A database error occurred. " + se.getMessage());

        } finally {
            try { if (rs != null) rs.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
            try { if (stmt != null) stmt.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
            try { if (connection != null) connection.close(); } catch(SQLException ex){ ex.printStackTrace(System.err); }
        }

        return boardList;
	}

	/* (non-Javadoc)
	 * @see casestudy.business.service.BoardDao#selectboard(int)
	 */
	@Override
	public Board selectBoard(int num) {
        String query = "SELECT num, writer, title, contents, ip, read_count, reg_date, mod_date FROM board WHERE num = ?";
        System.out.println("BoardDaoImpl selectBoard() query: " + query);
        
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        Board board = null;
        
        try {
            connection = obtainConnection();
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, num);
            rs = stmt.executeQuery();

            if (rs.next()) {
            	board = new Board(rs.getInt("num"), 
    								rs.getString("writer"),
    								rs.getString("title"),
    								rs.getString("contents"),
    								rs.getString("ip"),
    								rs.getInt("read_count"),
    								rs.getString("reg_date"),
    								rs.getString("mod_date"));
            }
            
        } catch(SQLException se) {
            System.err.println("BoardDaoImpl selectBoard() Error :" + se.getMessage());
            se.printStackTrace(System.err);  
            throw new RuntimeException("A database error occurred. " + se.getMessage());

        } finally {
            try { if (rs != null) rs.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
            try { if (stmt != null) stmt.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
            try { if (connection != null) connection.close(); } catch(SQLException ex){ ex.printStackTrace(System.err); }
        }

        return board;
	}

	/* (non-Javadoc)
	 * @see casestudy.business.service.BoardDao#addReadCount(int)
	 */
	@Override
	public void addReadCount(int num) {
        String query = "UPDATE board SET read_count = read_count + 1 WHERE num = ?";
        System.out.println("BoardDaoImpl addReadCount() query: " + query);
        
        Connection connection = null;
        PreparedStatement stmt = null;
        
        try {
            connection = obtainConnection();
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, num);
            stmt.executeUpdate();
            
        } catch(SQLException se) {
            System.err.println("BoardDaoImpl addReadCount() Error :" + se.getMessage());
            se.printStackTrace(System.err);  
            throw new RuntimeException("A database error occurred. " + se.getMessage());

        } finally {
            try { if (stmt != null) stmt.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
            try { if (connection != null) connection.close(); } catch(SQLException ex){ ex.printStackTrace(System.err); }
        }
	}
	
	/* (non-Javadoc)
	 * @see casestudy.business.service.BoardDao#boardNumExists(int)
	 */
	@Override
	public boolean boardNumExists(int num) {
		boolean result = false;
		
        String query = "SELECT num FROM board WHERE num = ?";
        System.out.println("BoardDaoImpl boardNumExists() query: " + query);
        
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            connection = obtainConnection();
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, num);
            rs = stmt.executeQuery();
            result = rs.next();

        } catch(SQLException se) {
            System.err.println("BoardDaoImpl boardNumExists() Error :" + se.getMessage());
            se.printStackTrace(System.err);  
            throw new RuntimeException("A database error occurred. " + se.getMessage());

        } finally {
            try { if (rs != null) rs.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
            try { if (stmt != null) stmt.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
            try { if (connection != null) connection.close(); } catch(SQLException ex){ ex.printStackTrace(System.err); }
        }
        
        return result;
	}

	/* (non-Javadoc)
	 * @see casestudy.business.service.BoardDao#insertBoard(Board)
	 */
	@Override
	public void insertBoard(Board board) {
        String query = "INSERT INTO board (num, writer, title, contents, ip, read_count, reg_date, mod_date) VALUES (board_num_seq.NEXTVAL, ?, ?, ?, ?, 0, SYSDATE, SYSDATE)";
        System.out.println("BoardDaoImpl insertBoard() query: " + query);
        
        Connection connection = null;
        PreparedStatement stmt = null;
        
        try {
            connection = obtainConnection();
            stmt = connection.prepareStatement(query);
            stmt.setString(1, board.getWriter());
            stmt.setString(2, board.getTitle());
            stmt.setString(3, board.getContents());
            stmt.setString(4, board.getIp());
            stmt.executeUpdate();

        } catch(SQLException se) {
            System.err.println("BoardDaoImpl insertBoard() Error :" + se.getMessage());
            se.printStackTrace(System.err);  
            throw new RuntimeException("A database error occurred. " + se.getMessage());

        } finally {
            try { if (stmt != null) stmt.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
            try { if (connection != null) connection.close(); } catch(SQLException ex){ ex.printStackTrace(System.err); }
        }
	}

	/* (non-Javadoc)
	 * @see casestudy.business.service.BoardDao#updateBoard(Board)
	 */
	@Override
	public void updateBoard(Board board) {
        String query = "UPDATE board SET  writer = ?, title = ?, contents = ?, ip = ?, mod_date = SYSDATE WHERE num = ?";
        System.out.println("BoardDaoImpl updateBoard() query: " + query);
        
        Connection connection = null;
        PreparedStatement stmt = null;
        
        try {
            connection = obtainConnection();
            stmt = connection.prepareStatement(query);
            stmt.setString(1, board.getWriter());
            stmt.setString(2, board.getTitle());
            stmt.setString(3, board.getContents());
            stmt.setString(4, board.getIp());
            stmt.setInt(5, board.getNum());
            stmt.executeUpdate();

        } catch(SQLException se) {
            System.err.println("BoardDaoImpl updateBoard() Error :" + se.getMessage());
            se.printStackTrace(System.err);  
            throw new RuntimeException("A database error occurred. " + se.getMessage());

        } finally {
            try { if (stmt != null) stmt.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
            try { if (connection != null) connection.close(); } catch(SQLException ex){ ex.printStackTrace(System.err); }
        }
	}

	/* (non-Javadoc)
	 * @see casestudy.business.service.BoardDao#deleteBoard(int)
	 */
	@Override
	public void deleteBoard(int num) {
        String query = "DELETE FROM board WHERE num = ?";
        System.out.println("BoardDaoImpl deleteBoard() query: " + query);
        
        Connection connection = null;
        PreparedStatement stmt = null;
        
        try {
            connection = obtainConnection();
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, num);
            stmt.executeUpdate();

        } catch(SQLException se) {
            System.err.println("BoardDaoImpl deleteBoard() Error :" + se.getMessage());
            se.printStackTrace(System.err);  
            throw new RuntimeException("A database error occurred. " + se.getMessage());

        } finally {
            try { if (stmt != null) stmt.close(); } catch(SQLException ex) { ex.printStackTrace(System.err); }
            try { if (connection != null) connection.close(); } catch(SQLException ex){ ex.printStackTrace(System.err); }
        }
	}
}
