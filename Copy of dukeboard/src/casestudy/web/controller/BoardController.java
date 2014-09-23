package casestudy.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import casestudy.business.domain.Board;
import casestudy.business.service.BoardService;
import casestudy.business.service.BoardServiceImpl;
import casestudy.business.service.DataNotFoundException;
import casestudy.util.PageHandler;

/**
 * Servlet implementation class BoardController
 */
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /* 
     * HTTP GET�� POST ����� ��û�� ��� ó���Ѵ�.
     * ��û�Ķ���� ���� Ȯ���Ͽ� ������ ������� ��û�� ó���Ѵ�.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        try {
	        // ��û ��� ������ Ȯ���Ѵ�.
	        String action = request.getPathInfo();
	
	        // ��û ��� ���� ���� ������ �޼ҵ带 �����Ͽ� ȣ���Ѵ�.
	        if (action.equals("/list")) {
	        	selectBoardList(request, response);
	        } else if (action.equals("/read")) {
	        	readBoard(request, response);
	        } else if (action.equals("/writeForm")) {
	        	writeBoardForm(request, response);
	        } else if (action.equals("/write")) {
	        	writeBoard(request, response);
	        } else if (action.equals("/updateForm")) {
	            updateBoardForm(request, response);
	        } else if (action.equals("/update")) {
	            updateBoard(request, response);
	        } else if (action.equals("/remove")) {
	            removeBoard(request, response);
	        } else {
	        	response.sendError(HttpServletResponse.SC_NOT_FOUND);
	        }
        } catch (Exception ex) {
        	throw new ServletException(ex);
        }
    }

	/* 
     * ���ǿ� �´� ��� �Խù� ����� �����ִ� ��û�� ó���Ѵ�.
     */
	private void selectBoardList(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
		// 1.1. searchType, searchText ��û �Ķ���� ���� ���Ѵ�.
		String searchType = request.getParameter("searchType");
		String searchText = request.getParameter("searchText");
		// 1.2. pageNumber ��û �Ķ���� ���� ���Ѵ�.
		String pageNumber = request.getParameter("pageNumber");
		
		// ���� ������ ��ȣ
		int currentPageNumber = 1;
		if (pageNumber != null && pageNumber.length() !=0) {
			currentPageNumber = Integer.parseInt(pageNumber);
		}
	
		// 2. �˻� �ɼ��� ��� �ִ� Map ��ü�� �����Ͽ� searchType, searchText ���� �����Ѵ�.
		Map<String, Object> searchInfo = new HashMap<String, Object>();
		searchInfo.put("searchType", searchType);
		searchInfo.put("searchText", searchText);
		
		// BoardService ��ü�� �����Ѵ�.
		BoardService service = new BoardServiceImpl();
		
		// ��ü �Խñ� ���� (BoardService�κ��� ���Ѵ�.) 
		int totalBoardCount = service.getBoardCount(searchInfo);
		// ��ü ������ ����
		int totalPageCount = PageHandler.getTotalPageCount(totalBoardCount);

		// ������ ���� �ٿ� ǥ�õ� ���� ������ ��ȣ 
		int startPageNumber = PageHandler.getStartPageNumber(currentPageNumber);
		// ������ ���� �ٿ� ǥ�õ� �� ������ ��ȣ
		int endPageNumber = PageHandler.getEndPageNumber(currentPageNumber, totalBoardCount);
		
		// ���� �������� �Խñ� ��Ͽ��� ó�� ������ �Խñ��� �� ��ȣ
		int startRow = PageHandler.getStartRow(currentPageNumber);
		// ���� �������� �Խñ� ��Ͽ��� �������� ������ �Խñ��� �� ��ȣ
		int endRow = PageHandler.getEndRow(currentPageNumber);
		
		// 3.1. �˻��ɼ� Map(searchInfo)�� startRow�� endRow ���� �����Ѵ�.
		searchInfo.put("startRow", startRow);
		searchInfo.put("endRow", endRow);
		
		// 3.2. BoardService ��ü�κ��� ��� �Խñ� ����Ʈ�� ���ؿ´�.
		Board[] boardList = service.getBoardList(searchInfo);
		
        // 4.1 request scope �Ӽ�(boardList)�� �Խñ� ����Ʈ�� �����Ѵ�.
        request.setAttribute("boardList", boardList);
        // 4.2. request scope �Ӽ����� currentPageNumber, startPageNumber, 
        //	       endPageNumber, totalPageCount ���� �����Ѵ�.
        request.setAttribute("currentPageNumber", currentPageNumber);
        request.setAttribute("startPageNumber", startPageNumber);
        request.setAttribute("endPageNumber", endPageNumber);
        request.setAttribute("totalPageCount", totalPageCount);
        
        // 5. RequestDispatcher ��ü�� ���� �� ������(/WEB-INF/views/board/list.jsp)�� ��û�� �����Ѵ�.
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
        dispatcher.forward(request, response);
	}

	/* 
     * ���õ� �Խñ��� �о�ͼ� �����ִ� ��û�� ó���Ѵ�.
     */
	private void readBoard(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DataNotFoundException {
		// 1.1 ��û �Ķ����(num)�� ���� �� ��ȣ�� ���Ѵ�.
		String num = request.getParameter("num");
		// 1.2. pageNumber ��û �Ķ���� ���� ���Ѵ�.
		String pageNumber = request.getParameter("pageNumber");
		
		int currentPageNumber = 1;
		if (pageNumber != null && pageNumber.length() !=0) {
			currentPageNumber = Integer.parseInt(pageNumber);
		}
		
		// 2. BoardService ��ü�κ��� �ش� �� ��ȣ�� �Խñ��� ���ؿ´�.
		BoardService service = new BoardServiceImpl();
		Board board = service.readBoard(Integer.parseInt(num));
		
		// 3.1. request scope �Ӽ�(board)�� �Խñ��� �����Ѵ�.
        request.setAttribute("board", board);
        // 3.2. request scope �Ӽ����� currentPageNumber�� �����Ѵ�.       
        request.setAttribute("currentPageNumber", currentPageNumber);
        
        // 4. RequestDispatcher ��ü�� ���� �� ������(/WEB-INF/views/board/read.jsp)�� ��û�� �����Ѵ�.
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/board/read.jsp");
        dispatcher.forward(request, response);
	}
	
	/* 
     * �Խñ� ����� ���� ���� �����Ѵ�.
     */
	private void writeBoardForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DataNotFoundException {
        // RequestDispatcher ��ü�� ���� �� ������(/WEB-INF/views/board/writeForm.jsp)�� ��û�� �����Ѵ�.
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/board/writeForm.jsp");
        dispatcher.forward(request, response);
	}
	
	/* 
     * �Խñ��� ����ϴ� ��û�� ó���Ѵ�.
     */
	private void writeBoard(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DataNotFoundException {
		// 1. ��û �Ķ���ͷ� ���� �ۼ���(writer), ����(title), ����(contents)�� ���Ѵ�.
		String writer = request.getParameter("writer");
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		String ip = request.getRemoteAddr();
		
		// 2. ���� �� ��û �Ķ���� ���� ip ���� ���� Board ��ü�� �����Ѵ�.
		Board board = new Board(writer, title, contents, ip);
		
		// 3. BoardService ��ü�� ���� �ش� �Խñ��� ����Ѵ�.
		BoardService service = new BoardServiceImpl();
        service.writeBoard(board);

        // 4. RequestDispatcher ��ü�� ���� ��� ����(list)�� ��û�� �����Ѵ�.
        RequestDispatcher dispatcher = request.getRequestDispatcher("list");
        dispatcher.forward(request, response);	
	}

	/* 
     * �Խñ� ������ ���� ������ ������ ä���� ���� �����Ѵ�.
     */
	private void updateBoardForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DataNotFoundException {
		// ��û �Ķ���ͷ� ���� �� ��ȣ(num)�� ���Ѵ�.
		String num = request.getParameter("num");
		// pageNumber ��û �Ķ���� ���� ���Ѵ�.
		String pageNumber = request.getParameter("pageNumber");
		
		int currentPageNumber = 1;
		if (pageNumber != null && pageNumber.length() !=0) {
			currentPageNumber = Integer.parseInt(pageNumber);
		}
		
		// BoardService ��ü�� ���� �ش� ��ȣ�� �Խñ��� �˻��Ѵ�.
        BoardService boardService = new BoardServiceImpl();
        Board board = boardService.findBoard(Integer.parseInt(num));
        
        // request scope �Ӽ�(board)�� �˻��� �Խñ��� �����Ѵ�.
        request.setAttribute("board", board);
        // request scope �Ӽ����� currentPageNumber�� �����Ѵ�.        
        request.setAttribute("currentPageNumber", currentPageNumber);
        
        // RequestDispatcher ��ü�� ���� �� ������(/WEB-INF/views/board/updateForm.jsp)�� ��û�� �����Ѵ�.
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/board/updateForm.jsp");
        dispatcher.forward(request, response);
	}
	
	/* 
     * �Խñ��� �����ϴ� ��û�� ó���Ѵ�.
     */
	private void updateBoard(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DataNotFoundException {
		// 1. ��û �Ķ���ͷ� ���� �� ��ȣ(num), �ۼ���(writer), ����(title), ����(contents)�� ���Ѵ�.
		String num = request.getParameter("num");
		String writer = request.getParameter("writer");
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		String ip = request.getRemoteAddr();
		
		// 2. ���� �� ��û �Ķ���� ���� ip ���� ���� Board ��ü�� �����Ѵ�.
		Board board = new Board(Integer.parseInt(num), writer, title, contents, ip);
		
		// 3. BoardService ��ü�� ���� �ش� �Խñ��� �����Ѵ�.
        BoardService boardService = new BoardServiceImpl();
        boardService.updateBoard(board);    
        
		// 4. request scope �Ӽ�(board)�� �Խñ��� �����Ѵ�.
        request.setAttribute("board", board);        
        
        // 5. RequestDispatcher ��ü�� ���� �Խù� ����(read)�� ��û�� �����Ѵ�.
        RequestDispatcher dispatcher = request.getRequestDispatcher("read");
        dispatcher.forward(request, response);
	}

	/* 
     * �Խñ��� �����ϴ� ��û�� ó���Ѵ�.
     */
	private void removeBoard(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, DataNotFoundException {
		// 1. ��û �Ķ���ͷ� ���� �� ��ȣ(num)�� ���Ѵ�.
		String num = request.getParameter("num");
		// 2. BoardService ��ü�� ���� �ش� ��ȣ�� �Խñ��� �����Ѵ�.
        BoardService boardService = new BoardServiceImpl();
        boardService.removeBoard(Integer.parseInt(num));

        // 3. RequestDispatcher ��ü�� ���� ��� ����(list)�� ��û�� �����Ѵ�.
        RequestDispatcher dispatcher = request.getRequestDispatcher("list");
        dispatcher.forward(request, response);	
	}

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
