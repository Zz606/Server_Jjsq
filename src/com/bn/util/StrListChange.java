package com.bn.util;

import java.util.ArrayList;
import java.util.List;

public class StrListChange
{
	//将字符串转换成List
	public static List<String[]> StrToList(String info)
	{
		List<String[]> list=new ArrayList<String[]>();         //创建一个List列表
		String[] s=info.split("\\|");                          //将字符串以"|"为界分割开
		int num=0;                                             //定义大小常量
		for(String ss:s)                                       //遍历数组
		{
			num=0;                                             //计数器
			String[] temp=ss.split("<#>");                     //将字符串以"<#>"为界分割开
			String[] midd=new String[temp.length];             //创建临时数组
			for(String a:temp)                                 //遍历数组
			{
				midd[num++]=a;	
			}
			list.add(midd);                                    //将字符串加入列表
		}
		return list;                                           //返回列表
	}
	
	//将字符串转换成字符数组
	public static String[] StrToArray(String info)
	{
		int num=0;                                              //定义大小常量
		String[] first=info.split("\\|");                       //将字符串以"|"为界分割开
		for(int i=0;i<first.length;i++)                         //遍历字符串数组
		{
			String[] temp1=first[i].split("<#>");               //将字符串以"<#>"为界分割开
			num+=temp1.length;
		}
		String[] temp2=new String[num];                         //创建临时数组
		num=0;                                                  //清零
		for(String second:first)                                //遍历数组
		{
			String[] temp3=second.split("<#>");                 //将字符串以"<#>"为界分割开
			for(String third:temp3)                             //遍历数组
			{
				temp2[num]=third;                               //给临时数组赋值
				num++;
			}
		}
		return temp2;                                            //返回数组
	}
	
	//将List转换成字符串
	public static String ListToStr(List<String[]> list)
	{ 
		String mess="";                                          //声明常量
		List<String[]> ls=new ArrayList<String[]>();             //创建list列表
		ls=list;                                          
		for(int i=0;i<ls.size();i++)                             //遍历列表
		{
			String[] ss=ls.get(i);
			for(String s:ss)                                     //便利数组
			{
				mess+=s+"<#>";                                   //将字符串以"<#>"为界分割开
		    }
			mess+="|";                                           //将字符串以"|"为界分割开
		}
		return mess;                                             //返回字符串
	}
	
	//将List转换成字符串 （安卓端）
	public static String ListToStrAndroid(List<String[]> list)
	{
		if(list.isEmpty())
		{
			return "";
		}
		String mess="";
		List<String[]> ls=new ArrayList<String[]>();
		ls=list;
		for(int i=0;i<ls.size();i++)
		{
			String[] ss=ls.get(i);
			for(String s:ss)
			{
				mess+=s+"<#>";
		    }
			mess=mess.substring(0,mess.length()-3);
			mess+="|";
		}
		return mess.substring(0,mess.length()-1);
	}
	
	//排除相同的内容 返回字符串
	public static String Streamline(String mess)
	{
		String[] str=mess.split("<#>");
		String info="";
		for(int i=0;i<str.length-1;i++)
		{
			String temp=str[i];
			String temp2=str[i+1];
			if(!temp.equals(temp2))
			{
				info+=temp+"<#>";
			}
			else
			{
				continue;
			}
		}
		return info+str[str.length-1];
	}
	
	//字符数组改成字符串
	public static String ArrayToStrAndroid(String[] arry)
	{
		String str="";
		for(String ss:arry)
		{
			if(ss!=null)
			{
				str+=ss+"|";
			}
		}
		return str;
	}
}
