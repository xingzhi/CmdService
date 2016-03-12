/*
 *
 * FileName: MsgHanddleBiz.java
 *
 * Description：
 *
 * History:
 * v1.0.0, lds, 2014-8-25, Create
 */
package com.haohan.msg.biz;

/**
 * 
 * @author lds
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public interface CmdHanddlerBiz
{
    public boolean processCmd(String msg);
    
    public boolean isCmdStandard(String message);
    
}
