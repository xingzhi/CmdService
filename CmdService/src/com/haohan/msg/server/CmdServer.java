/*
 *
 * FileName: HrsccServer.java
 *
 * Description：
 *
 * History:
 * v1.0.0, lds, 2014-3-7, Create
 */
package com.haohan.msg.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;
import org.jboss.netty.handler.execution.ExecutionHandler;
import org.jboss.netty.handler.execution.OrderedMemoryAwareThreadPoolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haohan.msg.biz.CmdHanddlerBiz;
import com.haohan.msg.constant.CmdConstant;
import com.haohan.msg.server.handdler.ServerHandler;


/**
 * 类：HrsccServer
 * @author szg
 * @version 
 */
public class CmdServer extends Thread
{
    private static Logger logger = LoggerFactory.getLogger(CmdServer.class);
    
    private CmdHanddlerBiz msgHanddleBiz;
    
    private int port = 0;
    
    /**
     * @param port
     */
    public CmdServer(int port, CmdHanddlerBiz msgHanddleBiz)
    {
        this.port = port;
        this.msgHanddleBiz = msgHanddleBiz;
    }

    @Override
    public void run()
    {
        bind(this.port);
    }

    public void bind(int port)
    {
        ChannelFactory factory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool()
                ,Executors.newCachedThreadPool());
        ServerBootstrap bootstrap = new ServerBootstrap(factory);
        bootstrap.setPipelineFactory(new ChannelPipelineFactory()
        {
            public ChannelPipeline getPipeline()
            {
                ChannelPipeline pipeline = null;
                if (CmdConstant.ISBEGINLENGTH)
                {
                    pipeline = Channels.pipeline(
                            new ExecutionHandler(new OrderedMemoryAwareThreadPoolExecutor(16, 1048576, 1048576)),
                            new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4),
                            new ServerHandler(msgHanddleBiz));
                }
                else
                {
                    pipeline = Channels.pipeline(
                            new ExecutionHandler(new OrderedMemoryAwareThreadPoolExecutor(16, 1048576, 1048576)),
                            new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, true,
                                    true, ChannelBuffers.copiedBuffer(CmdConstant.ENDFLAG.getBytes())),
                            new ServerHandler(msgHanddleBiz));
                }
                return pipeline;
            }
        });
        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);
        bootstrap.bind(new InetSocketAddress(port));
        logger.info("bind:[OK]");
    }

    /**
     * @return the port
     */
    public int getPort()
    {
        return port;
    }
}
