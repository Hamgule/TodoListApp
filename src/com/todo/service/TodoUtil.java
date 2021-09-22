package com.todo.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n=== 항목 추가 ===");
		System.out.print("제목(title) > ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.println("\n중복되는 제목입니다");
			return;
		}

		sc.nextLine();
		System.out.print("설명(description) > ");
		desc = sc.nextLine();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		
		System.out.println(String.format("\n'%s' 항목이 추가되었습니다", title));
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);

		System.out.println("\n=== 항목 삭제 ===");
		System.out.print("제목(title) > ");
		
		String title = sc.next();
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println(String.format("\n'%s' 항목이 삭제되었습니다", title));
				return;
			}
		}

		System.out.println(String.format("\n'%s' 항목이 존재하지 않습니다", title));
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);

		System.out.println("\n=== 항목 수정 ===");
		System.out.print("제목(title) > ");
		
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println(String.format("\n'%s' 항목이 존재하지 않습니다", title));
			return;
		}

		System.out.print("\n새 제목(new title) > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("\n중복되는 제목입니다");
			return;
		}
		sc.nextLine();

		System.out.print("새 설명(new description) > ");
		String new_description = sc.nextLine().trim();
		
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println(String.format("\n'%s' 항목이 수정되었습니다", title));
				
				return;
			}
		}

	}

	public static void listAll(TodoList l) {
		if (l.getList().size() == 0) {
			System.out.println("\n표시할 항목이 없습니다");
			return;
		}
		
		String title, desc, current_date;
		int t_kor, d_kor;
		
		System.out.println("\n=============================================================");
		System.out.println(String.format("%-12s | %-20s | %s", "제목(title)", "설명(description)", "작성시간(date)"));
		for (TodoItem item : l.getList()) {
			title = item.getTitle();
			desc = item.getDesc();
			current_date = item.getCurrent_date();
			t_kor = 0; d_kor = 0;
			
			for (int i = 0; i < title.length(); i++) {
				if (Character.toString(title.charAt(i)).matches("^[가-힣]*$")) t_kor++;
			}
			for (int i = 0; i < desc.length(); i++) {
				if (Character.toString(desc.charAt(i)).matches("^[가-힣]*$")) d_kor++;
			}
			System.out.println(String.format(
					"%-" + (14 - t_kor) + "s | %-" + (22 - d_kor) + "s | %s", title, desc, current_date)
			);
		}
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

				String title = st.nextToken();
				String desc = st.nextToken();
				String current_date = st.nextToken();
				
				TodoItem t = new TodoItem(title, desc);
				t.setCurrent_date(current_date);
				
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
