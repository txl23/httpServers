package com.yc.net.bean1;

import java.net.InetAddress;
import java.net.UnknownHostException;

/* InetAddress
根据命令行传入的参数来取出地址
如果有参数，则显示参数所对应的地址, 没有则本机.
测试时，在命令行参数中写入两个参数:
    1. www.baidu.com.com
    2. 机器名
 */
public class Test1InetAddress {

	public static void main(String[] args) throws UnknownHostException {
		if(args.length>0){
			// www.baidu.com ->  DNS -> IP
			String host=args[0];
			InetAddress[] addresses=InetAddress.getAllByName(host);
			for(InetAddress a:addresses){
				System.out.println(a);
			}
		}else{
			InetAddress localHostAddress=InetAddress.getLocalHost();   //本机地址
			System.out.println(localHostAddress);
		}

	}

}

