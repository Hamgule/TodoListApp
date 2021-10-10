package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
	private int id;
	private int is_completed;
    private String category;
    private String title;
    private String desc;
    private String due_date;
    private String current_date;

    public TodoItem() {}
    
	public TodoItem(String title, String desc, String category, String due_date) {
    	this.title = title;
        this.desc = desc;
        this.category = category;
        this.due_date = due_date;
        
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        this.current_date = f.format(new Date());
    }

    public TodoItem(String title, String desc, String category, String due_date, String current_date) {
    	this.category = category;
    	this.title = title;
        this.desc = desc;
        this.due_date = due_date;
        this.current_date = current_date;
    }
    
    public TodoItem(int id, int is_completed, String title, String desc, String category, String due_date, String current_date) {
        this.id = id;
        this.is_completed = is_completed;
    	this.category = category;
    	this.title = title;
        this.desc = desc;
        this.due_date = due_date;
        this.current_date = current_date;
    }

    public int getId() { return id; }
	public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDesc() { return desc; }
    public void setDesc(String desc) { this.desc = desc; }
    
    public String getCategory() { return category; }
	public void setCategory(String category) { this.category = category; }

	public String getDue_date() { return due_date; }
	public void setDue_date(String due_date) { this.due_date = due_date; }

    public String getCurrent_date() { return current_date; }
	public void setCurrent_date(String current_date) { this.current_date = current_date; }

	public int getIs_completed() { return is_completed; }
	public void setIs_completed(int is_completed) { this.is_completed = is_completed; }
    
	public String toString(int[] width) {
		return String.format(" %2d | %-3"
			 + "s | %-" + width[0]
			 + "s | %-" + width[1]
			 + "s | %-" + width[2]
			 + "s | %-10s | %s", 
			id, is_completed == 1 ? " V": " ", category, title, desc, due_date, current_date
		);
	}
	
	// Useless for now
    public String toSaveString() {
    	return String.format("%s##%s##%s##%s##%s\n", category, title, desc, due_date, current_date);
    }
}
