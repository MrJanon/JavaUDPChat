package pack4;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.Scanner;

public class Server {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket server = null;
		Socket client = null;
		
		try {
			server = new ServerSocket(9999);
			System.out.println("�������������");
			client = server.accept();
			System.out.println("�����ͻ�����");
			
			InputStream is = client.getInputStream();
			OutputStream os = client.getOutputStream();
			
			while(true)
			{
				String message = "";
				int c = 0;
//				client.setSoTimeout(5000);//��Ӧʱ�������������
				System.out.println("�ȴ��ͻ��˷���ָ�");
				
				while((c=is.read())!=-1) {
					message += (char)c;
					if((char)c=='\n')
						break;
				}
				
				if(message.equals("Time\n")) {
					Date time = new Date();
					message = "��������ǰʱ��Ϊ��"+time;
					System.out.println(message);
					os.write((time.toString()+"\n").getBytes());
				}
				else if(message.equals("Exit\n")) {
					os.write("Bye\n".getBytes());
					System.out.println("���");
					break;
				}
				else
					os.write("��Чָ��\n".getBytes());
				
				
				os.flush();
			}
			
			os.flush();
			os.close();
			is.close();
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
