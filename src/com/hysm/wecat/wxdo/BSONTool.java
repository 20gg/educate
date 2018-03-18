package com.hysm.wecat.wxdo;

import java.util.List;

import org.bson.BSONObject;

public class BSONTool
{
	int  listContainMems(List<?> list,String key,Object value)
	{
		if(list == null || list.size() <=0)
		{
			return 0;
		}
		
		int num = 0;
		Object obj = null;
		
		for(int i= 0;i<list.size();i++)
		{			
			obj = list.get(i);
			if(obj instanceof BSONObject)
			{
				if(((BSONObject)obj).get(key).equals(value))
				{
					num ++;
				}
			}			
		}
		
		return num;
	}
}
