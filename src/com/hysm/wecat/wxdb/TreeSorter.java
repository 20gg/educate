package com.hysm.wecat.wxdb;


//import java.sql.*;
import java.util.Hashtable;
import java.util.Vector;

//this file are not used.
public class TreeSorter
{
	Hashtable<String,Integer> columnMapping = null;
	Vector<String[]> vec = null;
	Hashtable<String,String[]> map = null;	
	public TreeSorter(QueryResult qr,String parentKey,String idKey)
	{
		this.qr = qr;
		this.parentKey = parentKey;
		this.idKey = idKey;
	}
	
	QueryResult qr = null;
	String parentKey = null;
	String idKey = null;
	
	
	Hashtable<String,Integer> idKey_ind = new Hashtable<String,Integer>();
	int[][] treeArr = null;
	int rank0Ind = 0;
	int[] sortedArr =null;
	
	public QueryResult getQueryResult()
	{
		return qr;
	}
	/*
	 * 该函数用于对从数据库表查到的结果按tree方式排序。
	 * */
	public  void treeSort()
	{
		if(qr == null){return;}
		columnMapping = qr.columnMapping;
		vec = qr.vec;
		map = qr.map;
		
		int keyInd = columnMapping.get(idKey.toUpperCase()).intValue();
		int parentInd = columnMapping.get(parentKey.toUpperCase()).intValue();
		
		
		int nodeSum = vec.size();
		sortedArr = new int[nodeSum];
		//[0]--ind,[1]--rank [2]--parent ind [3] -子节点总数 [4]后一子节点开始ID数 [5]本子节点编码，也是本节点的排序.
		treeArr = new int[nodeSum][6];	
		
		//第一轮循环，正序，建立起ID与IND映射。	
		int _pInd = 0;
		Integer _pIndObj = null;
		for(int i=0;i< nodeSum;i++)
		{
			treeArr[i][0] = i;//Integer.parseInt(vec.elementAt(i)[keyInd]);
			idKey_ind.put(vec.elementAt(i)[keyInd],Integer.valueOf(i));
		}
		
		for(int i=0;i< nodeSum;i++)
		{
			/*
			 * 该行有些问题，限定了key值只能为整数，可以考虑扩充到字符串<ERROR>
			 * */
			//treeArr[i][0] = i;//Integer.parseInt(vec.elementAt(i)[keyInd]);
			//idKey_ind.put(vec.elementAt(i)[keyInd],Integer.valueOf(i));
			if(vec.elementAt(i)[parentInd].equals("0"))
			{
				treeArr[i][1] = 0;
			    treeArr[i][2] = -1;		
			}
			else
			{
				_pIndObj = idKey_ind.get(vec.elementAt(i)[parentInd]);
				if(_pIndObj == null)
				{
					treeArr[i][1] = 0;
				    treeArr[i][2] = -1;	
				}
				else
				{
					_pInd = _pIndObj.intValue();
					treeArr[i][2] = _pInd;
					treeArr[i][1] = treeArr[_pInd][1]+1;	
				}	
			}			
		}
		

		//System.out.println("test0-5-1:"+System.nanoTime());	
		
		//第二轮循环，倒序，统计各节点下的子节点数
		for(int i= nodeSum-1;i>=0;i--)
		{
			if(treeArr[i][2] == -1)
			{				
			}
			else
			{   
				treeArr[treeArr[i][2]][3]+=treeArr[i][3]+1;
			}
		}
		
		//System.out.println("test0-5-2:"+System.nanoTime());		
		
		//第三轮循环，正序，为节点赋值,排序完成.
		for(int i= 0;i<nodeSum;i++)
		{
			if(treeArr[i][2] == -1)
			{	
				treeArr[i][5] = rank0Ind;
				rank0Ind = rank0Ind + 1 + treeArr[i][3];
			}
			else
			{
				_pInd = treeArr[i][2];
				treeArr[i][5] = treeArr[_pInd][5]+treeArr[_pInd][4]+1;
				treeArr[_pInd][4] = treeArr[_pInd][4]+treeArr[i][3]+1;				
			}
			sortedArr[treeArr[i][5]] = i;
		}
		//System.out.println("test0-5-3:"+System.nanoTime());		
		
		
	}
	
	public void dump()
	{
		if(sortedArr == null)
		{
			return;			
		}
		for(int i= 0; i<sortedArr.length;i++)
		{
			for(int x = 0;x<treeArr[sortedArr[i]][1];x++)
			{
				System.out.print("-");
			}
			String[] data = vec.elementAt(sortedArr[i]);
			for(int x = 0;x<data.length;x++)
			{
				System.out.print(data[x]);
				System.out.print(" ");
			}
			System.out.println();
		}
	}
	
	public void dump(int begin,int end)
	{
		if(sortedArr == null)
		{
			return;			
		}
		for(int i= begin; i<end && i<sortedArr.length;i++)
		{
			for(int x = 0;x<treeArr[sortedArr[i]][1];x++)
			{
				System.out.print("-");
			}
			String[] data = vec.elementAt(sortedArr[i]);
			for(int x = 0;x<data.length;x++)
			{
				System.out.print(data[x]);
				System.out.print(" ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args)
    {    
		 QueryResult qr = DBManager.queryResult("select * from c1.corporgs","orgid");
		 qr.dump();
		 
		 TreeSorter ts = new TreeSorter(qr,"parentorgid", "orgid");
		 ts.treeSort();
		 ts.dump();
	 }  
	
	
	public static void main2(String[] args)
    {    
		 System.out.println("test0-4:"+System.nanoTime());
		 QueryResult qr = DBManager.queryResult("select * from testtree limit 0,800000",null);
		 TreeSorter ts = new TreeSorter(qr,"parent", "id");
		 System.out.println("test0-5:"+System.nanoTime());
		 ts.treeSort();
		 System.out.println("test0-6:"+System.nanoTime());
		 //ts.dump();
		 ts.dump(18000,18050);
		 System.out.println("test0-7:"+System.nanoTime());
	 }  
	
	
	public int[]  parentBranch(String id)
	{
		int ind = idKey_ind.get(id).intValue();
		int[] indArr = new int[treeArr[ind][1]];
		
		int ptr =  treeArr[ind][2];
		for(int i= indArr.length-1;i>=0;i--)
		{
			indArr[i] = treeArr[ptr][5];
			ptr =  treeArr[ptr][2];
		}		
		return indArr;
	}
	
	public int getParent(String id)
	{
		Integer objInd = idKey_ind.get(id);
		if(objInd == null){return 0;}
		return Integer.parseInt(qr.get(objInd.intValue(),this.parentKey));
	}
	public int[]  childrenBranch(String id)
	{
		int ind = idKey_ind.get(id).intValue();
		int[] indArr = new int[treeArr[ind][3]];
		System.arraycopy(sortedArr, treeArr[ind][5]+1, indArr, 0, indArr.length);
		return indArr;
	}
	public String dumpJSONStr()
	{
		return qr.dumpJSONStr(sortedArr);
	}
	/*
	 * Only for Plan
	 * */
	public String toPlanJSON()
	{
		if(qr == null){return "[]";}
		StringBuffer sb = new StringBuffer();
		sb.append('[');
		int baseRank = treeArr[sortedArr[0]][1];
		String temp = null;
		for(int i=0;i<sortedArr.length;i++)
		{
			sb.append("{\'id\':");
			sb.append(qr.get(sortedArr[i],"id"));
			sb.append(",\'parent\':");
			sb.append(qr.get(sortedArr[i],"parentid"));
			sb.append(",\'sheetid\':");
			sb.append(qr.get(sortedArr[i],"sheetid"));
			sb.append(",\'indent\':");
			sb.append(treeArr[sortedArr[i]][1]-baseRank);
			sb.append(",\'title\':'");
			sb.append(qr.get(sortedArr[i],"title"));
			sb.append("',\'description\':'");
			sb.append(qr.get(sortedArr[i],"info"));
			sb.append("',\'duration\':");
			sb.append(qr.get(sortedArr[i],"duration"));
			sb.append(",\'action\':");
			sb.append(qr.get(sortedArr[i],"ACTION"));
			sb.append(",\'state\':");
			sb.append(qr.get(sortedArr[i],"state"));
			sb.append(",\'percentComplete\':");
			sb.append(qr.get(sortedArr[i],"COMPLETED"));
			sb.append(",\'finish\':'");
			sb.append(qr.get(sortedArr[i],"finish"));
			sb.append("',\'start\':'");
			sb.append(qr.get(sortedArr[i],"start"));
			sb.append("',\'res\':");
			temp = qr.get(sortedArr[i],"resource");
			if(temp == null || temp.equals("")){temp="[]";}
			sb.append(temp);
			sb.append(",\'importance\':");
			sb.append(qr.get(sortedArr[i],"importance"));
			sb.append(",\'emergency\':");
			sb.append(qr.get(sortedArr[i],"emergency"));
			sb.append(",\'remark\':");
			sb.append(qr.get(sortedArr[i],"remark"));
			sb.append(",\'right\':");
			sb.append(qr.get(sortedArr[i],"r","0"));
			sb.append("},");			
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(']');
		return sb.toString();
		
	}
	
	
 }
