/*
 * Copyright (c) 2008-2014 浩瀚深度 All Rights Reserved.
 *
 * FileName: AesEncrypt.java
 *
 * Description：
 *
 * History:
 * v1.0.0, szg, 2014-3-10, Create
 */
package com.haohan.msg.encrypt;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 类：AesEncrypt
 * @author szg
 * @version 
 */
public class AesEncrypt
{
    private static Logger logger = LoggerFactory.getLogger(AesEncrypt.class);
    /**
    * 加密
    * @param content 需要加密的内容
    * @param password 加密密码
    * @return
    */ 
    public static byte[] encrypt(String content, String password) 
    { 
        try
        {
            KeyGenerator kgen = KeyGenerator.getInstance("AES"); 
            kgen.init(128, new SecureRandom(password.getBytes())); 
            SecretKey secretKey = kgen.generateKey(); 
            byte[] enCodeFormat = secretKey.getEncoded(); 
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES"); 
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器 
            byte[] byteContent = content.getBytes("utf-8"); 
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化 
            byte[] result = cipher.doFinal(byteContent); 
            return result; // 加密 
        }
        catch (Exception e) 
        { 
            logger.error("encrypt:[" + e.getMessage() + "]");
        }
        return null; 
    } 
    
    /**解密
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
     */ 
    public static byte[] decrypt(byte[] content, String password) 
    { 
        try 
        {
             KeyGenerator kgen = KeyGenerator.getInstance("AES"); 
             kgen.init(128, new SecureRandom(password.getBytes())); 
             SecretKey secretKey = kgen.generateKey(); 
             byte[] enCodeFormat = secretKey.getEncoded(); 
             SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");             
             Cipher cipher = Cipher.getInstance("AES");// 创建密码器 
             cipher.init(Cipher.DECRYPT_MODE, key);// 初始化 
             byte[] result = cipher.doFinal(content); 
             return result; // 加密 
        } 
        catch (Exception e) 
        { 
            logger.error("decrypt:[" + e.getMessage() + "]");
        } 
        return null; 
    }
     
     
    /**将二进制转换成16进制
    * @param buf
    * @return
    */ 
    public static String parseByte2HexStr(byte buf[]) 
    { 
        StringBuffer sb = new StringBuffer(); 
        for (int i = 0; i < buf.length; i++) 
        { 
            String hex = Integer.toHexString(buf[i] & 0xFF); 
            if (hex.length() == 1) 
            { 
                hex = '0' + hex; 
            } 
            sb.append(hex.toUpperCase()); 
        } 
        return sb.toString(); 
    } 
      
    /**将16进制转换为二进制
    * @param hexStr
    * @return
    */ 
    public static byte[] parseHexStr2Byte(String hexStr) 
    { 
        if (hexStr.length() < 1) 
            return null; 
        byte[] result = new byte[hexStr.length()/2]; 
        for (int i = 0;i< hexStr.length()/2; i++) 
        { 
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16); 
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16); 
            result[i] = (byte) (high * 16 + low); 
        } 
        return result; 
    } 
       
    /**     
    * 加密
    * @param content 需要加密的内容
    * @param password  加密密码
    * @return
    */ 
    public static byte[] encrypt2(String content, String password) 
    { 
        try 
        {
            SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES"); 
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding"); 
            byte[] byteContent = content.getBytes("utf-8"); 
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化 
            byte[] result = cipher.doFinal(byteContent); 
            return result; // 加密 
        } 
        catch (Exception e) 
        { 
            logger.error("encrypt2:[" + e.getMessage() + "]");
        }
        return null; 
    } 
}
