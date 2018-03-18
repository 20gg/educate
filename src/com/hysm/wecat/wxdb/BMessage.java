package com.hysm.wecat.wxdb;


import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Date;
import org.bson.BasicBSONDecoder;
import org.bson.BasicBSONEncoder;
import org.bson.BasicBSONObject;



public class BMessage
{
	
	int msgType = 0;  //消息类型
	int fromID  = 0;  //来源用户ID
	int toID = 0;     //去用户ID 0--表示服务端
	int state = 0;   

	
    /*
     * 分了三级：
     * 1.会话  2.事务  3.序列
     * 从而以便于异步的通讯处理。
     * 如：服务端同时处理多组IM会话。
     * */
	private int transactionID = 0;//客户端的事务ID 	
	private int sessionID = 0;  //用于多会话时的sessionID
	private int serialID = 0;   //序号ID
	
	//直接使用BSONObject来处理消息各字段，同时便于编解码。
	BasicBSONObject bson = new BasicBSONObject();
	
	long timestamp = 0;
	//在BMessage中保存bytes的最大问题是对内存不能快速使用。
	InetSocketAddress remote = null;	
	int ttl = 3;  //用于控制重发处理
	

	static BasicBSONDecoder decoder = new BasicBSONDecoder();
	static BasicBSONEncoder encoder = new BasicBSONEncoder();
	
	byte[] msgBytes = null;
	
	public void setBytes(byte[] bytes)
	{
		this.msgBytes = bytes;
	}
	
	public BMessage()
	{		
	}	

	public BMessage(int msgType)
	{
		this.msgType = msgType;
	}
	public long getTimestamp() 
	{
		return timestamp;
	}
	public BMessage setTimestamp(long timestamp) 
	{
		this.timestamp = timestamp;
		return this;
	}
	
	public InetSocketAddress getRemote() 
	{
		return remote;
	}
	public BMessage setRemote(SocketAddress remote)
	{
		this.remote = (InetSocketAddress)remote;
		return this;
	}
	
	public int getTtl() 
	{
		return ttl;
	}
	public BMessage setTtl(int ttl) 
	{
		this.ttl = ttl;
		return this;
	}
	public BMessage(DatagramPacket udpPacket)
	{
		//仅拷贝，不作解码。
		remote = (InetSocketAddress)udpPacket.getSocketAddress();
		msgBytes = new byte[udpPacket.getLength()];
		System.arraycopy(udpPacket.getData(), 0, msgBytes, 0, udpPacket.getLength());		
	}
	
	public byte[] getMsgBytes()
	{
		return msgBytes;
	}
	
	public BMessage setFromID(int pFrom)
	{
		this.fromID = pFrom;
		return this;
	}
	public int getFromID()
	{
		return fromID;
	}

	public void debugMsg()
	{
		System.out.println("--------------------debug MSG header-----------------");
		System.out.println("msgType:"+ msgType);
		System.out.println("from:"+fromID);
		System.out.println("to:"+toID);
		System.out.println("transactionID:"+transactionID);
		System.out.println("sessionID:"+sessionID);	
		System.out.println("serialID:"+serialID);			
		System.out.println("state:"+state);			
		System.out.println("remote:"+ ((remote == null)?null:remote.toString()));	
		System.out.println("--------------------debug MSG body-----------------");
		System.out.println(bson);
		
		if(msgBytes == null)
		{
			System.out.println("--------------------msgBytes is null-----------------");
		}
		else
		{
			System.out.println("--------------------debug msgBytes length:"+msgBytes.length+"-----------------");
			DebugTool.dumpBytes(msgBytes, 0, msgBytes.length);
		}
		System.out.println("--------------------debug MSG ended-----------------");	
	}
	
	
	public int getMsgType() {
		return msgType;
	}
	public BMessage setMsgType(int msgType)
	{
		this.msgType = msgType;
		return this;
	}
	public int getToID() {
		return toID;
	}
	public BMessage setToID(int toID) 
	{
		this.toID = toID;
		return this;
	}
	public int getState() {
		return state;
	}
	public BMessage setState(int state) 
	{
		this.state = state;
		return this;
	}
	public int getTransactionID() {
		return transactionID;
	}
	public BMessage setTransactionID(int transactionID)
	{
		this.transactionID = transactionID;
		return this;
	}
	public BasicBSONObject getBson()
	{
		return bson;
	}
	public BMessage setBson(BasicBSONObject bson) 
	{
		this.bson = bson;
		return this;
	}
	public int getSessionID() 
	{
		if(sessionID == 0)
		{
			sessionID = ClientCommCenter.instance.assignSessionID();
		}
		return sessionID;
	}
	public BMessage setSessionID(int sessionID) 
	{
		this.sessionID = sessionID;
		return this;
	}
	
	
	public String getField(String key)
	{
		return bson.getString(key);
	}
	
	public BMessage setField(String key,Object val)
	{
		if(bson == null)
		{
			bson = new BasicBSONObject();
		}
		bson.put(key, val);
		return this;
	}
	


	public byte[] encode()
	{
		if(bson == null)
		{
			bson = new BasicBSONObject();
		}
		bson.put("msgtype",msgType);
		bson.put("sessionID", sessionID);
		bson.put("transactionID", transactionID);
		bson.put("from", fromID);
		bson.put("to", toID);
		bson.put("state", state);
		
		msgBytes = BytesCoder.encode(encoder.encode(bson));
		return msgBytes;
	}
	
	public void decode()
	{
		
		if(msgBytes == null){return;}
		try
		{
			
			System.out.println("--------------------decode MSG-----------------");		
			
			DebugTool.dumpBytes(msgBytes,0,msgBytes.length);
			//msgBytes =  BytesCoder.decode(msgBytes);			
			//DebugTool.dumpBytes(msgBytes, 0, msgBytes.length);			
			bson = (BasicBSONObject)decoder.readObject(BytesCoder.decode(msgBytes));
			fromID = bson.getInt("from");
			msgType = bson.getInt("msgtype");
			toID = bson.getInt("to", 0);
			sessionID = bson.getInt("session", 0);
			transactionID = bson.getInt("transactionID", 0);
			state = bson.getInt("state",0);
			timestamp = System.currentTimeMillis();		
			msgBytes = null;
			debugMsg();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

	}
	//TIME TO LOVE用于控制消息的多次循环处理
	public void setTTL(int v)
	{
		ttl = v;
	}

	public void destroy()
	{
		if(ttl <= 0)
		{
		}
		else
		{
			ttl --;
		}
	}


	public BMessage convertToResponse()
	{
		bson.clear();
		this.msgType = this.msgType+0xf000;
		int temp = fromID;
		this.fromID = this.toID;
		this.toID = temp;			
		return this;
	}

  
	public void reduceTTl(int i) 
	{
		ttl --;
	}
	
	private int resptimeout = 30000;

	public boolean timeout(int hEARTBEAT_THREAD_INTERVAL)
	{
		if((resptimeout -= hEARTBEAT_THREAD_INTERVAL)<=0)
		{
			return true;
		}
		return false;
	}
	
	
	  //测试编解码
		public static void main(String[] args)
		{
			/*
			BasicBSONObject bson = new BasicBSONObject();
			bson.put("name", "赵毅");
			bson.put("age", 9);
			bson.put("regdate", new Date());
			byte[] arrs = new byte[10];
			arrs[1]=1;arrs[2] =2;arrs[3] =2;arrs[3] =2;arrs[4] = 4;
			bson.put("bytes", arrs);
			
			System.out.println(bson);
			
			byte[] bytes = encoder.encode(bson);
			DebugTool.dumpBytes(bytes, 0, bytes.length);
			
			byte[] byte2 = new byte[bytes.length+100];
			System.arraycopy(bytes, 0, byte2, 0, bytes.length);
			for(int i = 0;i<100;i++)
			{
				byte2[bytes.length+i] = 5;
			}
			
			BasicBSONObject obj2 = (BasicBSONObject)decoder.readObject(byte2);
			System.out.println(obj2);
			
			byte[] arr = (byte[])obj2.get("bytes");
			System.out.println(arr.length);
			System.out.println(arr[1]);
			System.out.println(arr[2]);
			
			
			
			BMessage hmsg = new BMessage(MSGTYPEDEF.UDP_MSG_MFRAME_HEART_BEAR_REQ);
			hmsg.encode();
			//hmsg.debugMsg();
			hmsg.decode();
			*/
			
			
			/*
			BMessage msg = new BMessage(MSGTYPEDEF.UDP_LOGIN_AUTHCODE_REQ);
			msg.setField("info", "this is a test.");
			byte[] bytes = msg.encode();
			
			BMessage bmsg = new BMessage();
			bmsg.setBytes(bytes);
			bmsg.decode();
			bmsg.debugMsg();
			*
			*/
			//BasicBSONObject bson = new BasicBSONObject();
			
			//System.out.println(bson.getInt("ret",1));
			
			
		}

	
	

	

}
