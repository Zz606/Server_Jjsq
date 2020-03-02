package com.bn.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtil 
{
	//获取单张图片
	public static byte[] getImage(String path){								
		byte[] data=null;//声明图片比特数组								
		try{
			//根据路径创建输入流
			//提供缓冲功能，提高输入输出的效率。
			BufferedInputStream in=new BufferedInputStream(new FileInputStream(path));
			//创建一个新的缓冲输出流，指定缓冲区大小为1024Byte
			ByteArrayOutputStream out=new ByteArrayOutputStream(1024);
			byte[] temp=new byte[1024];	//创建大小为1024的比特数组
			int size=0;						        //定义大小常量
			//从输入流读取一定数量的的字节放入字节数组temp
			//size 是实际读取的字节数
			while((size=in.read(temp))!=-1)//若有内容读出，写入比特数组
			{	
				out.write(temp,0,size);//图片比特数组写入输出流，0是偏移量，size是字节个数
			}
			data=out.toByteArray();	//将图片信息以比特数组形式读出并赋值给图片比特数组
			out.close();  //关闭输出流
			in.close();     //关闭输入流
		}
		catch(Exception e){
			e.printStackTrace();                                  //打印异常信息
		}
		return data; 		                                      //返回比特数组
	}
	//保存图片
	public static void savePic(byte[] data,String path) throws IOException{
		File file= new File(path);                                   //创建文件
		FileOutputStream fos = new FileOutputStream(file);           //将File实例放入输出流
		fos.write(data);                                             //将实例数据写入文件流
		fos.flush();                                                 //清空缓冲区数据
		fos.close();                                                 //关闭文件流
	}
}
