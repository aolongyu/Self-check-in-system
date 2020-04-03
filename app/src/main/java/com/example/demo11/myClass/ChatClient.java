package com.example.demo11.myClass;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


/**
 * 注意用到的输入输出流DataInputStream和DataOutputStream，成对出现，最好用字节流
 */
// 客户端类
public class ChatClient implements Runnable{//创建公共类

    static private String host = "192.168.1.6";// 默认连接到本机
    static private int port = 12345;// 默认连接到端口12345
    static private Socket socket;
    static private DataInputStream in;
    static private DataOutputStream out;
    static private String sendMsg;
    static private String recvMsg;
    static
    {
        try{
            socket = new Socket(host, port);//创建Socket类对象
            in = new DataInputStream(socket.getInputStream());// 读取服务器端传过来信息的DataInputStream
            out = new DataOutputStream(socket.getOutputStream());// 向服务器端发送信息的DataOutputStream
            sendMsg = "0";
            recvMsg = "";
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }


    public ChatClient() throws IOException {

    }
    // 连接到指定的主机和端口
    public void setSendMsg(String Message)
    {
        sendMsg = Message;
    }

    public String getRecvMsg()
    {
        String ans = new String(recvMsg);
        recvMsg = "";
        return ans;
    }


    public void SendMessage() throws IOException
    {
        if(!sendMsg.equals("0"))
        {
            System.out.println("客户端：" + sendMsg);
            byte[] responseBuffer = sendMsg.getBytes("GB2312");
            out.write(responseBuffer, 0, responseBuffer.length);//将客户端的信息传递给服务器
            sendMsg = "0";
        }
        return;
    }


    public void chat() throws IOException {//chat方法
        try {
            // 连接到服务器
            new Thread(this).start();
            while (true) {
                SendMessage();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        finally {
//            socket.close();//关闭Socket监听
//        }
    }

    public void run() { //新线程
        try {
            while (true) {
                byte[] buffer = new byte[10000];  //缓冲区的大小
                in.read(buffer);// 读取来自服务器的信息
                recvMsg = new String(buffer, "GB2312").trim();
                //Thread.sleep(10000);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
//        finally {
//            try {
//                socket.close();//关闭Socket监听
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
