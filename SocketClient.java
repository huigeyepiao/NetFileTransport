package com.captain.demo.socketFile;

import com.captain.demo.utils.FileUtil;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

/**
 * 文件传送客户端
 * Created by captain on 2017/7/31.
 */
public class SocketClient {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SocketClient client = new SocketClient();
        while (true){
            System.out.println("请输入您需要的功能：1传送文件，2退出");
            String message = sc.next();
            if("2".equals(message)){
                break;
            }else{
                System.out.println("请输入要传送文件的完整路径：");
                String path = sc.next();
                try {
                    List<String> context = FileUtil.readFile(path); //读取文件内容
                    if(context.size()>0){  //文件存在且有内容则传送给服务器
                        boolean flag = client.sendFileToServer(context
                                ,path.substring(path.lastIndexOf("\\"),path.length()));
                        if(flag){
                            System.out.println("文件传送成功");
                        }else{
                            System.out.println("文件传送失败");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        sc.close();
    }

    /**
     * 发送文件内容到服务器
     * @param context
     * @param fileName
     * @return
     * @throws IOException
     */
    private boolean sendFileToServer(List<String> context,String fileName) throws IOException {
        Socket socket = new Socket("localhost",7070);
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        writer.println("天王盖地虎");  //第一步对暗号
        writer.flush();
        for(String str : context){  //发送文件内容
            writer.println(str);
            writer.flush();
        }
        writer.println("endendend");  //发送结束标志
        writer.flush();
        writer.println(fileName);  //发送文件名
        writer.flush();
        Reader reader = new InputStreamReader(socket.getInputStream());
        BufferedReader in = new BufferedReader(reader);
        String serverMessage = in.readLine();  //获取结果并返回
        socket.close();
        if("文件保存成功".equals(serverMessage)){
            return true;
        }else{
            return false;
        }
    }

}
