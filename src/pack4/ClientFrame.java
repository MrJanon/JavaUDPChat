package pack4;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientFrame extends JFrame implements ActionListener, Runnable {

	private DatagramSocket client;
	private DatagramPacket sendpacket, getpacket;
	private JButton send, clear, exit;
	private JTextField sendtext, mine;
	private JTextArea chattext;
	private InetAddress host;
	private String name;
	private int state;
	
	public ClientFrame() {
		// TODO Auto-generated constructor stub
		this.setTitle("Client1");
		try {
			client = new DatagramSocket(9999);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Thread(this).start();
		state = 0;
		send = new JButton("·¢ËÍ");
		clear = new JButton("Çå¿Õ");
		exit = new JButton("ÍË³ö");
		send.addActionListener(this);
		clear.addActionListener(this);
		exit.addActionListener(this);
		try {
			host = InetAddress.getLocalHost();
			name = host.toString().split("/")[1];
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sendtext = new JTextField(20);
		mine = new JTextField(name);
		mine.setEditable(false);
		chattext = new JTextArea(15, 25);
		chattext.setEditable(false);
		
		Container pane = this.getContentPane();
		JPanel temp = new JPanel();
		temp.setLayout(new FlowLayout());
		temp.add(mine);
		temp.add(sendtext);
		temp.add(send);
		temp.add(clear);
		pane.add(exit, BorderLayout.NORTH);
		pane.add(chattext, BorderLayout.CENTER);
		pane.add(temp, BorderLayout.SOUTH);
		
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if(send == ae.getSource()) {
			StringBuffer contain = new StringBuffer(name+":"+sendtext.getText()+"\n");
System.out.println(contain);
			try {
				sendpacket = new DatagramPacket(contain.toString().getBytes(), contain.toString().length(), InetAddress.getByName("127.0.0.1"), 9998);
				client.send(sendpacket);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(clear == ae.getSource()) {
			sendtext.setText("");
		}
		else if(exit == ae.getSource()) {
			client.close();
			state = 1;
			this.dispose();
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			if(state == 1)
				break;
			byte data[] = new byte[1024];
			getpacket = new DatagramPacket(data, data.length);
			try {
				client.receive(getpacket);
				chattext.append(new String(data));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				break;
			}
		}
	}

}
