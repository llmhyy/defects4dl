package com.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.LineNumberReader;

public class CodeUtils {

    private final static String CACHE_PATH = "cache";
    public static List<String> getRootCause(String bugID) throws Exception{
        String path=CACHE_PATH+File.separator+bugID+"-fix";
        File file =new File(path);
        List<String> result =new ArrayList<>();
        FileReader  fr =new FileReader(file);
        BufferedReader reader=new BufferedReader(fr);
        String line;
        while((line=reader.readLine())!=null){
            line=line.replaceAll("\n", "").trim();
            if (!"".equals(line) || line!=null) {
                result.add(line);
            }
        }
        return result;
    }
}
