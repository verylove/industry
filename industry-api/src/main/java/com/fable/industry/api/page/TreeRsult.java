package com.fable.industry.api.page;

/**
 * @auther jiangmingjun
 * @create 2018/2/3
 * @description 返回树结构
 */
public class TreeRsult {
    private Integer id;
    private Integer pid;
    private String name;
    //额外参数
    /** 菜单是否被选中*/
    private boolean checked=false;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
