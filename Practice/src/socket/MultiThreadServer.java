package socket;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class MultiThreadServer{
		private int port = 4600;
		private ServerSocket serverSocket;
		private ExecutorService executorService;
		private final int POOL_SIZE = 10;
		
		public MultiThreadServer()  throws IOException{
			serverSocket = new ServerSocket(port);
			
			executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * POOL_SIZE);
			
			System.out.println("����������");
		}
		
		public void service(){
			while(true){
				Socket socket = null;
				try{
					socket = serverSocket.accept();
					executorService.execute(new Handler(socket));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		
		public static void main(String[] args) throws IOException{
			new MultiThreadServer().service();
		}
}

class Handler implements Runnable{
	private Socket socket;
	public Handler(Socket socket){
		this.socket = socket;
	}
	
	private PrintWriter getWriter(Socket socket) throws IOException{
		OutputStream socketOut = socket.getOutputStream();
		return new PrintWriter(socketOut, true);
	}
	
	private BufferedReader getReader(Socket socket) throws IOException{
		InputStream socketIn = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketIn));
	}
	
	public String echo(String msg){
		return "echo:" + msg;
	}
	
	public void run(){
		try{
			System.out.println("New connection accepted " + socket.getInetAddress() + ":" + socket.getPort());
			BufferedReader br = getReader(socket);
			PrintWriter pw = getWriter(socket);
			String msg = null;
			while((msg = br.readLine()) != null){
				System.out.println(msg);
				pw.println(echo(msg));
				if(msg.equals("bye")) break;
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		finally{
			try{
				if(socket != null)
					socket.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
}