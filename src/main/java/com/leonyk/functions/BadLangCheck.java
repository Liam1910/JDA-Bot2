package com.leonyk.functions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BadLangCheck {

    public static List<String> badWords() {
        List<String> bad = new ArrayList<>();

        bad.add("negga");
        bad.add("nigger");
        bad.add("nigga");
        bad.add("neggar");
        bad.add("asshole");
        bad.add("arschloch");
        //bad.add("");

        return bad;
    }


    public static Boolean BadLanguageCheck(@NotNull String message) {
        String check = message.toLowerCase();

        for (String word : badWords()) {
            if (check.contains(word)) {
                return true;
            }
        }
        return false;
    }
}
