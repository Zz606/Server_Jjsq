package com.bn.Server;

import com.bn.Server.JjsqServerHandler;

import io.netty.bootstrap.*;
import io.netty.channel.*;
import io.netty.channel.nio.*;
import io.netty.channel.socket.*;
import io.netty.channel.socket.nio.*;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

public class JjsqServer {
	
    private int port;//服务器端口号

    public JjsqServer(int port) 
    {
        this.port = port;
    }

    public void doTask() throws Exception 
    {    	
    	//创建用于接收连接请求的多线程事件消息循环组，处理accept事件
        EventLoopGroup bossGroup = new NioEventLoopGroup(); 
        //创建用于执行具体业务逻辑的的多线程事件消息循环组，处理已经建立的连接的io 
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
        	//ServerBootstrap负责初始化netty服务器，并且开始监听端口的socket请求。
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)//设置时间循环对象，
             .channel(NioServerSocketChannel.class)//用它来建立新accept的连接，用于构造serversocketchannel的工厂类 
             .childHandler
             (
            	 new ChannelInitializer<SocketChannel>() 
	             { 
	                 @Override//当新连接accept的时候，这个方法会调用
	                 public void initChannel(SocketChannel ch) throws Exception 
	                 {
	                	 //设置发送时的编码器，4表示长度占四个字节
	                	 ch.pipeline().addLast(new LengthFieldPrepender(4, false));
	                	 //接收时的解码器，参数依次为： 每帧最大字节数、长度字节偏移量、长度字节数、长度调整值、返回数据字节偏移量
	                	 ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024*1024,0,4,0,4));
	                	 //自定义的处理器，为当前的channel的pipeline添加自定义的处理函数 
	                     ch.pipeline().addLast(new JjsqServerHandler());
	                 }
	             }
             )
             //配置tcp参数，将BACKLOG设置为128
             .option(ChannelOption.SO_BACKLOG, 128)          
             .childOption(ChannelOption.SO_KEEPALIVE, true);
            
            
            /*
             * ServerBootstrap.bind(int)负责绑定端口，
             * 当这个方法执行后，ServerBootstrap就可以接受指定端口上的socket连接了。
             * 一个ServerBootstrap可以绑定多个端口。
             * bind方法会创建一个serverchannel，并且会将当前的channel注册到eventloop上面，  
             * 会为其绑定本地端口，并对其进行初始化，为其的pipeline加一些默认的handler  
             */
            //绑定端口，并调用sync方法阻塞等待绑定工作结束
            ChannelFuture f = b.bind(port).sync(); 
            System.out.println("Listen to "+port+"	服务器启动成功......");
            //调用sync阻塞主线程，保证服务端关闭后main方法才退出
            f.channel().closeFuture().sync();       
        } 
        finally 
        {
        	//优雅地关闭线程组
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}
