package com;

import com.yc.javax.servlet.http.HttpServlet;
import com.yc.javax.servlet.http.HttpServletRequest;
import com.yc.javax.servlet.http.HttpServletResponse;

/**
 * @program: httpserverVer1.0
 * @description:
 * @author: Erebus
 * @create: 2021-03-21 20:23
 */
public class HelloServlet extends HttpServlet {
    public HelloServlet(){
        System.out.println("HelloServlet的构造方法");
    }

    @Override
    public void init(){
        System.out.println("HelloServlet的初始化方法");
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp){
        System.out.println("HelloServlet的POST方法");

    }

    @Override
    public  void doGet(HttpServletRequest req, HttpServletResponse resp){
        System.out.println("HelloServlet的GET方法");

    }


}
