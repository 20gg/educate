package com.hysm.tools;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PageBean<T>
{

    private int pageSize;	// 一页数据
    private int pageNum;	// 第几页
    private int pageCount;	// 总页数
    private int rowCount;	//总订单数
    private List<T> list;

    public void setPageCount(int pageCount)
    {
        this.pageCount = pageCount;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public int getPageNum()
    {
        return pageNum;
    }

    public void setPageNum(int pageNum)
    {
        this.pageNum = pageNum;
    }

    public int getRowCount()
    {
        return rowCount;
    }

    public void setRowCount(int rowCount)
    {
        this.rowCount = rowCount;
        setPageCount();
    }

    public int getPageCount()
    {
        return pageCount;
    }

    public void setPageCount()
    {
       System.out.println("行数="+rowCount+"------每页行数="+pageSize);
        
        if (rowCount % pageSize == 0)
        {
            this.pageCount = rowCount / pageSize;
        }
        else
        {
            this.pageCount = (rowCount / pageSize) + 1;
        }
    }

    public List<T> getList()
    {
        return list;
    }

    public void setList(List<T> list)
    {
        this.list = list;
    }
}
