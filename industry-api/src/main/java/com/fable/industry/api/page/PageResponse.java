package com.fable.industry.api.page;


import com.github.pagehelper.Page;

import java.util.List;

/**
 *@author jiangmingjun
 *@date 2018/1/29
 *@description 分页返回
 */
public class PageResponse<T> {
    List<T> data;
    private int recordsTotal;
    private int recordsFiltered;
    private boolean success;
    private String code;
    private String message;

    public static <T> PageResponse<T> wrap(List<T> page,int no,int size) {
        PageResponse<T> response = new PageResponse<T>();
        response.setRecordsTotal(page.size());
        response.setRecordsFiltered(page.size());
        if(no*size>page.size()){
            page=page.subList((no-1)*size,page.size());
        }
        else{
            page = page.subList((no-1)*size,(no-1)*size+size);
        }
        response.setData(page);
        return response;
    }
    public static <T> PageResponse<T> wrap(Page<T> page,Boolean success,String code,String message){
        PageResponse<T> response = new PageResponse<T>();
        response.setRecordsTotal((int)page.getTotal());
        response.setRecordsFiltered((int)page.getTotal());
        response.setData(page);
        response.setSuccess(success);
        response.setCode(code);
        response.setMessage(message);
        return response;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
