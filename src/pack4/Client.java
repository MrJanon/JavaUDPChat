package pack4;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Socket client = null;
		
		try {
			client = new Socket("127.0.0.1", 9999);
			
			InputStream is = client.getInputStream();
			OutputStream os = client.getOutputStream();
			Scanner reader = new Scanner(System.in);
			
			while(true)
			{
				System.out.println("请输入指令：");
				String message = reader.nextLine()+"\n";
				os.write(message.getBytes());
				System.out.println("发送请求,等待响应...");
//				client.shutdownOutput();//写入结束
				
				int c = 0;
				while((c = is.read())!=-1) {
					System.out.print((char)c);
					if((char)c == '\n')
						break;
				}
					
				
				System.out.println();
				
				if(message.equals("Exit\n"))
					break;
			}
			
			reader.close();
			System.out.println("\n完毕");
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
