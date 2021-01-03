package edu.khai.client2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUI extends JFrame {
	
	private Client client;
	
	private JButton send = new JButton("send");
	private JTextArea monitor = new JTextArea();
	private JTextField input = new JTextField();
	
	private JMenu ind = new JMenu();
	private JMenu connect = new JMenu("Отсоединиться");
	private JMenuBar menuBar = new JMenuBar();
	private JDialog connectDialog;
	
	public GUI(Client client) {		
		super("Chat");
		this.client = client;
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(dim.width/2, dim.height/2);
		setLocationRelativeTo(null);
		
		Container content = getContentPane();
		
		monitor.setLineWrap(true);
		monitor.setEditable(false);
		content.add(monitor,BorderLayout.CENTER);
		
		JPanel p = new JPanel(new BorderLayout());
		p.add(input,BorderLayout.CENTER);
		p.add(send,BorderLayout.EAST);
		content.add(p,BorderLayout.SOUTH);
		
		
		connect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//TODO 
			}
		});
		
		menuBar.add(connect);
		menuBar.add(ind);
		offIndicator();
		content.add(menuBar,BorderLayout.NORTH);
		
		//connectDialog = createDialog("Куда подсоединиться.", true);
		
		//this.setVisible(true);
	}
	public class ConnectDialog extends JDialog{
		public ConnectDialog(String title) {
			JDialog dialog = new JDialog(GUI.this, title, true);
	        JTextField ip = new JTextField();
	        JTextField port = new JTextField();
	        JButton ok = new JButton();
	        ok.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					client.Connect();
					
				}
			});
	        dialog.add();
	        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	        dialog.setLocationRelativeTo(null);
	        dialog.setSize(180, 90);
		}
		
	}
	
	public String getInputIP() {
		return 
	}
	public String getInputPort() {
		
	}
	public void setIndicator(String text) {
		this.ind.setForeground(Color.green);
		this.ind.setText(text);
	}
	public void offIndicator() {
		this.ind.setForeground(Color.red);
		this.ind.setText("Нет соединения...");
	}
}
