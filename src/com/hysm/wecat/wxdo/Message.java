package com.hysm.wecat.wxdo;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class Message 
{
	public short magic_num = 0x686F;
	public byte equipment = 0x01;
	public byte source_s = 0x01;
	public byte dest_s = 0x03;
	public short command_name = 0;
	public byte command_len = 0;
	public byte[] command_data = null;
	
	public int mun_cigam = 0xF686;
	
	
	public static short COMMAND_C_FIND_ME = 0x023A;
	public static short COMMAND_C_SET_CURRENT_TIME = 0x0204;
	public static short COMMAND_C_SET_WORK_MODE = 0x0201;
	public static short COMMAND_C_DIRECTION = 0x0203;
	public static short COMMAND_C_SET_SCHEDULE_PROGRAM = 0x0205;
	public static short COMMAND_C_POWEROFF = 0x0232;
	public static short COMMAND_C_POWERON = 0x0241;
	public static short COMMAND_C_GET_POWER_STATUS = 0x0242;
	public static short COMMAND_C_SET_TURBO_STATUS_ON = 0x023d;
	public static short COMMAND_C_SET_TURBO_STATUS_OFF = 0x023e;
	public static short COMMAND_C_SET_FULLGO_ON = 0x023f;
	public static short COMMAND_C_SET_FUULGO_OFF = 0x0240;
	public static short COMMAND_C_SET_VOICE_VOLUME = 0x0206;
	public static short COMMAND_C_GET_LEFT_BUMPER_STATUS = 0x0209;
	public static short COMMAND_C_GET_RIGHT_BUMPER_STATUS = 0x020A;
	public static short COMMAND_C_GET_BUMBER_STATUS = 0x020B;	
	public static short COMMAND_C_GET_FIRMWARE_VERSION = 0x0231;
	public static short COMMAND_C_SCHEDULE_PRGRAM = 0x023C;
	
	
	public Message(short command)
	{
		this.command_name = command;
	}
	
	
	
	public byte[] encode()
	{
		ByteArrayOutputStream bOut = null;
		DataOutputStream dOut = null;
		try
		{
		    bOut = new ByteArrayOutputStream();
		    dOut = new DataOutputStream(bOut);
		    dOut.writeByte(0x6f);
		    dOut.writeByte(0x68);
		    dOut.writeByte(equipment);
		    dOut.writeByte(source_s);
		    dOut.writeByte(dest_s);
		    dOut.writeByte(command_name&0xFF);
		    dOut.writeByte((command_name>>8)&0xFF);
		    
		    dOut.writeByte(command_len);
		    if(command_len>0 && command_data != null)
		    {
		    	dOut.write(command_data);
		    }
		    
		    dOut.writeByte(mun_cigam&0xFF);
		    dOut.writeByte((mun_cigam>>8)&0xFF);
		    dOut.flush();
		    dOut.close();
		  
		    return bOut.toByteArray();
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			if(bOut != null){try{bOut.close();bOut = null;}catch(Exception ex){}}
		}
		
		
		
		return null;
	}
	
	public enum Light 
	{
        RED(1), GREEN(2), YELLOW(3);
        
        int value = 0;
        private Light(int i)
        {        	
        	value = i;
        }
    }
	
	
	

}
