package com.yc;

import com.yc.javax.servlet.http.HttpServletRequest;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: httpserver
 * @description:
 * @author: Erebus
 * @create: 2021-03-16 18:55
 */
public class YcHttpServletRequest implements HttpServletRequest {
    private InputStream iis;
    private Socket socket;

    private String realPath;
    private String requestURI;
    private String requestURL;
    private String queryString;


    private String method;//方法
    private Map<String,String> headers=new ConcurrentHashMap<String,String>();//头域
    private String uri;//请求资源的地址
    private String protocol;//协议的版本
    private Map<String,String[]> parameterMap=new ConcurrentHashMap<String,String[]>();

    @Override
    public Map<String, String[]> getParameterMap() {
        return parameterMap;
    }

    public String getRequestURI() {
        return requestURI;
    }

    @Override
    public String getRealPath() {
        return realPath;
    }

    public YcHttpServletRequest(InputStream iis, Socket socket){
        this.iis=iis;
        this.socket=socket;
    }

    public void parse(){
        //1.从iis中取出协议--字符串
        String protocolContent= readProtcolFromInputStream();

        //2.解析 请求行 请求头域 实体 参数
        parseProtocol(protocolContent);

        //3.存信息到headers及其他的域中

    }

    private void parseProtocol(String protocolContent) {
        if(protocolContent==null||"".equals(protocolContent)){
            return ;//TODO:加入404响应
        }
        //字符串分隔类：对字符串的空格与回车 来切割 指定用\r\n
        StringTokenizer st=new StringTokenizer(protocolContent,"\r\n");
        int index=0;
        while(st.hasMoreElements()){
            String line=st.nextToken();
            if(index==0){
                String[] first=line.split(" ");
                this.method=first[0];
                this.uri=first[1];
                this.protocol=first[2];

                //解析出realPath
                this.realPath=System.getProperty("user.dir")+ File.separator+"webapps"+ File.separator+this.uri.split("/")[0];
                this.requestURI=this.uri.split("\\?")[0];
                if("HTTP/1.1".equals(this.protocol)||"HTTP/1.0".equals(this.protocol)){
                    this.requestURL="http://"+this.socket.getLocalSocketAddress()+this.requestURI;
                }
                if(this.uri.indexOf("?")>=0){
                    this.queryString=this.uri.split("\\?")[1];
                    String[] params=this.queryString.split("&");
                    for(int i=0;i<params.length;i++){
                        String[] pv=params[i].split("=");

                        if(pv[1].indexOf(".")>=0){
                            String[] values= pv[1].split(".");
                            this.parameterMap.put(pv[0],values);
                        }else {
                            this.parameterMap.put(pv[0], new String[]{pv[1]});
                        }
                    }

                }



            }else if("".equals(line)){
                if("POST".equals(this.method)){
                    parseParams(st);
                }
                break;
            }else{
                String[] heads=line.split(": ");
                headers.put(heads[0],heads[1]);
            }
            index++;
        }
    }

    private void parseParams(StringTokenizer st) {
        while(st.hasMoreElements()){
            String Line=st.nextToken();
            String[] params=Line.split("&");
            for(int i=0;i<params.length;i++){
                String[] pv=params[i].split("=");

                if(pv[1].indexOf(".")>=0){
                   String[] values= pv[1].split(".");
                   this.parameterMap.put(pv[0],values);
                }else {
                    this.parameterMap.put(pv[0], new String[]{pv[1]});
                }
            }
        }
    }

    //从iis中取出协议--字符串
    private String readProtcolFromInputStream() {
        String protocolContent=null;
        StringBuffer sb=new StringBuffer(1024*30);
        int length=-1;
        byte[] bs=new byte[1024*30];
        try {
            length=this.iis.read(bs);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i=0;i<length;i++){
            sb.append((char)bs[i]);
        }
        protocolContent=sb.toString();
        return protocolContent;
    }

    @Override
    public String getHeader(String headerName) {
        if(headers!=null){
            return headers.get( headerName);
        }
        return null;
    }

    @Override
    public String[] getParameterValues(String key){
        return this.parameterMap.get(key);
    }



    @Override
    public String getParameter(String key){
        String[] values=getParameterValues(key);
        if(values!=null&&values.length>0){
            return values[0];
        }
        return null;
    }
    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public String getProtocol() {
        return protocol;
    }

    @Override
    public InputStream getInputStream() {
        return this.iis;
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public Map<String, String> getHeaders() {
        return null;
    }
}
