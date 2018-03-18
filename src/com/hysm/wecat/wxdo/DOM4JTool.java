package com.hysm.wecat.wxdo;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Iterator;


import org.bson.BasicBSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;


public class DOM4JTool
{
	
	public static  BasicBSONObject decodeXML(Object obj)
	{		
		try
		{
			Document doc = null;
			if(obj instanceof InputStream)
			{
				SAXReader reader = new SAXReader();
				reader.setEncoding("UTF-8");
				doc = reader.read((InputStream)obj);				
			}
			else if(obj instanceof String)
			{
               doc = DocumentHelper.parseText((String)obj);
            }
			else{return null;}
			Element root = doc.getRootElement();
			BasicBSONObject bson = new BasicBSONObject();
			
			Iterator it = root.elementIterator();
			while(it.hasNext())
			{
				dump((Element)it.next(),bson);
			}			
			
			System.out.println(bson);
			
			return bson;
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	
	public static void dump(Element e,BasicBSONObject bson)
	{
		
		
		//System.out.println("name:"+e.getName());
		//System.out.println("text:"+e.getText());

		/*
		Iterator<Attribute> attrIt = (Iterator<Attribute>)e.attributeIterator();
		Attribute attr = null;
		while(attrIt.hasNext())
		{
			attr = (Attribute)attrIt.next();
			System.out.println("[attr]"+attr.getName()+":"+attr.getValue());
		}
		*/
        if(e.elements().size() >0)
		{		

			BasicBSONObject sub = new BasicBSONObject();
			Iterator it = e.elementIterator();
			while(it.hasNext())
			{
				dump((Element)it.next(),sub);
			}

        	bson.append(e.getName(), sub);
		}
        else
        {
        	bson.append(e.getName(), e.getText());
        }
	}
	
	public static byte[] writeXML(BasicBSONObject bson)
	{
		try
		{

			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			Document doc = DocumentFactory.getInstance().createDocument();
			Element e = DocumentFactory.getInstance().createElement("XML");
			doc.setRootElement(e);
			for(String key :bson.keySet())
			{
				Element em = DocumentFactory.getInstance().createElement(key);
				if(bson.get(key) instanceof String)
				{
					em.addCDATA(bson.getString(key));
				}
				else
				{
					em.setText(bson.getString(key));
				}
				e.add(em);
			}
			
			
			
			OutputFormat format = new OutputFormat();
			format.setEncoding("UTF-8");
			XMLWriter writer = new XMLWriter(bout,format);
			writer.setOutputStream(bout);
			
			writer.write(doc);
			
			return bout.toByteArray();
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	
	
	public static void main(String[] args)
	{
		String xml = "<xml><ToUserName><![CDATA[gh_318385f7872a]]></ToUserName><FromUserName><![CDATA[o9P2gt1ogzNOTTiAkc5rEa4N_Yj0]]></FromUserName><CreateTime>1402234058</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[为什么?]]></Content><MsgId>6022549420647587710</MsgId></xml>";
		DOM4JTool.decodeXML(xml);
		
		BasicBSONObject bson = new BasicBSONObject();
		bson.append("ToUserName", "o9P2gt1ogzNOTTiAkc5rEa4N_Yj0")
		    .append("FromUserName", "gh_318385f7872a")
		    .append("CreateTime", 1402234058)
		    .append("MsgType", "text")
		    .append("Content", "这是为什么！");
		byte[] bytes = DOM4JTool.writeXML(bson);
		String str = new String(bytes);
		System.out.println(str);
		    
	}
	
	
	
}
