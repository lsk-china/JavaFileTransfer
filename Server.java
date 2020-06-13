import java.io.*;
import java.net.*;
import java.util.*;

public class Server{
	public static void main(String[] args) throws Exception{
		ServerSocket serverSocket = new ServerSocket(10004);
		while(true){
			Socket socket = serverSocket.accept();
			new Thread(new ServerThread(socket)).start();
		}
	}
}
class ServerThread implements Runnable{

	private Socket socket;

	public ServerThread(Socket socket){
		this.socket = socket;
	}
	@Override
	public void run(){
		String ip = socket.getInetAddress().getHostAddress();
		try{
			InputStream in = socket.getInputStream();
			File parentFile = new File("D://ftp/"+ip+"/");
			if(!parentFile.exists())
				parentFile.mkdir();
			Scanner scanner = new Scanner(System.in);
			System.out.println("FtpClient connected. IP Address: "+ip+"\r\nSave file as?");
			String fileName = "";
			while(true){
				try{
					fileName = scanner.next();
					break;
				}catch(RuntimeException rte){
					System.out.println("Please input a correct filename!");
					continue;
				}
			}
			File file = new File(parentFile,fileName);
			FileOutputStream fos = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len = 0;
			System.out.println("Start transfer file.");
			while((len = in.read(buf)) != -1){
				fos.write(buf,0,len);
			}
			System.out.println("Transfer done!");
			System.out.println("File at "+file.getPath());
			OutputStream os = socket.getOutputStream();
			os.write("Done.".getBytes());
			fos.close();
			socket.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
