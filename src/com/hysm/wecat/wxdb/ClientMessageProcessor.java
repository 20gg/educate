package com.hysm.wecat.wxdb;


import org.bson.BasicBSONObject;

public interface ClientMessageProcessor 
{
	public void processMsg(BasicBSONObject msg);
}
