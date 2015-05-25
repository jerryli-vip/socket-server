package com.sanc.app.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TalkServer {

	public static void main(String[] args) {

		ServerSocket server = null;
		Socket socket = null;
		try {
			// 1. server
			// 创建一个ServerSocket在端口 8001 监听客户请求
			server = new ServerSocket(8001);
			System.out.println("server created on port 8001");

			// 2. socket
			// 使用 accept() 阻塞等待客户请求，有客户
			// 请求到来则产生一个 Socket 对象，并继续执行
			socket = server.accept();
			System.out.println("socket created");

			// 3. system input stream
			// 由系统标准输入设备构造 BufferedReader 对象
			BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("sin created");

			// 4. socket input/output stream
			// 由 Socket 对象得到输入流，并构造相应的 BufferedReader 对象
			BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// 由 Socket 对象得到输出流，并构造 PrintWriter 对象
			PrintWriter os = new PrintWriter(socket.getOutputStream());
			System.out.println("is and os created");

			String readLine = is.readLine();
			if (readLine != null && !readLine.equalsIgnoreCase("bye")) {
				// 在标准输出上打印从客户端读入的字符串
				System.out.println("Client : " + readLine);

				// 从标准输入读入一字符串
				String line = sin.readLine();

				// 如果该字符串为 "bye"，则停止循环
				while (!line.equalsIgnoreCase("bye")) {
					// 向客户端输出该字符串
					os.println(line);
					// 刷新输出流，使 Client 马上收到该字符串
					os.flush();

					// 在系统标准输出上打印读入的字符串
					System.out.println("Server : " + line);
					// 从 Client 读入一字符串，并打印到标准输出上

					String clientLine = is.readLine();
					if (clientLine == null || clientLine.equalsIgnoreCase("bye")) {
						break;
					}
					System.out.println("Clinet : " + clientLine);

					// 从系统标准输入读入一字符串
					line = sin.readLine();
				}
			} else {
				System.out.println("Client : " + readLine);
			}
			os.close();
			is.close();
			sin.close();
			socket.close();
			server.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
