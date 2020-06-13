import java.io.*;
import java.net.*;

public class Client{
	public static void main(String[] args) throws Exception{
		Socket socket = new Socket(args[0],10004);
		OutputStream os = socket.getOutputStream();
		FileInputStream fis = new FileInputStream(args[1]);
		byte[] buf = new byte[1024];
		int len = 0;
		while((len = fis.read(buf)) != -1){
			os.write(buf,0,len);
		}
		socket.shutdownOutput();
		InputStream is = socket.getInputStream();
		byte[] buf2 = new byte[1024];
		int num = is.read(buf2);
		System.out.println(new String(buf2,0,num));
		fis.close();
		socket.close();
	}
}
