package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean isList = false;
		boolean quit = false;
		String keyword;
		
		String filename = "todolist.txt";
		TodoUtil.loadList(l, filename);

		Menu.displaymenu();
		do {
			Menu.prompt();
			isList = false;
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
				TodoUtil.listCategories(l);
				break;
				
			case "ls_name_asc":
				l.sortByName(1);
				isList = true;
				break;

			case "ls_name_desc":
				l.sortByName(-1);
				isList = true;
				break;
				
			case "ls_date_asc":
				l.sortByDate(1);
				isList = true;
				break;

			case "ls_date_desc":
				l.sortByDate(-1);
				isList = true;
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
			
			if(isList) TodoUtil.listAll(l);
			
		} while (!quit);
		
		TodoUtil.saveList(l, filename);
		System.out.println("\n저장되었습니다");
	}
}
