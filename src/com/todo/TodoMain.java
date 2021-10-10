package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean quit = false;
		String keyword;
		
		// Data import completed
//		l.importData("todolist.txt"); 
		
		Menu.displaymenu();
		
		do {
			Menu.prompt();
			String choice = sc.next();
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
			
			case "find":
				keyword = sc.next();
				TodoUtil.findItem(l, keyword, false);
				break;
			
			case "find_cate":
				keyword = sc.next();
				TodoUtil.findItem(l, keyword, true);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			case "ls_cate":
				TodoUtil.listCateAll(l);
				break;
				
			case "ls_name":
				TodoUtil.listAll(l, "title", 1);
				break;

			case "ls_name_desc":
				TodoUtil.listAll(l, "title", 0);
				break;
				
			case "ls_date":
				TodoUtil.listAll(l, "current_date", 1);
				break;

			case "ls_date_desc":
				TodoUtil.listAll(l, "current_date", 0);
				break;
				
			case "comp":
				int index = sc.nextInt();
				TodoUtil.completeItem(l, index);
				break;
				
			case "ls_comp":
				TodoUtil.listCompleted(l);
				break;
				
			case "help":
				Menu.displaymenu();
				break;
				
			case "exit":
				quit = true;
				System.out.println("\n프로그램 종료 중 ...");
				break;

			default:
				System.out.println(String.format("'%s' 명령어가 존재하지 않습니다 (도움말 - help)", choice));
				break;
			}
			
		} while (!quit);
		
		TodoList.closeConnection();
		
	}
}
