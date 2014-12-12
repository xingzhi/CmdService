/*
 * Copyright (c) 2008-2014 浩瀚深度 All Rights Reserved.
 *
 * FileName: HrsccConstant.java
 *
 * Description：
 *
 * History:
 * v1.0.0, szg, 2014-3-7, Create
 */
package com.haohan.msg.constant;

import java.io.File;

/**
 * 类：CmdConstant
 * @author lds
 * @version 
 */
public class CmdConstant
{
    //配置文件目录
    public static final String PATH_CONFIG = "hrscc-config.xml";
    //路径分割符
    public static final String PATH_SEPARATOR = File.separator;
    /****************************主配置****************************/
    //file下载时上级IP
    public static String HIGHERIP = "127.0.0.1";
    //file下载时上级用户名
    public static String HIGHERUSER = "root";
    //Hrscc目前是否为最下级
    public static boolean ISLASTLEVEL = true;
    //Server端监听端口
    public static int PORT = 0;
    //Debug监听端口
    public static int DEBUGPORT = 0;
    //接收正确返回信息
    public static String RIGHTRN = "ok\r\n";
    //接收错误返回信息
    public static String WRONGRN = "error\r\n";
    //是否启用接收端数据移位
    public static boolean ISBEGINLENGTH = false;
    //判断一条命令结束的标志位
    public static String ENDFLAG = "\r\n";
    //是否进行加密下发
    public static boolean ISENCRYPT = false;
    //加密密码
    public static String ENCRYPTPWD = "haohandata";
    //是否进行解密处理
    public static boolean ISDECRYPT = false;
    //解密密码
    public static String DECRYPTPWD = "haohandata";
    
}
