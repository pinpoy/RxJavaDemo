package com.example.servicesocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {

    public static void main(String[] args) {
        new TcpServer();
    }

    private ServerSocket ss;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public TcpServer() {
        try {
            ss = new ServerSocket(8889);// TODO: 2018/4/25
            while (true) {
                //等待连接请求
                socket = ss.accept();// TODO: 2018/4/25
                //获取ip和端口
                String remoteIP = socket.getInetAddress().getHostAddress();
                String remotePort = ":" + socket.getLocalPort();
                System.out.println("客户端请求IP为 :" + remoteIP + remotePort);
                //获取请求过来的数据
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line = in.readLine();
                System.out.println("客户端的请求信息 :" + line);
                //返回处理信息
                out = new PrintWriter(socket.getOutputStream(), true);
                out.println("服务端已收到请求信息!");
                out.close();
                in.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
