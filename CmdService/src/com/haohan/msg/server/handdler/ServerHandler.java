/*
 *
 * FileName: HrsccServerHandler.java
 *
 * Description：
 *
 * History:
 * v1.0.0, lds, 2014-3-7, Create
 */
package com.haohan.msg.server.handdler;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haohan.msg.biz.CmdHanddlerBiz;
import com.haohan.msg.constant.CmdConstant;
import com.haohan.msg.encrypt.AesEncrypt;

/**
 * 类：ServerHandler
 * @author szg
 * @version 
 */
public class ServerHandler extends SimpleChannelUpstreamHandler
{
    private static Logger logger = LoggerFactory.getLogger(ServerHandler.class);
    
    private CmdHanddlerBiz cmdHanddleBiz;
    
    public ServerHandler(CmdHanddlerBiz msgHanddleBiz)
    {
        this.cmdHanddleBiz = msgHanddleBiz;
    }
    
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception
    {
        ChannelBuffer buf = (ChannelBuffer) e.getMessage();
        byte[] msgTemp = buf.array();
        logger.info("receive:[" + new String(msgTemp) + "]");
        String message;
        if (CmdConstant.ISDECRYPT)
        {
            //需要进行解密处理
            byte[] decryptMessage = AesEncrypt.decrypt(msgTemp,CmdConstant.DECRYPTPWD);
            message = new String(decryptMessage);
        }
        else
        {
            message = new String(msgTemp);
        }
        Channel ch = e.getChannel();
        if (message != null && !"".equals(message))
        {
            logger.info("receive&manage:[" + message + "]");
            String backStr = "";
            if (!cmdHanddleBiz.isCmdStandard(message))
            {
                backStr = CmdConstant.WRONGRN;
            }
            else
            {
                if (cmdHanddleBiz.processCmd(message))
                {
                    backStr = CmdConstant.RIGHTRN;    
                }
                else
                {
                    backStr = CmdConstant.WRONGRN;
                }
            }
            //短连接作为server端可断开连接
            ChannelFuture f = ch.write(ChannelBuffers.copiedBuffer(backStr.getBytes()));
            f.addListener(new ChannelFutureListener()
            {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception
                {
                    Channel ch = future.getChannel();
                    ch.close();
                }
            });
        }
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception
    {
        logger.error("exceptionCaught:[" + e.getCause() + "]");
        Channel ch = e.getChannel();
        ch.close();
    }
}
