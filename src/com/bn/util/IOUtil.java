package com.bn.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

public class IOUtil 
{
	//��Ϣ��ǰ�ĸ��ֽ�Ϊ�����ֽ���
	//��3���ֽ�Ϊ���ͱ��   0--�ַ���   1-����	
	
    public static void writeString(ChannelHandlerContext ctx,String msg,ChannelFutureListener finishAction)
    {
    	byte[] typeBytes=ConvertUtil.fromIntToBytes(0);
    	byte[] data=ConvertUtil.fromStringToBytes(msg); 
    	int length=data.length;	
    	
    	final ByteBuf buf = ctx.alloc().buffer(4+length); 
        buf.writeBytes(typeBytes);
        buf.writeBytes(data);
        
        final ChannelFuture f = ctx.writeAndFlush(buf); 
        f.addListener(finishAction); 
    }
      
    //���˷��ͱ�������
    public static void writeByte(ChannelHandlerContext ctx,byte[] msg,ChannelFutureListener finishAction)
    {
    	byte[] typeBytes=ConvertUtil.fromIntToBytes(1);
    	int length=msg.length;
    	final ByteBuf buf = ctx.alloc().buffer(4+length); 
        buf.writeBytes(typeBytes);
        buf.writeBytes(msg);
        final ChannelFuture f = ctx.writeAndFlush(buf); 
        f.addListener(finishAction);
    }
    
    public static void writeString(Channel c,String msg,ChannelFutureListener finishAction)
    {
    	byte[] typeBytes=ConvertUtil.fromIntToBytes(0);
    	byte[] data=ConvertUtil.fromStringToBytes(msg); 
    	int length=data.length;	
    	
    	final ByteBuf buf = c.alloc().buffer(4+length); 
        buf.writeBytes(typeBytes);
        buf.writeBytes(data);
        
        final ChannelFuture f = c.writeAndFlush(buf); 
        f.addListener(finishAction); 
    }
    
    public static void writeInt(ChannelHandlerContext ctx,int dataInt,ChannelFutureListener finishAction)
    {  	
    	byte[] typeBytes=ConvertUtil.fromIntToBytes(1);
    	byte[] data=ConvertUtil.fromIntToBytes(dataInt);    	
    	
    	final ByteBuf buf = ctx.alloc().buffer(4+4); 
        buf.writeBytes(typeBytes);
        buf.writeBytes(data);
        
        final ChannelFuture f = ctx.writeAndFlush(buf); 
        f.addListener(finishAction); 
    }
    
    public static byte[] readBytes(Object msg)
    {
    	ByteBuf buf = (ByteBuf) msg;    	
    	int count=buf.readableBytes();
    	byte[] data=new byte[count];
    	buf.readBytes(data);    	
    	return data;
    }
}
