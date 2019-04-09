package com.example.servicesocket;

import com.example.socket.CircularQueue;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by jesgoo on 2018/4/25.
 */

public class TcpClient {
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    public TcpClient(){
        try {
            System.out.println("尝试连接服务器ip端口 127.0.0.1:8889");
            socket = new Socket("127.0.0.1",8889);// TODO: 2018/4/25
            System.out.println("客户端已经成功连接至服务器");
            //请求信息拼接
            String ipRequest = "{123456789012345678}{127.0.0.1}{1234}";
            String requestLength = String.format("%04d", ipRequest.length());//获取长度，不足的补0，如长度56，则输出0056
            ipRequest = requestLength+ipRequest;
            System.out.println("发送给服务端的信息是:"+ipRequest);
            InputStream in_withcode = new ByteArrayInputStream(ipRequest.getBytes());
            BufferedReader line = new BufferedReader(new InputStreamReader(in_withcode));
            out = new PrintWriter(socket.getOutputStream(),true);
            out.println(line.readLine());
            //获取服务端返回信息
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(in.readLine());
            out.close();
            in.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
//        new TcpClient();

    }
}
