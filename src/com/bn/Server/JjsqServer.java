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
	
    private int port;//�������˿ں�

    public JjsqServer(int port) 
    {
        this.port = port;
    }

    public void doTask() throws Exception 
    {    	
    	//�������ڽ�����������Ķ��߳��¼���Ϣѭ���飬����accept�¼�
        EventLoopGroup bossGroup = new NioEventLoopGroup(); 
        //��������ִ�о���ҵ���߼��ĵĶ��߳��¼���Ϣѭ���飬�����Ѿ����������ӵ�io 
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
        	//ServerBootstrap�����ʼ��netty�����������ҿ�ʼ�����˿ڵ�socket����
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)//����ʱ��ѭ������
             .channel(NioServerSocketChannel.class)//������������accept�����ӣ����ڹ���serversocketchannel�Ĺ����� 
             .childHandler
             (
            	 new ChannelInitializer<SocketChannel>() 
	             { 
	                 @Override//��������accept��ʱ��������������
	                 public void initChannel(SocketChannel ch) throws Exception 
	                 {
	                	 //���÷���ʱ�ı�������4��ʾ����ռ�ĸ��ֽ�
	                	 ch.pipeline().addLast(new LengthFieldPrepender(4, false));
	                	 //����ʱ�Ľ���������������Ϊ�� ÿ֡����ֽ����������ֽ�ƫ�����������ֽ��������ȵ���ֵ�����������ֽ�ƫ����
	                	 ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024*1024,0,4,0,4));
	                	 //�Զ���Ĵ�������Ϊ��ǰ��channel��pipeline����Զ���Ĵ����� 
	                     ch.pipeline().addLast(new JjsqServerHandler());
	                 }
	             }
             )
             //����tcp��������BACKLOG����Ϊ128
             .option(ChannelOption.SO_BACKLOG, 128)          
             .childOption(ChannelOption.SO_KEEPALIVE, true);
            
            
            /*
             * ServerBootstrap.bind(int)����󶨶˿ڣ�
             * ���������ִ�к�ServerBootstrap�Ϳ��Խ���ָ���˿��ϵ�socket�����ˡ�
             * һ��ServerBootstrap���԰󶨶���˿ڡ�
             * bind�����ᴴ��һ��serverchannel�����һὫ��ǰ��channelע�ᵽeventloop���棬  
             * ��Ϊ��󶨱��ض˿ڣ���������г�ʼ����Ϊ���pipeline��һЩĬ�ϵ�handler  
             */
            //�󶨶˿ڣ�������sync���������ȴ��󶨹�������
            ChannelFuture f = b.bind(port).sync(); 
            System.out.println("Listen to "+port+"	�����������ɹ�......");
            //����sync�������̣߳���֤����˹رպ�main�������˳�
            f.channel().closeFuture().sync();       
        } 
        finally 
        {
        	//���ŵعر��߳���
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}
