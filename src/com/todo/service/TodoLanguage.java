package com.todo.service;

public class TodoLanguage {
	
	public static String msg(String lang, String caseTxt) {
		boolean isKo = (lang.equals("ko"));
		if (!isKo && !lang.equals("en")) return null;
		
		// In TodoMain.java
		if (caseTxt == "WrongInput") return isKo ? "명령어를 찾을 수 없습니다 (도움말: 'help')" : "command not found (Enter 'help')";
		if (caseTxt == "SelectLang") return isKo ? "사용할 언어를 입력하세요 (한국어: 'ko', 영어: 'en')" : "Enter the language to use. (Korean: 'ko', English: 'en')";
		if (caseTxt == "NotChangeLang") return isKo ? "언어가 이미 '한국어'로 설정되어 있습니다" : "Language already set to 'English'";
		if (caseTxt == "ChangeLang") return isKo ? "언어가 '한국어'로 변경되었습니다" : "Language changed to 'English'";
		if (caseTxt == "Command") return isKo ? "명령어" : "Command";
		if (caseTxt == "Title") return isKo ? "제목" : "Title";
		if (caseTxt == "Desc") return isKo ? "설명" : "Desc";
		if (caseTxt == "Standard") return isKo ? "기준 (이름: 'name', 날짜: 'date')" : "Std (name: 'name', date: 'date')";
		if (caseTxt == "Direction") return isKo ? "차순 (오름차순: 'asc', 내림차순: 'desc')" : "Dir (ascending: 'asc', descending: 'desc')";
		if (caseTxt == "Terminate") return isKo ? "프로그램 종료 중 입니다..." : "Terminates program...";
		if (caseTxt == "SaveItem") return isKo ? "파일이 저장되었습니다" : "File saved";
		
		// In Menu.java
		if (caseTxt == "langDesc") return isKo ? "언어 설정" : "set language";
		if (caseTxt == "addDesc") return isKo ? "아이템 추가" : "add item";
		if (caseTxt == "delDesc") return isKo ? "아이템 삭제" : "delete item";
		if (caseTxt == "editDesc") return isKo ? "아이템 수정" : "update item";
		if (caseTxt == "lsDesc") return isKo ? "리스트 출력" : "list items";
		if (caseTxt == "sortDesc") return isKo ? "아이템 정렬" : "sort item";
		if (caseTxt == "helpDesc") return isKo ? "도움말 (메뉴 재출력)" : "show menu";
		if (caseTxt == "exitDesc") return isKo ? "프로그램 종료" : "terminate program";
		
		// In TodoUtil.java
		if (caseTxt == "Duplicate") return isKo ? "중복되는 제목이 존재합니다" : "Title cannot be duplicated";
		if (caseTxt == "Added") return isKo ? "(이)가 추가되었습니다" : "is successfully added";
		if (caseTxt == "NotExist") return isKo ? "(이)가 없습니다" : "does not exist";
		if (caseTxt == "Deleted") return isKo ? "(이)가 삭제되었습니다" : "is successfully deleted";
		if (caseTxt == "NewTitle") return isKo ? "새 제목을 입력하세요" : "Enter the new title";
		if (caseTxt == "NewDesc") return isKo ? "새 설명을 입력하세요" : "Enter the new description";
		if (caseTxt == "Updated") return isKo ? "수정되었습니다" : "Item successfully updated";
		if (caseTxt == "NotToDisplay") return isKo ? "출력할 목록이 없습니다" : "There is nothing to display";
		if (caseTxt == "No") return isKo ? "번호" : "No";
		if (caseTxt == "LoadItem") return isKo ? "개의 항목을 불러왔습니다" : "items were found";
		if (caseTxt == "IOException") return isKo ? "파일이 없습니다" : "There is no file";
		
		return null;
	}
}
