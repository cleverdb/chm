package com.db;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author Bo.Dong
 *
 */
public class ChmParser {
	
	private String chmPath;
	
	private String htmlPath;
	
	//private Map<String, String[]> result =new HashMap<>();
	public List<String[]> list = new ArrayList<>();
	
	public ChmParser(String chmPath, String htmlPath) {
		super();
		this.chmPath = chmPath;
		this.htmlPath = htmlPath;
	}

	/**
	 * chm转换为html利用window命令:hh -decompile  [htmlPath] [chmPath]
	 */
	private void chm2html(){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("hh -decompile ");
			sb.append(this.htmlPath);
			sb.append(" ");
			sb.append(this.chmPath);
			System.out.println("exec"+sb);
			Runtime.getRuntime().exec(sb.toString()).waitFor();
		} catch (InterruptedException | IOException e) {
			System.out.println("fail : chm to html");
			e.printStackTrace();
		}
	}
	
	/**
	 * 提取数据
	 * @param file
	 * @return
	 */
	private String[] parserHtml(File file){
		String[] str = new String[5];
		int i =0;
		try {
			Document doc = Jsoup.parse(file, "utf-8");
			//获取自己需要的<p>标签
			Elements eles = doc.getElementsByTag("p");
			while(i<5){
				str[i] = eles.get((i*2)+1).text();
				i++;
			}
		} catch (IOException e) {
			System.out.println("parser html error");
			e.printStackTrace();
		}
		return str;
	} 

	/**
	 * 递归遍历文件
	 * @param file
	 */
	private void parserFile(File file){
		File[] files = file.listFiles();
		for(File f : files){
			if(f.isDirectory()){
				parserFile(f);
			}else{
				//过滤节点下自动生成的index.html
				if(f.getName().equals("index.html")) return;
				String[] e =parserHtml(f);
				if(f.getName().equals("611D0200.html")) System.out.println(f.getAbsolutePath());
				list.add(e);
				//result.put(f.getName(),e);
			}
		}
	
	}

	/**
	 * 
	 */
	public void start(){
		this.chm2html();
		this.parserFile(new File(this.htmlPath));
	}
	
	public static void main(String[] args)  {
		
	}
}
