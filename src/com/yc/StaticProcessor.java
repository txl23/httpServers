package com.yc;

/**
 * @program: httpserverVer1.0
 * @description:静态处理
 * @author: Erebus
 * @create: 2021-03-21 19:34

public class StaticProcessor implements Processor {
    @Override
    public void process(YcHttpServletRequest request, YcHttpServletResponse response){
        //静态资源处理则直接调用

        response.sendRedirect();
    }
}
 */