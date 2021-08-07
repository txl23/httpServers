package com.yc;

import java.io.*;

/**
 * @program: httpserver
 * @description:
 * @author: Erebus
 * @create: 2021-03-16 18:55
 */
public class YcHttpServletResponse {
    private OutputStream oos;
    private YcHttpServletRequest request;

    public YcHttpServletResponse(OutputStream oos, YcHttpServletRequest request){
        this.oos=oos;
        this.request=request;

    }
    public void sendRedirect(){

        String responseprotocol=null;//响应协议
        byte[] fileContent=null;//响应的资源内容

        String uri=request.getRequestURI();

        File f=new File( request.getRealPath(),uri);

        if(!f.exists()){
            //若文件不存在
           File file404=new File(request.getRealPath(),"404.html");
           fileContent=readFile(file404);
           responseprotocol=gen404(file404,fileContent);
        }else{
            //存在文件 则读取文件
            fileContent=readFile(f);
            responseprotocol=gen200(f,fileContent);
        }

        try{
            //以输出流 输出数据到客户端
            this.oos.write(responseprotocol.getBytes());
            this.oos.flush();
            this.oos.write(fileContent);
            this.oos.flush();

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(this.oos!=null){

                try {
                    this.oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    public byte[] readFile(File file){
        byte[] bs=null;
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        InputStream iis= null;
        try {
            iis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bs =IoUtil.readFromInputStream(iis);


        return bs;

    }

    public String gen200(File file,byte[] fileContent){
        String result=null;
        String uri=this.request.getRequestURI();
        int index=uri.lastIndexOf(".");
        if(index>=0){
            index+=1;
        }
        String fileExtension=uri.substring(index);
        if("jpg".equalsIgnoreCase(fileExtension)||"jpeg".equalsIgnoreCase(fileExtension)){
            result="HTTP/1.1 200\r\nAccept-Ranges: bytes\r\nContent-Type: image/jpeg\r\nContent-Length: "+fileContent.length+"\r\n\r\n";
        }else if("png".equalsIgnoreCase(fileExtension)){
            result="HTTP/1.1 200\r\nAccept-Ranges: bytes\r\nContent-Type: image/png\r\nContent-Length: "+fileContent.length+"\r\n\r\n";

        }else if("json".equalsIgnoreCase(fileExtension)){
            result="HTTP/1.1 200\r\nAccept-Ranges: bytes\r\nContent-Type: application/json\r\nContent-Length: "+fileContent.length+"\r\n\r\n";

        }else if("css".equalsIgnoreCase(fileExtension)){
            result="HTTP/1.1 200\r\nAccept-Ranges: bytes\r\nContent-Type: text/css\r\nContent-Length: "+fileContent.length+"\r\n\r\n";

        }else if("js".equalsIgnoreCase(fileExtension)){
            result="HTTP/1.1 200\r\nAccept-Ranges: bytes\r\nContent-Type: application/javascript\r\nContent-Length: "+fileContent.length+"\r\n\r\n";

        }else{
            result="HTTP/1.1 200\r\nAccept-Ranges: bytes\r\nContent-Type: text/html;charset=UTF-8\r\nContent-Length: "+fileContent.length+"\r\n\r\n";

        }

        return result;
    }

    public String gen404(File file,byte[] fileContent){
        String result=null;
        result="HTTP/1.1 404\r\nAccept-Ranges: bytes\r\nContent-Type: text/html;charset=UTF-8\r\nContent-Length: "+fileContent.length+"\r\n\r\n";

        return result;
    }

    public String gen500(File file,byte[] fileContent){
        String result=null;
        return result;
    }
}
