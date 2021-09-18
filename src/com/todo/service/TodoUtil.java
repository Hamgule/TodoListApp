package com.todo.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList l, String title, String desc) { createItem("en", l, title, desc); }
	public static void createItem(String lang, TodoList l, String title, String desc) {
		
		Scanner sc = new Scanner(System.in);

		if (l.isDuplicate(title)) {
			System.out.println(TodoLanguage.msg(lang, "Duplicate"));
			return;
		}
		
		l.addItem(new TodoItem(title, desc));
		System.out.println(String.format("\n'%s' %s", title, TodoLanguage.msg(lang, "Added")));
	}

	public static void deleteItem(TodoList l, String title) { deleteItem("en", l, title); }
	public static void deleteItem(String lang, TodoList l, String title) {
		
		Scanner sc = new Scanner(System.in);
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println(String.format("\n'%s' %s", title, TodoLanguage.msg(lang, "Deleted")));
				return;
			}
		}

		System.out.println(String.format("\n'%s' %s", title, TodoLanguage.msg(lang, "NotExist")));
	}

	public static void updateItem(TodoList l, String title) { updateItem("en", l, title); }
	public static void updateItem(String lang, TodoList l, String title) {
		
		Scanner sc = new Scanner(System.in);
		
		if (!l.isDuplicate(title)) {
			System.out.println(String.format("\n'%s' %s", title, TodoLanguage.msg(lang, "NotExist")));
			return;
		}

		System.out.print(String.format("%s > ", TodoLanguage.msg(lang, "NewTitle")));
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println(TodoLanguage.msg(lang, "Duplicate"));
			return;
		}
		
		System.out.print(String.format("%s > ", TodoLanguage.msg(lang, "NewDesc")));
		String new_description = sc.next().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println(String.format("\n%s", TodoLanguage.msg(lang, "Updated")));
			}
		}

	}

	public static void listAll(TodoList l) { listAll("en", l); }
	public static void listAll(String lang, TodoList l) {
		if (l.getList().size() == 0) {
			System.out.println(String.format("\n%s", TodoLanguage.msg(lang, "NotToDisplay")));
			return;
		}
		String no = TodoLanguage.msg(lang, "No");
		String title = TodoLanguage.msg(lang, "Title");
		String desc = TodoLanguage.msg(lang, "Desc");
		System.out.println(String.format("\n  %-2s   %-10s   %-20s", no, title, desc));
		
		ArrayList<TodoItem> list = l.getList();
		for (int i = 0; i < list.size(); i++) {
			System.out.println(String.format("  %02d | %-10s | %-20s", i + 1, list.get(i).getTitle(), list.get(i).getDesc()));
		}
	}

	public static void sortItem(TodoList l, String std, String dir) { sortItem("en", l, std, dir); }
	public static void sortItem(String lang, TodoList l, String std, String dir) {
		if (std.equals("name")) l.sortByName();
		else if (std.equals("date")) l.sortByDate();
		
		if (dir.equals("desc")) l.reverseList();
	}
	
	public static void saveList(TodoList l, String filename) { saveList("en", l, filename); }
	public static void saveList(String lang, TodoList l, String filename) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(filename);
			
			for (TodoItem item : l.getList())
				writer.write(item.toSaveString());
			
			writer.close();
		} 
		catch (IOException e) { e.printStackTrace(); }
	}
	
	public static void loadList(TodoList l, String filename) { loadList("en", l, filename); }
	public static void loadList(String lang, TodoList l, String filename) {
		BufferedReader reader = null;
		String line;
		TodoList list = new TodoList();
		int count = 0;
		
		try { 
			reader = new BufferedReader(new FileReader(filename));
			
			while ((line = reader.readLine()) != null) {
				String[] lineList = line.split("##");
				TodoItem t = new TodoItem(lineList[0], lineList[1]);
				t.setCurrent_date(lineList[2]);
				l.addItem(t);
				count++;
			}
			
			System.out.println(String.format("\n%d %s", count, TodoLanguage.msg(lang, "LoadItem")));
			reader.close();
		} 
		catch (IOException e) {
			System.out.println(String.format("\n%s", TodoLanguage.msg(lang, "IOException")));
		}
	}
}
