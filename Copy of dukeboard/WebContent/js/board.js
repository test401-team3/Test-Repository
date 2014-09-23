// 해당 url로 이동하는 기능
function goUrl(url) {
	location.href = url;
}

// 삭제 여부를 확인 후 해당 url로 이동하는 기능
function deleteCheck(url) {
	if (confirm('정말로 삭제하시겠습니까?')) { 
		goUrl(url);
	}
}

// 검색 폼의 공백을 체크하는 기능
function searchCheck() {
	var searchInput = document.searchForm.searchText;
	
	if (searchInput.value == '') {
		alert('검색어를 입력하세요');
		searchInput.focus();
		return false;		
	}
	return true;
}

// 작성/수정 폼의 공백을 체크하는 기능
function boardWriteCheck(form) {
	if (form.title.value.length == 0) {
		alert('제목을 입력하세요');
		form.title.focus();
		return;
	}	
	if (form.writer.value.length == 0) {
		alert('이름을 입력하세요');
		form.writer.focus();
		return;
	}
	if (form.contents.value.length == 0) {
		alert('내용을 입력하세요');
		form.contents.focus();
		return;
	}	
	form.submit();	
}

// 폼 필드가 비어있는지 여부를 체크하여 에러메시지를 출력
function checkNotEmpty(inputField, errorSpan) {
	if (inputField.value.length == 0) {
		errorSpan.innerHTML = '글쓴이 이름을 입력하세요.';
	} else {
		errorSpan.innerHTML = '';
	}
}














