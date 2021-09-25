package com.todo.menu;
public class Menu {

    public static void displaymenu() {
    	int no = 0;
        System.out.println("\n========================= 메뉴 ===========================");
        System.out.println(String.format("%2d. %-15s : %s", ++no, "add", "항목 추가"));
        System.out.println(String.format("%2d. %-15s : %s", ++no, "del", "항목 삭제"));
        System.out.println(String.format("%2d. %-15s : %s", ++no, "edit", "항목 수정"));
        System.out.println(String.format("%2d. %-15s : %s", ++no, "find <key>", "특정 문자열을 포함하는 항목 검색"));
        System.out.println(String.format("%2d. %-15s : %s", ++no, "find_cate <key>", "카테고리 검색"));
        System.out.println(String.format("%2d. %-15s : %s", ++no, "ls", "항목 리스트 출력"));
        System.out.println(String.format("%2d. %-15s : %s", ++no, "ls_cate", "카테고리 목록 출력"));
        System.out.println(String.format("%2d. %-15s : %s", ++no, "ls_name_asc", "항목 리스트를 제목 오름차순으로 정렬"));
        System.out.println(String.format("%2d. %-15s : %s", ++no, "ls_name_desc", "항목 리스트를 제목 내림차순으로 정렬"));
        System.out.println(String.format("%2d. %-15s : %s", ++no, "ls_date_asc", "항목 리스트를 오래된순으로 정렬"));
        System.out.println(String.format("%2d. %-15s : %s", ++no, "ls_date_desc", "항목 리스트를 최신순으로 정렬"));
        System.out.println(String.format("%2d. %-15s : %s", ++no, "help", "메뉴 재출력"));
        System.out.println(String.format("%2d. %-15s : %s", ++no, "exit", "프로그램 종료"));
    }
    
    public static void prompt() {
    	System.out.print("\ncommand > ");
    }
}
