package com.fable.industry.api.page;


/**
 *@author jiangmingjun
 *@date 2018/1/29
 *@description 分页请求
 */
public class PageRequest<T> {
    private int pageNo;
    private int pageSize;
    T param;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public T getParam() {
        return param;
    }

    public void setParam(T param) {
        this.param = param;
    }
}
