package com.puhui.email.util;


import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 邹玉玺
 * @date: 2020/7/7-10:59
 */
public class EncodeUtil {

    //保存公钥私钥的文件名
    private static String pathName = "D:\\key.txt";

    /**
     * 创建一个文件，用于存储加密所需的公钥私钥
     */
    @Test
    public static void createFile(String pathName) throws Exception {
        Map<Integer, String> keyMap = new HashMap<Integer, String>();  //用于封装随机产生的公钥与私钥
        //生成公钥和私钥
        RSAEncryptUtil.genKeyPair(keyMap);
        String publicKey = keyMap.get(0);
        String privateKey = keyMap.get(1);
        System.out.println("公钥是" + publicKey);
        System.out.println("私钥是" + privateKey);
        try {
            //创建一个文件保存公钥秘钥
            File file = new File(pathName);
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(publicKey);
            bw.newLine();//换行
            bw.write(privateKey);
            bw.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 获取txt文件中的公钥与秘钥
     */
    @Test
    public static Map<Integer,String> readKey() {
        //获取需要读取的文件
        File file = new File("D:\\key.txt");
        BufferedReader br = null;//字符输入流进行读取操作读取
        String publicKey = null;//用于存放公钥
        String privateKey = null;//用于存放私钥
        Map<Integer,String> map=new HashMap();
        int line = 2;//行号
        try {
            //输入字节流，FileInputStream主要用来操作文件输入流
            FileInputStream intput = new FileInputStream(file);

            // System.out.println("以行为单位读取文件内容，一次读一整行：")
            //InputStreamReader是转换流，将字节流转成字符流
            br = new BufferedReader(new InputStreamReader(intput),60 * 1024 * 1024);
          map.put(1,br.readLine());
            map.put(2,br.readLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return map;
        }
    }

    /**
     * 生成公钥私钥
     *放在D盘下的key.txt文件中
     * 第一行是公钥
     * 第二行是私钥
     * @param args
     */
    public static void main(String[] args) throws Exception {
        //创建文件存放公钥私钥
        createFile(pathName);
    }
}
