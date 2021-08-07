package com.yc;

import com.yc.javax.servlet.http.HttpServletRequest;
import com.yc.javax.servlet.http.HttpServletResponse;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * @program: httpserverVer1.0
 * @description:
 * @author: Erebus
 * @create: 2021-03-21 20:52

public class DynamicProcessor implements  Processor{
    @Override
    public void processor(HttpServletRequest request, HttpServletResponse response){
        String uri=request.getUri();
        int slash= uri.lastIndexOf("/")+1;
        int dot=uri.lastIndexOf(".");
        String servletName=uri.substring(slash,dot);
        String basePath=request.getRealPath();

        URL url=new URL("file",null,basePath);
        URL[] urls=new URL[]{url};
        URLClassLoader ucl=new URLClassLoader(urls);


    }
}
 */