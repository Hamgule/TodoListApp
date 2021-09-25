package com.todo.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String category, title, desc, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n=== 항목 추가 ===");
		System.out.print("카테고리(category) > ");
		
		category = sc.next().trim();
		
		System.out.print("제목(title) > ");
		
		title = sc.next().trim();
		if (list.isDuplicate(title)) {
			System.out.println("\n중복되는 제목입니다");
			return;
		}

		sc.nextLine().trim();
		System.out.print("내용(description) > ");
		do {
			desc = sc.nextLine().trim();
		} while (desc.equals(""));

		System.out.print("마감일자(due date) > ");
		due_date = sc.next().trim();
		
		TodoItem t = new TodoItem(category, title, desc, due_date);
		list.addItem(t);
		
		System.out.println(String.format("\n'%s' 항목이 추가되었습니다", title));
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);

		int size = l.getList().size();
		
		System.out.println("\n=== 항목 삭제 ===");
		
		
		if (size == 0) {
			System.out.println("\n삭제할 항목이 없습니다");
			return;
		}
		
		System.out.print("삭제할 번호 입력 > ");
		
		int no;
		
		while (true) {
			no = sc.nextInt();
			if (no > 0 && no <= size) break;
			
			System.out.print(String.format("\n숫자를 잘못 입력하셨습니다 (%s) > ", size == 1 ? "1" : "1 ~ " + size));
		}

		TodoItem item = l.getList().get(no - 1);
		
		int[] max_width = {8, 5, 11};
		max_width = getMax_width(l, max_width);
		listLine(item, no - 1, max_width);
		
		String choice;
		
		do {
			System.out.print("\n위 항목을 삭제하시겠습니까? (y/n) > ");
			
			choice = sc.next().toLowerCase();
			
			switch (choice) {
			case "y":
				System.out.println(String.format("\n%d번 항목이 삭제되었습니다", no));
				l.deleteItem(no - 1);
				break;
			
			case "n": 
				System.out.println(String.format("\n취소하였습니다", no));
				break;
			
			default:
				System.out.println("잘못 입력했습니다");
				break;
			}
		} while (!choice.equals("y") && !choice.equals("n"));
		
	}

	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);

		int size = l.getList().size();
		
		System.out.println("\n=== 항목 수정 ===");
		
		if (size == 0) {
			System.out.println("\n수정할 항목이 없습니다");
			return;
		}
		
		System.out.print("수정할 번호 입력 > ");

		int no;
		
		while (true) {
			no = sc.nextInt();
			if (no > 0 && no <= size) break;
			
			System.out.print(String.format("\n숫자를 잘못 입력하셨습니다 (%s) > ", size == 1 ? "1" : "1 ~ " + size));
		}

		TodoItem item = l.getList().get(no - 1);
		
		int[] max_width = {8, 5, 11};
		max_width = getMax_width(l, max_width);
		listLine(item, no - 1, max_width);

		System.out.print("\n새 카테고리(new category) > ");
		String new_category = sc.next().trim();

		System.out.print("새 제목(new title) > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("\n중복되는 제목입니다");
			return;
		}
		sc.nextLine().trim();

		String new_desc;
		System.out.print("새 내용(new description) > ");
		do {
			new_desc = sc.nextLine().trim();
		} while (new_desc.equals(""));
		
		System.out.print("새 마감일자(new due date) > ");
		String new_due_date = sc.next().trim();
		
		l.deleteItem(no - 1);
		TodoItem t = new TodoItem(new_category, new_title, new_desc, new_due_date);
		l.addItem(t);
		
		System.out.println(String.format("\n%d번 항목이 수정되었습니다", no));
		
	}
	
	public static void findItem(TodoList l, String keyword, Boolean isCate) {

		Scanner sc = new Scanner(System.in);

		if (isCate) System.out.println("\n=== 카테고리 검색 ===");
		else System.out.println("\n=== 항목 검색 ===");
		
		int count = 0;
		int[] max_width = {8, 5, 11};
		max_width = getMax_width(l, max_width);
		
		for (TodoItem item : l.getList()) {
			if ((item.hasText(keyword) && !isCate) || (item.getCategory().contains(keyword) && isCate)) {
				listLine(item, l.indexOf(item), max_width);
				count++;
			}
		}
		
		if (count == 0) System.out.println(String.format("\n항목을 찾지 못했습니다", count));
		else System.out.println(String.format("\n총 %d개의 항목을 찾았습니다", count));
	}
	
	private static int countKor(String str) {
		int cnt = 0;
		for (int i = 0; i < str.length(); i++) {
			if (Character.toString(str.charAt(i)).matches("^[가-힣]*$")) cnt++;
		}
		return cnt;
	}
	
	private static int[] getMax_width(TodoList l, int[] max_width) {
		int c_kor, t_kor, d_kor;
		
		for (TodoItem item : l.getList()) {
			c_kor = countKor(item.getCategory()); 
			t_kor = countKor(item.getTitle()); 
			d_kor = countKor(item.getDesc());
			
			max_width[0] = Math.max(item.getCategory().length() + c_kor, max_width[0]);
			max_width[1] = Math.max(item.getTitle().length() + t_kor, max_width[1]);
			max_width[2] = Math.max(item.getDesc().length() + d_kor, max_width[2]);
		}
		
		return max_width;
	}
	
	public static void listLine(TodoItem item, int index, int[] max_width) {
		int c_kor = countKor(item.getCategory()); 
		int t_kor = countKor(item.getTitle()); 
		int d_kor = countKor(item.getDesc());

		System.out.println(String.format(
			" %2d | %-" + (max_width[0] - c_kor)
			 + "s | %-" + (max_width[1] - t_kor)
			 + "s | %-" + (max_width[2] - d_kor)
			 + "s | %-10s | %s", 
			index + 1, item.getCategory(), item.getTitle(), item.getDesc(), item.getDue_date(), item.getCurrent_date())
		);
	}
	
	public static void listAll(TodoList l) {
		int contents = l.getList().size();
		
		if (contents == 0) {
			System.out.println("\n표시할 항목이 없습니다");
			return;
		}
		
		int total_width = 0;
		int[] max_width = {8, 5, 11};
		
		max_width = getMax_width(l, max_width);
		
		total_width = max_width[0] + max_width[1] + max_width[2] + 47;
		String ls = String.format(" 전체 목록 (총 %-2d개) ", contents);
		int ls_len = ls.length() + countKor(ls);
		
		for (int i = 0; i < (total_width - ls_len) / 2; i++) System.out.print("=");
		System.out.print(ls);
		for (int i = 0; i < (total_width - ls_len) / 2; i++) System.out.print("=");
		System.out.println((total_width - ls_len) % 2 == 0 ? "" : "=");
		
		System.out.println(String.format(
			" %2s | %-" + max_width[0] + "s | %-" + max_width[1] + "s | %-" + max_width[2] + "s | %-10s | %s", 
			"No", "category", "title", "description", "due date", "written time"
		));
		for (TodoItem item : l.getList()) listLine(item, l.getList().indexOf(item), max_width);
	}
	
	public static void listCategories(TodoList l) {
		Set<String> hs = new HashSet<String>();
		
		for (TodoItem item : l.getList()) hs.add(item.getCategory());
		
		Iterator<String> it = hs.iterator();

		int size = hs.size();
		
		System.out.println("\n=== 카테고리 목록 ===");
		
		if (size == 0) {
			System.out.println("\n등록된 카테고리가 없습니다");
			return;
		}
		
		while (it.hasNext()) {
			System.out.print(it.next());
			if (it.hasNext()) System.out.print(" / ");
		}
		
		System.out.println(String.format("\n\n총 %d개의 카테고리가 등록되어 있습니다", size));
		
	}
	
	public static void saveList(TodoList l, String filename) {
		try {
			FileWriter fw = new FileWriter(filename);
			
			for (TodoItem i : l.getList()) {
				fw.write(i.toSaveString());
			}
			
			fw.close();
		} 
		catch (IOException e) { e.printStackTrace(); }
	}

	public static void loadList(TodoList l, String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			StringTokenizer st;
			String line;
			
			while ((line = br.readLine()) != null) {
				st = new StringTokenizer(line, "##");
				
				String category = st.nextToken();
				String title = st.nextToken();
				String desc = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				
				TodoItem t = new TodoItem(category, title, desc, due_date, current_date);
				
				l.addItem(t);
			}
			
			int contents = l.getList().size();
			if (contents == 0) System.out.println("파일에 불러올 항목이 없습니다");
			else System.out.println(String.format("%d 개의 항목을 불러왔습니다", contents));
			br.close();
		} 
		catch (IOException e) { 
			System.out.println("파일이 없습니다");
		}
	}
}
