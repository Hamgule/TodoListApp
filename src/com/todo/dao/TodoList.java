package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import com.todo.service.DbConnect;
import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;

public class TodoList {
	private List<TodoItem> list;
	Connection conn;

	public TodoList() {
		this.list = new ArrayList<TodoItem>();
		this.conn = DbConnect.getConnection();
	}

	public int addItem(TodoItem t) {
		String sql = "insert into list (title, desc, category, current_date, due_date) values (?, ?, ?, ?, ?);";
		PreparedStatement pstmt;
		int count = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			
			count = pstmt.executeUpdate();
			pstmt.close();
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return count;
	}

	public int deleteItem(int index) {
		String sql = "delete from list where id=?;";
		PreparedStatement pstmt;
		int count = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			
			count = pstmt.executeUpdate();
			pstmt.close();			
		}
		catch (SQLException e) { e.printStackTrace(); }
		
		return count;
	}

	public int editItem(TodoItem t) {
		String sql = "update list set title=?, desc=?, category=?, current_date=?, due_date=? where id=?;";
		PreparedStatement pstmt;
		int count = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setInt(6, t.getId());
			
			count = pstmt.executeUpdate();
			pstmt.close();			
			
		}
		catch (SQLException e) { e.printStackTrace(); }
		
		return count;
	}

	public int completeItem(int index, int is_completed) {
		PreparedStatement pstmt;
		
		String sql = "update list set is_completed=? where id=?;";
		
		int count = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 1 - is_completed);
			pstmt.setInt(2, index);
			
			count = pstmt.executeUpdate();
			
			pstmt.close();
			
		}
		catch (SQLException e) { e.printStackTrace(); }
		
		return count;
	}

	public int getIs_completed(int index) {
		PreparedStatement pstmt;
		
		String sql = "select is_completed from list where id=?;";
		
		int is_completed = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			
			ResultSet rs = pstmt.executeQuery();
			is_completed = rs.getInt("is_completed");
			
			pstmt.close();
			
		}
		catch (SQLException e) { e.printStackTrace(); }
		
		return is_completed;
	}
	
	public TodoItem getItem(int index) {
		TodoItem item = new TodoItem();
		PreparedStatement pstmt;
		
		try {
			String sql = "select * from list where id=?;";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			ResultSet rs = pstmt.executeQuery();
			
			rs.next();
			int id = rs.getInt("id");
			int is_completed = rs.getInt("is_completed");
			String category = rs.getString("category");
			String title = rs.getString("title");
			String desc = rs.getString("desc");
			String due_date = rs.getString("due_date");
			String current_date = rs.getString("current_date");
			
			item = new TodoItem(id, is_completed, title, desc, category, due_date, current_date);
		
			pstmt.close();
		}
		catch (SQLException e) { e.printStackTrace(); }
		
		return item;
	}
	
	public ArrayList<TodoItem> getList() { return getList("*", false); }
	public ArrayList<TodoItem> getList(String keyword, boolean isCate) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		boolean isAll = keyword.equals("*");
		
		if (!isAll) keyword = "%" + keyword + "%";
		
		try {
			String sql = "select * from list";
			if (!isAll) sql += " where " + (isCate ? "category like ?;" : "title like ? or desc like ?;");
			
			pstmt = conn.prepareStatement(sql);
			
			if (!isAll) {
				pstmt.setString(1, keyword);
				if (!isCate) pstmt.setString(2, keyword);
			}
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("id");
				int is_completed = rs.getInt("is_completed");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String desc = rs.getString("desc");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				
				list.add(new TodoItem(id, is_completed, title, desc, category, due_date, current_date));
			}
			
			pstmt.close();
		}
		catch (SQLException e) { e.printStackTrace(); }
		
		return list;
	}

	public int getCount() {
		Statement stmt;
		int count = 0;
		
		try {
			stmt = conn.createStatement();
			String sql = "select count(id) from list;";
			ResultSet rs = stmt.executeQuery(sql);
			
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
			
		} 
		catch (SQLException e) { e.printStackTrace(); }
		
		return count;
	}
	
	public ArrayList<String> getCategories() {
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		
		try {
			stmt = conn.createStatement();
			String sql = "select distinct category from list;";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) list.add(rs.getString("category"));
		}
		catch (SQLException e) { e.printStackTrace(); }
		
		return list;
	}
	
	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		
		try {
			stmt = conn.createStatement();
			String sql = "select * from list order by " + orderby + (ordering == 1 ? ";" : " desc;");
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				int id = rs.getInt("id");
				int is_completed = rs.getInt("is_completed");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String desc = rs.getString("desc");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				
				list.add(new TodoItem(id, is_completed, title, desc, category, due_date, current_date));
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
		
		return list;
	}
	
	public ArrayList<TodoItem> getCompletedList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		
		try {
			stmt = conn.createStatement();
			String sql = "select * from list where is_completed=1;";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				int id = rs.getInt("id");
				int is_completed = rs.getInt("is_completed");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String desc = rs.getString("desc");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				
				list.add(new TodoItem(id, is_completed, title, desc, category, due_date, current_date));
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
		
		return list;
	}
	
	/* Useless for now */
	
//	public void listAll() {
//		System.out.println("\n"
//				+ "inside list_All method\n");
//		for (TodoItem myitem : list) {
//			System.out.println(myitem.getTitle() + myitem.getDesc());
//		}
//	}

//	public void sortByName(int order) {
//		Collections.sort(list, new TodoSortByName());
//		if (order < 0) Collections.reverse(list);
//	}
//
//	public void sortByDate(int order) {
//		Collections.sort(list, new TodoSortByDate());
//		if (order < 0) Collections.reverse(list);
//	}

//	public int indexOf(TodoItem t) {
//		return list.indexOf(t);
//	}

	public Boolean isDuplicate(String title) {
		for (TodoItem item : list) {
			if (title.equals(item.getTitle())) return true;
		}
		return false;
	}
	
	public void importData(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "insert into list (title, desc, category, current_date, due_date) values (?, ?, ?, ?, ?);";
			
			int records = 0;
			while ((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String desc = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, desc);
				pstmt.setString(3, category);
				pstmt.setString(4, current_date);
				pstmt.setString(5, due_date);
				
				int count = pstmt.executeUpdate();
				if (count > 0) records++;
				pstmt.close();
			}
			
			System.out.println(records + " records read!!");
			br.close();
		}
		catch (Exception e) { e.printStackTrace(); }
	}
	
	public boolean isExist(int index) {
		PreparedStatement pstmt;
		String sql = "select count(id) from list where id=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			ResultSet rs = pstmt.executeQuery();
			
			rs.next();
			
			return rs.getInt("count(id)") > 0;
		}
		catch (SQLException e) { e.printStackTrace(); }
		
		return false;
	}
	
	public static void closeConnection() {
		DbConnect.closeConnection();
	}
}
