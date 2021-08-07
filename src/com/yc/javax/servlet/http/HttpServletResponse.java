package com.yc.javax.servlet.http;

import com.yc.javax.servlet.ServletResponse;

/**
 * 针对http的响应接口
 */
public interface HttpServletResponse extends ServletResponse {
    public void sendRedirect();
}
