package com.yc;

import com.yc.threadpool.Taskable;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

//一个任务类完成与一个客户的处理.
public class NetTask implements Runnable, Taskable {
    private Socket s;
    private InputStream iis;
    private OutputStream oos;

    public NetTask(Socket s) {
        this.s=s;
    }

    @Override
    public void doTask(){
        run();
    }

    @Override
    public void run() {
        // 取出流   socket
      try {
              this.iis = this.s.getInputStream();
              this.oos = this.s.getOutputStream();
              //request功能就是解析? 请求行，请示头域，请求实体...
              YcHttpServletRequest request = new YcHttpServletRequest(this.iis,this.s);
              request.parse();
              //为什么在response中要有一个request呢? 因为响应时要知道请求中请求的资源地址
              YcHttpServletResponse response = new YcHttpServletResponse(this.oos, request);
                response.sendRedirect();
              //Processor processor=null;
              //判断request中资源是静态还是动态
//              if(request.getRequestURI().endsWith(".action")){
//
//                  processor=new DynamicProcessor();
//              }else{
//                  //如果是静态
//                  processor=new StaticProcessor();
//              }
//              processor.process(request,response);

              // Connection: keep-alive
            this.s.close();   // http协议. 无状态
        }catch( Exception ex){
            ex.printStackTrace();
        }

    }
}
