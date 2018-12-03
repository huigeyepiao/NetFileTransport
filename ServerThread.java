package com.captain.demo.socketFile;

import com.captain.demo.utils.FileUtil;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务器处理线程
 * Created by captain on 2017/7/31.
 */
public class ServerThread implements Runnable {

    private Socket socket;

    /**
     * 传入打开的客户端连接
     * @param socket
     */
    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("服务器转送线程已开启");
        try {
            Reader reader = new InputStreamReader(socket.getInputStream());
            BufferedReader in = new BufferedReader(reader);
            //对暗号，防止其它程序恶意进攻
            String message = in.readLine();
            if(!"天王盖地虎".equals(message)){
                return;
            }
            System.out.println("服务器暗号对接通过");
            //接收传输的文本内容
            List<String> context = getFile(in);
            System.out.println("服务器文件接收完成");
            String filename = in.readLine();//客户端指定的文件名
            System.out.println("服务器文件名接收完成");
            boolean flag = FileUtil.writeFile(FileUtil.getFullPath(SocketFileServer.PATH,filename)
                    ,context,false);
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            if(flag){
                writer.println("文件保存成功");
            }else{
                writer.println("文件保存失败");
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读取文本文件的全部内容
     * @param in 输入流
     * @return 读取到的文件内容
     * @throws IOException
     */
    private List<String> getFile(BufferedReader in) throws IOException {
        List<String> context = new ArrayList<>();
        while(true){
            String tempStr = in.readLine();
            if("endendend".equals(tempStr)){
                break;
            }
            context.add(tempStr);
        }
        return context;
    }
}
