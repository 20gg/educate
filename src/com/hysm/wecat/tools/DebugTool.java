package com.hysm.wecat.tools;

//import java.lang.reflect.Array;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class DebugTool 
{

	public static void dumpObject(Object obj)
	{

		if(obj == null)
		{
			System.out.println("obj is null");
			return;
		}
		Class<?> _class = obj.getClass();
		System.out.println("class:"+_class.getName());

		if(obj instanceof Integer 
				|| obj instanceof Long
				|| obj instanceof Float 
				|| obj instanceof Double
				|| obj instanceof Short
				|| obj instanceof Byte
				|| obj instanceof String
				|| obj instanceof Boolean
				)
		{
			System.out.println("value:"+obj);
			return;
			
		}
		if(_class.getName().startsWith("[I"))
		{
			dumpArray((int[])obj);
			return;
		}
		else if(_class.getName().startsWith("[B"))
		{
			dumpArray((byte[])obj);
			return;
		}
		else if(_class.getName().startsWith("[S"))
		{
			dumpArray((short[])obj);
			return;
		}
		else if(_class.getName().startsWith("[J"))
		{
			dumpArray((long[])obj);
			return;
		}
		else if(_class.getName().startsWith("[F"))
		{
			dumpArray((float[])obj);
			return;
		}
		else if(_class.getName().startsWith("[D"))
		{
			dumpArray((double[])obj);
			return;
		}
		else if(_class.getName().startsWith("[L"))
		{
			dumpArray((Object[])obj);
			return;
		}

		Field[] fieldArr = _class.getDeclaredFields();
		String name;
		//String type;
		try
		{
			for(int i=0;i<fieldArr.length;i++)
			{

				//if(!fieldArr[i].isAccessible()){continue;}
				name = fieldArr[i].getName();
				try{
					System.out.println(name+":"+fieldArr[i].get(obj));


					if(fieldArr[i].getType().getSimpleName().endsWith("[]"))
					{
						System.out.println(fieldArr[i].getType().getSimpleName()+":");
						Object arr = fieldArr[i].get(obj);
						if(arr == null){continue;}
						int length = Array.getLength(arr);
						for(int k = 0; k<length;k++)
						{
							System.out.println(k+":"+Array.get(arr,k));
						}				   
					}
				}
				catch(Exception ex)
				{
					System.out.println(name+"(no access);");
				}
			}
			System.out.println("*****************");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

	}


	public static void dumpCollection(Collection<?> c)
	{
		System.out.println("size:"+c.size());
		Iterator<?> it = c.iterator();
		while(it.hasNext())
		{
			Object obj = it.next();
			dumpObject(obj);
		}
	}

	public static void dumpMap(Map<?,?> c)
	{
		System.out.println("size:"+c.size());
		Iterator<?> it = c.keySet().iterator();
		while(it.hasNext())
		{
			Object key = it.next();
			System.out.println("key:");
			dumpObject(key);
			System.out.println("value:");
			dumpObject(c.get(key));
		}
	}

    public static void dumpBytes(byte[] bytes,int offset,int length)
    {
        dumpBytes(bytes,offset,length,"dump bytes");
    }

	public static void dumpBytes(byte[] bytes,int offset,int length,String note)
	{
		System.out.println("*************"+note+" begin******************************");
		String str;
		for(int i=0;i<length;i++)
		{
			if(i>0 && i%16 == 0)
			{
				System.out.println();
			}
			str = Integer.toHexString(bytes[i]&0xff);
			if(str.length()<2)
			{
				System.out.print(0);
			}
			System.out.print(str);
			System.out.print(' ');
		}
		System.out.println();
		System.out.println("******************"+note+" end*************************");
	}

    public static String dumpBytes2Str(byte[] bytes,int offset,int length)
    {
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<length;i++)
        {
            if(i%8 == 0) sb.append("\n");
            sb.append(Integer.toHexString(bytes[offset+i])).append(' ');
        }
        return sb.toString();
    }

	public static void dumpArray(Object[] array)
	{
		System.out.println("------------");
		if(array instanceof String[])
			for(int i=0;i<array.length;i++)
			{
				System.out.println(i+":"+array[i].toString());
			}
		else
		{
			for(Object obj : array)
			{
				dumpObject(obj);
			}
		}
		System.out.println("------------");
	}
	public static void dumpArray(byte[] array)
	{
		System.out.println("------------");
		for(int i=0;i<array.length;i++)
		{
			System.out.println(i+":"+array[i]);
		}
		System.out.println("------------");
	}
	public static void dumpArray(short[] array)
	{
		System.out.println("------------");
		for(int i=0;i<array.length;i++)
		{
			System.out.println(i+":"+array[i]);
		}
		System.out.println("------------");
	}

	public static void dumpArray(int[] array)
	{
		System.out.println("------------");
		for(int i=0;i<array.length;i++)
		{
			System.out.println(i+":"+array[i]);
		}
		System.out.println("------------");
	}
	public static void dumpArray(long[] array)
	{
		System.out.println("------------");
		for(int i=0;i<array.length;i++)
		{
			System.out.println(i+":"+array[i]);
		}
		System.out.println("------------");
	}

	public static void dumpArray(float[] array)
	{
		System.out.println("------------");
		for(int i=0;i<array.length;i++)
		{
			System.out.println(i+":"+array[i]);
		}
		System.out.println("------------");
	}

	public static void dumpArray(double[] array)
	{
		System.out.println("------------");
		for(int i=0;i<array.length;i++)
		{
			System.out.println(i+":"+array[i]);
		}
		System.out.println("------------");
	}





	public static void main(String[] args)
	{
		TObject t1 = new TObject("S1");
		TObject t2 = new TObject("S2");
		TObject[] a = {t1,t2};
		//DebugTool.dumpArray(Array.newInstance(Integer.class, a));

		//String[] a = {};
		DebugTool.dumpObject(a);
		DebugTool.dumpObject(7.23);
	}

}
