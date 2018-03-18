package com.hysm.wecat.wxdb;
/*
 * 该类用于处理各activity的消息处理。 
 * */


import java.util.Hashtable;
import org.bson.BasicBSONObject;


public class ClientCommCenter
{
	
	private ClientCommCenter()
	{	
	}
	
	//private Hashtable<Integer,AsynActivity> asynActivityHash = new Hashtable<Integer,AsynActivity>();
	private Hashtable<Integer,Integer> msgTracer = new Hashtable<Integer,Integer>();
	
	private int transactionID = 1000;	
	public  synchronized  int assignTransactionID()
	{
		return transactionID++;
	}
	
    private int sessionID = 1000;
	
	public  synchronized  int assignSessionID()
	{
		return sessionID++;
	}
	
	 private int serialID = 1000;
		
	 public  synchronized  int assignSerialID()
	 {
			return serialID++;
	 }
	
	private ClientMessageProcessor processor = null;	
	
	public static final ClientCommCenter instance = new ClientCommCenter();
	
	public ClientMessageProcessor getProcessor() 
	{
		return processor;
	}
	public void setProcessor(ClientMessageProcessor processor)
    {
		this.processor = processor;
	}
	
	//Handler	
	public void receivedMsg(BasicBSONObject msg)
	{	
		System.out.println("MessageCenter received msg:"+msg);
		
		
	}
	
	
	

	/*
	 * 同步消息是activity处于等待中，关键是消息发送与接收是异步的。
	 * 因此消息发送完成后，异步的接收消息要找到最初发送的activity.
	 * 
	*/
	
	
	
	
	
	
	public void main(String[] args)
	{
		
	}
}
