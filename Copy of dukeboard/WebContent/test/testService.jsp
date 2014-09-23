<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="casestudy.business.service.*, casestudy.business.domain.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>BoardService 테스트</title>
</head>
<body>
<%
	BoardService service = new BoardServiceImpl();

	 /**** Write Board  ****/
	Board tempBoard = new Board("이지은", "세 번째 테스트 글", "세 번째 글 내용입니다.", "220.67.115.217");
	service.writeBoard(tempBoard);
	tempBoard = new Board("이지은", "네 번째 테스트 글", "네 번째 글 내용입니다.", "220.67.115.220");
	service.writeBoard(tempBoard);    
    
    /**** Find Board  ****/
    Board selectedBoard = service.findBoard(3);
    out.println("<h4>Select Board Test</h4>");
    out.println("<p>");
    out.println("num: " + selectedBoard.getNum() + "<br>");
    out.println("writer: " + selectedBoard.getWriter() + "<br>");
    out.println("title: " + selectedBoard.getTitle() + "<br>");
    out.println("contents: " + selectedBoard.getContents() + "<br>");
    out.println("ip: " + selectedBoard.getIp() + "<br>");
    out.println("readCount: " + selectedBoard.getReadCount() + "<br>");
    out.println("regDate: " + selectedBoard.getRegDate() + "<br>");
    out.println("modDate: " + selectedBoard.getModDate() + "<br>");
    out.println("</p>");

    /**** Update Board  ****/
    tempBoard = new Board(3, "이지은", "세 번째 테스트 글 (수정)", "수정된 글 내용입니다.", "220.67.115.218");
    service.updateBoard(tempBoard);
    out.println("<h4>Update Board Test</h4>");
    out.println("<p>");    
    out.println(service.findBoard(3));
    out.println("</p>");    
    
    /**** Read Board  ****/
    for (int count = 0; count < 5; count++) {
    	service.readBoard(3);
    }
    out.println("<h4>Read Board Test</h4>");
    out.println("<p>");
    out.println(service.readBoard(3));
    out.println("</p>");
    
    /**** Select Board List ****/
    Board[] boardList = service.getBoardList(new java.util.HashMap<String, Object>());
    out.println("<h4>Select Board List Test</h4>");
    out.println("<p>");
    for (Board board : boardList) {
        out.println(board + "<br>");
    }
    out.println("</p>");
    
    /**** Remove Board  ****/
    service.removeBoard(6);
    boardList = service.getBoardList(new java.util.HashMap<String, Object>());
    out.println("<h4>Remove Board Test</h4>");
    out.println("<p>");
    for (Board board : boardList) {
        out.println(board + "<br>");
    }
    out.println("</p>");

%>
</body>
</html>
