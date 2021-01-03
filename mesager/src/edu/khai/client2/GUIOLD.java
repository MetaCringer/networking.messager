package edu.khai.client2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class GUIOLD extends JFrame {
	private static GUIOLD console;
	public static GUIOLD getConsole() {return console;}
	public static void SetConsole(GUIOLD c) {console = c;}
	private static int sizex=600,sizey=400;
	private JButton send = new JButton("send");
	private JTextArea monitor = new JTextArea();
	private JTextField input = new JTextField();
	private int indexHistory = 0;
	private LinkedList<String> historyMessage = new LinkedList<String>();
	
	private JScrollPane scroll;
	
	public GUIOLD() {
		super("Chat");		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(sizex,sizey);
		Container content = this.getContentPane();
		content.setLayout(new BorderLayout());
		send.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {InputFromConsole();}
		});
		input.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_ENTER:
					InputFromConsole();
					break;
				case KeyEvent.VK_UP:
					prev(1);
					break;
				case KeyEvent.VK_DOWN:
					prev(-1);
					break;
				default:
					break;
				}
			}
		});
		this.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {}
			@Override
			public void windowIconified(WindowEvent e) {}
			@Override
			public void windowDeiconified(WindowEvent e) {}
			@Override
			public void windowDeactivated(WindowEvent e) {}
			@Override
			public void windowClosing(WindowEvent e) {Close();}
			@Override
			public void windowClosed(WindowEvent e) {}
			@Override
			public void windowActivated(WindowEvent e) {}
		});
		send.setSize(50,20);
		monitor.setLineWrap(true);
		JPanel p = new JPanel(new BorderLayout(2, 2));
		p.add(input,BorderLayout.CENTER);
		p.add(send,BorderLayout.EAST);
		content.add(scroll = new JScrollPane(monitor,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),BorderLayout.CENTER);
		content.add(p,BorderLayout.SOUTH);
		
		Color background = new Color(0,0,0);
		Color foreground = new Color(128,192,128);
		send.setBackground(background);
		send.setForeground(foreground);
		input.setBackground(background);
		input.setForeground(foreground);
		this.setBackground(background);
		this.setForeground(foreground);
		content.setBackground(background);
		content.setForeground(foreground);
		monitor.setEditable(false);
		monitor.setBackground(background);
		monitor.setForeground(foreground);
		this.setContentPane(content);
		this.setVisible(true);
		historyMessage.add("");
	}
	
	private void prev(int offset) {
		input.setText(historyMessage
				.get(indexHistory = 
				Math.abs(indexHistory + offset) % historyMessage.size() ) );
	}
	
	public void InputFromConsole(){
		
		String text = input.getText();
		if(text == null || text.equals("")) {return;}
		indexHistory = 0;
		historyMessage.addFirst(text);
		//cmd.InputFromConsole(text);//TODO отказоустойчивая отправка на сервер
		input.setText("");
	}
	
	public void clear() {
		monitor.setText("");
	}
	public void write(String text) {
		monitor.append(text);
		scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
	}
	public void println(String text) {
		monitor.append(text + "\n");
		scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
	}
	public void Close() {//todo
		
	}
	
}
