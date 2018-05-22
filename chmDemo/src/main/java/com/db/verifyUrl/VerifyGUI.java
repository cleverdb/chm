package com.db.verifyUrl;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerifyGUI {

	
	private static final int STATUS_CODE = 200;
	
	private static final String REGEX = "[a-zA-z]+://[^\\s]*";
	
	private final Frame frame;
	
	private final TextArea input;
	
	private final TextArea output;
	
	
	public VerifyGUI() {
		this.frame = creatFrame();
		this.input = createTextArea();
		this.output = createTextArea();
	}
	
	void init(){
		output.setEditable(false);
		frame.add(input);
		frame.add(output);
		frame.setVisible(true);
	}
	
	 Frame creatFrame(){
		Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
		Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
		int screenWidth = screenSize.width; //获取屏幕的宽
		int screenHeight = screenSize.height; //获取屏幕的高
		Frame frame = new Frame("verify url");
		frame.setLayout(new GridLayout(2, 1));
		frame.setIconImage(Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("").getPath()+"/logo.png"));
		frame.setMenuBar(createMenuBar());
		frame.setSize(500, 600);
		frame.setLocation(screenWidth/2 - 250, screenHeight/2 - 300);//设置窗口居中显示
		frame.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		return frame;
	}
	
	
	/**
	 * @return
	 */
	 TextArea createTextArea(){
		TextArea ta = new TextArea(10, 1);
		return ta;
	}
	 
	 /**
	 * @param label
	 * @param actionListener
	 * @return
	 */
	 MenuItem createMenuItem(String label, ActionListener actionListener){
		 MenuItem menu =  new  MenuItem(label);
		 menu.addActionListener(actionListener);
		 return menu;
	 }
	
	 /**
	 * @param label
	 * @return
	 */
	Menu createMenu(String label){
		 Menu menu = new Menu(label);
		 menu.add(createMenuItem("clear", new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("cl");
				input.setText(" ");
				output.setText(" ");
			}
		}));
		 menu.add(createMenuItem("verify" , new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					StringBuilder sb = textHandler(input.getText());
					output.setText(sb.toString());
				}
			}));
		 return menu;
	 }
	
	/**
	 * @return
	 */
	MenuBar createMenuBar(){
		MenuBar mb = new MenuBar();
		mb.add(createMenu("Menu"));
		return mb;
	}
	
	
	 /**
	 * @param inputText
	 * @return
	 */
	StringBuilder textHandler(String inputText){
		 StringBuilder sb = new StringBuilder();
		 String[] lines = inputText.split("\r\n");
		 Pattern pattern = Pattern.compile(REGEX);
		 for(String line : lines){
			 Matcher macther = pattern.matcher(line);
			 if(macther.find()){
				 System.out.println(line);
				 String url = macther.group();
				 if(checkUrl(url))
					 sb.append(line + "\r\n");
			 }else{
				 sb.append(line + "\r\n"); 
			 }
		 }
		 return sb;
	 }
	 
	 /**
	 * @param verifyUrl
	 * @return 404 :true others false 
	 */
	boolean  checkUrl(String verifyUrl){
		 boolean flag = false;
		 URL url = null;
		 HttpURLConnection uc = null;
		try {
			url = new URL(verifyUrl);
			uc = (HttpURLConnection)url.openConnection();
			int code =  uc.getResponseCode();
			//302: verifyUrl.equals(uc.getURL().toString()) 
			flag =  verifyUrl.equals(uc.getURL().toString()) && STATUS_CODE == code;
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(uc != null)
			   uc.disconnect();
		}
		return flag;
	 }
	 
	
	public static void main(String[] args) {
		new VerifyGUI().init();
		//new VerifyGUI().checkUrl("http://baishi.baidu.com/watch/0626905987279197518211.html");
	}
	
}
