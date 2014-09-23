<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="casestudy.dataaccess.*, casestudy.business.service.*, casestudy.business.domain.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>BoardDao 테스트</title>
</head>
<body>
<%
	BoardDao dao = new BoardDaoImpl();
    
    /**** Insert Board  ****/
    Board tempBoard = new Board("이지은", "첫 번째 테스트 글", "글 내용입니다.", "220.67.115.217");
    dao.insertBoard(tempBoard);
    tempBoard = new Board("이지은", "두 번째 테스트 글", "두 번째 글 내용입니다.", "220.67.115.220");
    dao.insertBoard(tempBoard);
    
    /**** Select Board  ****/
    Board selectedBoard = dao.selectBoard(1);
    
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
    tempBoard = new Board(1, "이지은", "첫 번째 테스트 글 (수정)", "수정된 글 내용입니다.", "220.67.115.218");
    dao.updateBoard(tempBoard);
    out.println("<h4>Update Board Test</h4>");
    out.println("<p>");    
    out.println(dao.selectBoard(1));
    out.println("</p>");    
    
    /**** Add Read Count  ****/
    dao.addReadCount(1);
    for (int count = 0; count < 5; count++) {
    	dao.addReadCount(2);
    }
    out.println("<h4>Add Read Count Test</h4>");
    out.println("<p>");
    out.println(dao.selectBoard(1) + "<br>");
    out.println(dao.selectBoard(2));
    out.println("</p>");
    
    /**** Select Board List ****/
    java.util.List<Board> boardList = dao.selectBoardList(new java.util.HashMap<String, Object>());
    out.println("<h4>Select Board List Test</h4>");
    out.println("<p>");
    for (Board board : boardList) {
        out.println(board + "<br>");
    }
    out.println("</p>");
    
    /**** Board Num Exists ****/       
    boolean result = dao.boardNumExists(2);
    out.println("<h4>Board Num Exists Test</h4>");
    out.println("<p>");
    out.println(result);
    out.println("</p>");
    
    /**** Delete Board  ****/
    dao.deleteBoard(2);
    result = dao.boardNumExists(2);
    out.println("<h4>Delete Board Test</h4>");
    out.println("<p>");
    out.println(result);
    out.println("</p>");
%>
</body>
</html>
