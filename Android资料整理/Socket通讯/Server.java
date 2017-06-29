

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	
	private static final int port = 8000;
	private static ServerSocket serverSocket;
	private static Socket socket;
	private static BufferedReader br;
	private static PrintWriter pw;
	
	public static void main(String[] args) throws Exception {
		serverSocket = new ServerSocket(port);
		

		while(true){
			socket = serverSocket.accept();
			System.out.println("接收成功！");
			//发送消息
			write(socket);
			
			//读消息
			read(socket);
			
			System.out.println("执行结束");
			if(br!=null){
				br.close();
			}
			if(pw!=null){
				pw.close();
			}
		}
		
		
	}

	private static void write(Socket socket) throws Exception {
		pw = new PrintWriter(socket.getOutputStream(), true);
		new Thread(){

			@SuppressWarnings("resource")
			@Override
			public void run() {
				while(true){
					Scanner s = new Scanner(System.in);
					String sendMsg = s.nextLine();
					pw.println(sendMsg);
				}
			}
			
		}.start();
		
	}
	
	private static void read(Socket socket) throws Exception{
		br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String msg = "";
		while((msg=br.readLine())!=null){
			System.out.println(msg);
		}
	}


}
