import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class UDPServer {
	public static void main(String[] args) throws Exception{
		DatagramSocket ds = new DatagramSocket(3000);
		String str = "Hi, I'm server.";
		DatagramPacket dp = new DatagramPacket(str.getBytes(), str.length(),
				InetAddress.getByName("localhost"), 9000);
		ds.send(dp);
		ds.close();
	}
}
