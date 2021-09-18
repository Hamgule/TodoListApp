package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoLanguage;
import com.todo.service.TodoUtil;

public class TodoMain {
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean isList = false;
		boolean quit = false;
		boolean isWrongInput = false;
		String lang = "en";
		String title, desc;
		String std, dir;
		
		TodoUtil.loadList(lang, l, "todolist.txt");
		Menu.displaymenu(lang);
		
		do {
			isList = false;
			isWrongInput = false;
			
			prompt(lang);
			String[] cmds = sc.nextLine().split(" ");
			for (int i = 3; i < cmds.length; i++) cmds[2] += " " + cmds[i];
			
			switch (cmds[0]) {
			case "lang":
				try {
					if (cmds[1].equals(lang)) {
						System.out.println(TodoLanguage.msg(lang, "NotChangeLang"));
						break;
					}
					if (cmds[1].equals("ko") || cmds[1].equals("en")) lang = cmds[1];
					else isWrongInput = true;
				}
				catch (ArrayIndexOutOfBoundsException e) {
					System.out.print(String.format("%s > ", TodoLanguage.msg(lang, "SelectLang")));
					lang = sc.next(); 
					sc.nextLine();
				}
				System.out.println(String.format("\n%s", TodoLanguage.msg(lang, "ChangeLang")));
				break;
				
			case "add":
				try { title = cmds[1]; }
				catch (ArrayIndexOutOfBoundsException e) { 
					System.out.print(String.format("%s > ", TodoLanguage.msg(lang, "Title")));
					title = sc.next();
					sc.nextLine();
				}
				try { desc = cmds[2]; }
				catch (ArrayIndexOutOfBoundsException e) { 
					System.out.print(String.format("%s > ", TodoLanguage.msg(lang, "Desc")));
					desc = sc.nextLine();
				}
				TodoUtil.createItem(lang, l, title, desc);
				break;
			
			case "del":
				try { title = cmds[1]; }
				catch (ArrayIndexOutOfBoundsException e) { 
					System.out.print(String.format("%s > ", TodoLanguage.msg(lang, "Title")));
					title = sc.next();
					sc.nextLine();
				}
				TodoUtil.deleteItem(lang, l, title);
				break;
				
			case "edit":
				try { title = cmds[1]; }
				catch (ArrayIndexOutOfBoundsException e) { 
					System.out.print(String.format("%s > ", TodoLanguage.msg(lang, "Title")));
					title = sc.next();
					sc.nextLine();
				}
				TodoUtil.updateItem(lang, l, title);
				break;
				
			case "ls":
				TodoUtil.listAll(lang, l);
				break;
			
			case "sort":
				try { 
					std = cmds[1];
				}
				catch (ArrayIndexOutOfBoundsException e) {
					System.out.print(String.format("%s > ", TodoLanguage.msg(lang, "Standard")));
					std = sc.next();
					sc.nextLine();
				}
				
				if (std.equals("name") || std.equals("date"));
				else { System.out.print(String.format("\n'%s' ", std)); isWrongInput = true; break; }
				
				try { 
					dir = cmds[2]; 
				}
				catch (ArrayIndexOutOfBoundsException e) { 
					System.out.print(String.format("%s > ", TodoLanguage.msg(lang, "Direction")));
					dir = sc.next();
					sc.nextLine();
				}
				
				if (dir.equals("asc") || dir.equals("desc"));
				else { System.out.print(String.format("\n'%s' ", dir)); isWrongInput = true; break; }
				
				TodoUtil.sortItem(lang, l, std, dir);
				isList = true;
				break;

			case "help":
				Menu.displaymenu(lang);
				break;
				
			case "exit":
				quit = true;
				System.out.println(String.format("\n%s", TodoLanguage.msg(lang, "Terminate")));
				break;

			default:
				isWrongInput = true;
				break;
			}
			
			if(isList) TodoUtil.listAll(lang, l);
			if (isWrongInput) System.out.println(TodoLanguage.msg(lang, "WrongInput"));
			
		} while (!quit);

		TodoUtil.saveList(lang, l, "todolist.txt");
		System.out.println(String.format("\n%s", TodoLanguage.msg(lang, "SaveItem")));
	}
	
	public static void prompt(String lang) {
		System.out.print(String.format("\n%s > ", TodoLanguage.msg(lang, "Command")));
	}
}
