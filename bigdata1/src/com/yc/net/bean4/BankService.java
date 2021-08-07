package com.yc.net.bean4;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class BankService implements Runnable {
	private Socket s;
	private Bank b;   // 多个线程操作同一个资源
	private Scanner reader;
	private PrintWriter pw;
	private boolean flag;

	public BankService(  Socket s, Bank b){
		this.s=s;
		this.b=b;
		try {
			System.out.println("atm客户端:"+ s.getInetAddress()+"联接了服务器");
			reader=new Scanner(   s.getInputStream() );
			pw=new PrintWriter(   s.getOutputStream() );
			flag=true;
		} catch (IOException e) {
			e.printStackTrace();
			flag=false;
		}
	}

	@Override
	public void run() {
		while(  flag ){
			if(   !reader.hasNext()  ){   //  hasNext() 表示是否有一个空格分隔的单词,    hasNextLine()是否有一行数据.,
				System.out.println(    "atm客户端:"+ s.getRemoteSocketAddress()+"掉线了...");
				break;
			}
			String command=reader.next();    //取出命令动作   next()方法按空格分隔取数据.   command只会取到命令动词
			if(  command.equals("QUIT")){
				System.out.println(    "atm客户端:"+ s.getRemoteSocketAddress()+"掉线了...");
				break;
			}
			//处理剩下的三种情况...
			int accountId=reader.nextInt();  //账号    此时取的是  账号
			try {
				BankAccount ba=null;
				if(command.equals("DEPOSIT")){     // deposit 100 100
					double amount=reader.nextDouble();
					ba=b.deposite(accountId, amount);
				}else if(command.equals("WITHDRAW")){      //WITHDRAW 1 10
					double amount=reader.nextDouble();
					ba=b.withdraw(accountId, amount);
				}else if(    !command.equals("BALANCE")    ){   //如果命令也不是BALANCE的话，则错误
					pw.println("错误的命令");
					pw.flush();
					return;
				}
				ba=b.search(accountId);
				pw.println(ba.getId()+" "+ba.getBalance());
				pw.flush();
			} catch (Exception e) {
				pw.println("操作异常:"+ e.getMessage());
				pw.flush();
			}
		}

	}

}
