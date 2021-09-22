package com.todo.menu;
public class Menu {

    public static void displaymenu() {
        System.out.println("\n======================= 메뉴 =========================");
        System.out.println(String.format("%d. %-12s : %s", 1, "add", "항목 추가"));
        System.out.println(String.format("%d. %-12s : %s", 2, "del", "항목 삭제"));
        System.out.println(String.format("%d. %-12s : %s", 3, "edit", "항목 수정"));
        System.out.println(String.format("%d. %-12s : %s", 4, "ls", "항목 리스트 출력"));
        System.out.println(String.format("%d. %-12s : %s", 5, "ls_name_asc", "항목 리스트를 제목 오름차순으로 정렬"));
        System.out.println(String.format("%d. %-12s : %s", 6, "ls_name_desc", "항목 리스트를 제목 내림차순으로 정렬"));
        System.out.println(String.format("%d. %-12s : %s", 7, "ls_date", "항목 리스트를 시간 오름차순으로 정렬"));
        System.out.println(String.format("%d. %-12s : %s", 8, "help", "메뉴 재출력"));
        System.out.println(String.format("%d. %-12s : %s", 9, "exit", "프로그램 종료"));
    }
    
    public static void prompt() {
    	System.out.print("\ncommand > ");
    }
}
