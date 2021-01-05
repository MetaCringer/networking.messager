package edu.khai.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.Keymap;

public class GUI extends JFrame {
	
	JTextArea Jterminal = new JTextArea();
	JPanel JPanelInput = new JPanel(new BorderLayout());
	JButton JBsend = new JButton("Send");
	JTextField JFInput = new JTextField();
	DConnect authGUI;
	
	public GUI(){
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(dim.width/2, dim.height/2);
		setLocationRelativeTo(null);
		
		Jterminal.setLineWrap(true);
		Jterminal.setEditable(false);
		Jterminal.setAutoscrolls(true);
		
		JBsend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				send();
			}
		});
		
		JFInput.setFocusable(true);
		JFInput.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					send();
			}
		});
		
		Container content = this.getContentPane();
		
		JPanelInput.add(JFInput,BorderLayout.CENTER);
		JPanelInput.add(JBsend,BorderLayout.EAST);
		
		content.add(Jterminal,BorderLayout.CENTER);
		content.add(JPanelInput,BorderLayout.SOUTH);
		
		authGUI = new DConnect();
		
	}
	
	public void println(String s) {
		Jterminal.append(s);
		Jterminal.append("\n");
	}
	
	private void send() {
		String message = JFInput.getText();
		JFInput.setText("");
		Client.send(message);
		
	}
	
	public class DConnect extends JDialog{
		JLabel Lip=new JLabel("IP адресс"),Lport =new JLabel("Порт"),Lname = new JLabel("Имя"),Lerror=new JLabel();
		JTextField ip=new JTextField("localhost"),port=new JTextField("8001"), name=new JTextField("annonymous");
		JButton ok=new JButton("Connect");
		public DConnect() {
			super(GUI.this,true);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
			this.setSize(300, 200);
			setLocationRelativeTo(null);
			
			JPanel p = new JPanel();
			p.setLayout(new GridBagLayout());
			
			Lerror.setForeground(Color.RED);
			
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx=100;
			c.weighty=100;
			
			
			
			p.add(Lname,correct(c,0,0,1,1));
			p.add(name,correct(c,1,0,3,1));

			p.add(Lip,correct(c,0,1,1,1));
			p.add(ip,correct(c,1,1,3,1));
			
			p.add(Lport,correct(c,0,2,1,1));
			p.add(port,correct(c,1,2,3,1));

			p.add(Lerror,correct(c,0,3,1,1));
			
			p.add(ok,correct(c,2,3,1,1));
			
			
			ok.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						int p = Integer.parseInt(port.getText());
						if(!Client.connect(name.getText(), ip.getText(), p)) {
							Lerror.setText("Не удалось соединиться");
							return;
						}
						DConnect.this.setVisible(false);
						GUI.this.setVisible(true);
					} catch(NumberFormatException ex) {
						Lerror.setText("Не правильные порт или ip");
					}
					
					
				}
			});
			this.getContentPane().add(p);
			this.setVisible(true);
		}
		private GridBagConstraints correct(GridBagConstraints context, int x,int y,int wx,int wy) {
			context.gridx=x;
			context.gridy=y;
			context.gridwidth=wx;
			context.gridheight=wy;
			return context;
		}
	}
}
