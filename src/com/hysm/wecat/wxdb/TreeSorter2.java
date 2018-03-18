package com.hysm.wecat.wxdb;


//import java.sql.*;
import java.util.Hashtable;
import java.util.Vector;

public class TreeSorter2
{
	Hashtable<String,Integer> columnMapping = null;
	Vector<String[]> vec = null;
	Hashtable<String,String[]> map = null;
	
	public TreeSorter2(){}
	
	
	Hashtable<String,Integer> idKey_ind = new Hashtable<String,Integer>();
	int[][] treeArr = null;
	int rank0Ind = 0;
	
	

	int[] sortedArr =null;
	/*
	 * 该函数用于对从数据库表查到的结果按tree方式排序。
	 * */
	public  void treeSort(QueryResult qr,String parentKey,String idKey)
	{
		columnMapping = qr.columnMapping;
		vec = qr.vec;
		map = qr.map;
		
		int keyInd = columnMapping.get(idKey.toUpperCase()).intValue();
		int parentInd = columnMapping.get(parentKey.toUpperCase()).intValue();
		
		
		int nodeSum = vec.size();
		sortedArr = new int[nodeSum];
		//[0]--ID,[1]--rank [2]--parent id [3] -子节点总数 [4]后一子节点开始ID数 [5]本子节点编码
		treeArr = new int[nodeSum][6];	
		
		//第一轮循环，正序，建立起ID与IND映射。	
		int _pInd = 0;
		Integer _pIndObj = null;
		for(int i=0;i< nodeSum;i++)
		{
			treeArr[i][0] = Integer.parseInt(vec.elementAt(i)[keyInd]);
			idKey_ind.put(vec.elementAt(i)[keyInd],Integer.valueOf(i));
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
		

		System.out.println("test0-5-1:"+System.nanoTime());	
		
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
		/*
		for(int i= 0;i<nodeSum;i++)
		{
			System.out.println("ID:"+treeArr[i][0]+" parent:"+treeArr[i][2]+" subNodeSum:"+treeArr[i][3]);
		}
		*/

		System.out.println("test0-5-2:"+System.nanoTime());		
		
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
		System.out.println("test0-5-3:"+System.nanoTime());		
		
		
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
		 System.out.println("test0-4:"+System.nanoTime());
		 QueryResult qr = DBManager.queryResult("select * from testtree limit 0,800000",null);
		 TreeSorter2 ts = new TreeSorter2();
		 System.out.println("test0-5:"+System.nanoTime());
		 ts.treeSort(qr,"parent", "id");
		 System.out.println("test0-6:"+System.nanoTime());
		 //ts.dump();
		 ts.dump(18000,18050);
		 System.out.println("test0-7:"+System.nanoTime());
		 
		 

      }   
	
	
 }
