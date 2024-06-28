package com.testprocessor.textprocessor.textprocessing;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextProcessor {
    public List<String> findMatches(String text, String regex){
        List<String> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()){
            matches.add(matcher.group());
        }
        return matches;
    }

    public  String replaceAllOccurances(String text, String regex, String replacement){
        return text.replaceAll(regex, replacement);
    }

    public int countWordsMatchingPattern(String text, String regex) {
        int count = 0;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            count++;
        }

        return count;
    }




}

