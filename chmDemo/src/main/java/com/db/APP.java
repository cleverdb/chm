package com.db;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

public class APP {

	 private final static List<String> list = new ArrayList<>();
	public static void main(String[] args) {
		Frame frame = new Frame();
		frame.setSize(500,500);
		frame.setTitle("ShowFlowLayout");
		frame.setVisible(false);
		frame.setResizable(false);
		final Object[] columnNames = {"姓名", "性别", "家庭地址",//列名最好用final修饰
				   "电话号码", "生日", "工作", "收入", "婚姻状况","恋爱状况"};
				  Object[][] rowData = {
				     {"ddd", "男", "江苏南京", "1378313210", "03/24/1985", "学生", "寄生中", "未婚", "没"},
				     {"eee", "女", "江苏南京", "13645181705", "xx/xx/1985", "家教", "未知", "未婚", "好象没"},
				     {"fff", "男", "江苏南京", "13585331486", "12/08/1985", "汽车推销员", "不确定", "未婚", "有"},
				     {"ggg", "女", "江苏南京", "81513779", "xx/xx/1986", "宾馆服务员", "确定但未知", "未婚", "有"},
				     {"hhh", "男", "江苏南京", "13651545936", "xx/xx/1985", "学生", "流放中", "未婚", "无数次分手后没有"}
				    };
		JTable table = new JTable(rowData,columnNames);
		
		JScrollPane scrollPane = new JScrollPane (table);
		
		JPanel panel = new JPanel();
		
		panel.add(scrollPane);
		panel.setBackground(Color.blue);
		
		JTextField field = new JTextField();
		field.setText("----------------");
		field.setBounds(0,0,300,50);
		
		JButton button = new JButton("ok");
		  button.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	List<String> temp = Arrays.asList(field.getText().split("#"));
	            	for(int i = 0 ; i<temp.size() ; i++ ){
	            		combinerSelect(temp, list, 5, i);
	            	}
	            }
	        });
		frame.add(panel);
		panel.add(field);
		panel.add(button);
		frame.show();
	}
	
	private static void DB(){
		
	}
	
	 private static  void combinerSelect(List<String> data, List<String> workSpace, int n, int k) {  
	        List<String> copyData;  
	        List<String> copyWorkSpace;  
	          
	        if(workSpace.size() == k) {  
	            for(Object c : workSpace)  
	                System.out.print(c);  
	            System.out.println();  
	        }  
	          
	        for(int i = 0; i < data.size(); i++) {  
	            copyData = new ArrayList<String>(data);  
	            copyWorkSpace = new ArrayList<String>(workSpace);  
	              
	            copyWorkSpace.add(copyData.get(i));  
	            for(int j = i; j >=  0; j--)  
	                copyData.remove(j);  
	            combinerSelect(copyData, copyWorkSpace, n, k);  
	        }  
	          
	    }  
	
}
