package com.captain.demo.socketFile;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 文件传送服务器端
 * Created by captain on 2017/7/31.
 */
public class SocketFileServer {

    //默认要保存文件的文件夹路径
    public final static String PATH = "D:\\logs\\socketfile";

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(7070);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(serverSocket==null){
            System.out.println("服务器启动失败");
            return;
        }
        System.out.println("服务器已启动");
        while(true){
            try {
                System.out.println("等待客户端连接... ...");
                Socket socket = serverSocket.accept();
                ServerThread serverThread = new ServerThread(socket);
                new Thread(serverThread).start();
            } catch (IOException e) {
                System.out.println("服务器打开客户端失败");
                e.printStackTrace();
            }
        }
    }

}
