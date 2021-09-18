package com.todo.menu;

import com.todo.TodoMain;
import com.todo.service.TodoLanguage;

public class Menu {

    public static void displaymenu(String lang) {
        System.out.println("===========================================");
        System.out.println(String.format("1. %-18s : %s", "lang <lang>", TodoLanguage.msg(lang, "langDesc")));
        System.out.println(String.format("2. %-18s : %s", "add <title> <desc>", TodoLanguage.msg(lang, "addDesc")));
        System.out.println(String.format("3. %-18s : %s", "del <title>", TodoLanguage.msg(lang, "delDesc")));
        System.out.println(String.format("4. %-18s : %s", "edit <title>", TodoLanguage.msg(lang, "editDesc")));
        System.out.println(String.format("5. %-18s : %s", "ls", TodoLanguage.msg(lang, "lsDesc")));
        System.out.println(String.format("6. %-18s : %s", "sort <std> <dir>", TodoLanguage.msg(lang, "sortDesc")));
        System.out.println(String.format("7. %-18s : %s", "help", TodoLanguage.msg(lang, "helpDesc")));
        System.out.println(String.format("8. %-18s : %s", "exit", TodoLanguage.msg(lang, "exitDesc")));
        System.out.println("===========================================");
    }
}
