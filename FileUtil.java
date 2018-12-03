package com.captain.demo.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;



public class FileUtil {

    public final static String FILE_EXTENSION_SEPARATOR = ".";

    private FileUtil() {
        throw new AssertionError();
    }

    /**
     * read file
     * 
     * @param filePath
     * @param charsetName The name of a supported {@link java.nio.charset.Charset </code>charset<code>}
     * @return if file not exist, return null, else return content of file
     * @throws RuntimeException if an error occurs while operator BufferedReader
     */
    public static String FILE_READING_ENCODING = "GBK";
    public static String FILE_WRITING_ENCODING = "GBK";

    public static List<String> readFile(String path) {
        List<String> lines = new ArrayList<String>();
        String tempstr = null;
        try {
            File file = new File(path);
            if(!file.exists()) {
                throw new FileNotFoundException();
            }
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis, "GBK"));
            while((tempstr = br.readLine()) != null) {
                lines.add(tempstr.toString());
            }
        } catch(IOException ex) {
            System.out.println(ex.getStackTrace());
        }
        return lines;
    }
    
    
    public static void writeLog(String path, String str)
    {
        try
        {
            File file = new File(path);
            if(!file.exists())
                file.createNewFile();
            FileOutputStream out = new FileOutputStream(file); //true表示追加 
            StringBuffer sb = new StringBuffer();
            sb.append(str + "\r\n");
            out.write(sb.toString().getBytes("utf-8"));//
            out.close();
        }
        catch(IOException ex)
        {
            System.out.println(ex.getStackTrace());
        }
    }
    
    /**
     * 写入文件,末尾自动添加\r\n
     * @param path
     * @param str
     */
    public static void writeFile(String path, String str, boolean is_append, String encode)
    {
        try
        {
            File file = new File(path);
            if(!file.exists())
                file.createNewFile();
            FileOutputStream out = new FileOutputStream(file, is_append); //true表示追加 
            StringBuffer sb = new StringBuffer();
            sb.append(str + "\r\n");
            out.write(sb.toString().getBytes(encode));//
            out.close();
        }
        catch(IOException ex)
        {
            System.out.println(ex.getStackTrace());
        }
    }
    
    
    private static String removeBomHeaderIfExists(String _sLine) {
        if (_sLine == null) {
            return null;
        }
        String line = _sLine;
        if (line.length() > 0) {
            char ch = line.charAt(0);
            // ʹ��while����Ϊ��һЩ���߿�����ĳЩ�ļ�ǰ�����ֽڶ���0xfffe.
            // 0xfeff,0xfffe���ֽ���Ĳ�ͬ����.JVM��,һ����0xfeff
            while ((ch == 0xfeff || ch == 0xfffe)) {
                line = line.substring(1);
                if (line.length() == 0) {
                    break;
                }
                ch = line.charAt(0);
            }
        }
        return line;
    }

}

   
