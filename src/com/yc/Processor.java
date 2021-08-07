package com.yc;

import com.yc.javax.servlet.http.HttpServletRequest;
import com.yc.javax.servlet.http.HttpServletResponse;

/*
    资源处理接口
 */
public interface Processor {
    public void process(HttpServletRequest request, HttpServletResponse response);

}
