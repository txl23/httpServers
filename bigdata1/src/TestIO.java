import java.io.*;

public class TestIO {
    public static void main(String[] args) throws IOException {
        FileInputStream fis=new FileInputStream( new File("" +
                "e:\\a.txt"));
//        byte[] bs=new byte[2];
//        int length=-1;
//        while(  (length=fis.read(bs,0,bs.length))!=-1){
//            String s=new String( bs,0,length);
//            System.out.println(   s );
//        }
//        fis.close();

        byte[] bs=genByteArray(   fis );

        String s=new String(   bs);
        System.out.println( s  );
    }

    private static byte[] genByteArray(InputStream iis) {
        byte[] bs = new byte[2];
        int length = -1;
        //写法一: 老的try...catch...finally...
//        ByteArrayOutputStream boas=new ByteArrayOutputStream();  //字节数组输出流
//        try {
//            while ((length = iis.read(bs, 0, bs.length)) != -1) {
//                boas.write(bs, 0, length);
//            }
//        }catch( Exception ex){
//            ex.printStackTrace();
//        }finally{
//            try {
//                iis.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return boas.toByteArray();
        //写法二: 语法糖   try...catch新写法
        try (ByteArrayOutputStream boas = new ByteArrayOutputStream();
             InputStream iis0 = iis) {  //只有写在try()中的流才会自动关闭.
            while ((length = iis.read(bs, 0, bs.length)) != -1) {
                //System.out.println( "读取到字节数:"+  length );
                boas.write(bs, 0, length);
            }
            return boas.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
