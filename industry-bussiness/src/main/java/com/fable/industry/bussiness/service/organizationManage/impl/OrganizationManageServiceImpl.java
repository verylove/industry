package com.fable.industry.bussiness.service.organizationManage.impl;

import com.fable.industry.api.page.PageRequest;
import com.fable.industry.api.page.PageResponse;
import com.fable.industry.api.page.Result;
import com.fable.industry.api.page.TreeRsult;
import com.fable.industry.bussiness.mapper.organizationManage.OrganizationManageMapper;
import com.fable.industry.bussiness.model.bean.SysOrganizationBean;
import com.fable.industry.bussiness.service.organizationManage.OrganizationManageService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Auther Wangbaoan
 * @Date 2018-01-30 18:28
 */
@Service
@Transactional
public class OrganizationManageServiceImpl implements OrganizationManageService {
    @Autowired
    private OrganizationManageMapper organizationManageMapper;

    @Override
    public PageResponse<SysOrganizationBean> findOrganization(PageRequest<SysOrganizationBean> request) {
        SysOrganizationBean sysOrganizationBean = request.getParam();
        Page<SysOrganizationBean> data = PageHelper.startPage(request.getPageNo(), request.getPageSize());
        if (sysOrganizationBean.getComId() != 1) {
            sysOrganizationBean.setPid(sysOrganizationBean.getComId());
        }
        organizationManageMapper.findOrganization(sysOrganizationBean);
        return PageResponse.wrap(data, true, "0", "success");
    }

    @Override
    public List<TreeRsult> findOrganizationTree() {
        return organizationManageMapper.findOrganizationTree();
    }

    @Override
    public Map<String, Object> addOrganization(SysOrganizationBean sysOrganizationBean) {
        int count = organizationManageMapper.addOrganization(sysOrganizationBean);
        if (count > 0) {
            return Result.success("插入单位信息成功！");
        }
        return Result.fail("插入单位信息失败！");
    }

    @Override
    public Map<String, Object> updateOrganization(SysOrganizationBean sysOrganizationBean) {
        int count = organizationManageMapper.updateOrganization(sysOrganizationBean);
        if (count > 0) {
            return Result.success("修改单位信息成功！");
        }
        return Result.fail("修改单位信息失败！");
    }

    @Override
    public Map<String, Object> deleteOrganization(String comId) {
        String[] comIds = comId.split(",");
        List<String> list = Arrays.asList(comIds);
        int count = organizationManageMapper.deleteOrganization(list);
        if (count > 0) {
            return Result.success("删除单位信息成功！");
        }
        return Result.fail("删除单位信息失败！");
    }

    @Override
    public Map<String, Object> checkdeleteOrganization(String comId) {
        String[] comIds = comId.split(",");
        List<String> list = Arrays.asList(comIds);
        int count = organizationManageMapper.checkdeleteOrganization(list);
        int child = organizationManageMapper.checkdeleteOrganize(list);
        if (count > 0) {
            return Result.fail("该单位存在子单位信息或用户信息，无法删除请重新选择数据！");
        } else if (child > 0) {
            return Result.fail("该单位存在子单位信息或用户信息，无法删除请重新选择数据！");
        }
        return Result.success("该单位没有用户绑定也不存在子单位！");
    }

    @Override
    public Map<String, Object> checkOrganization(Map<String, String> map) {
        int count = organizationManageMapper.checkOrganization(map);
        if (count > 0) {
            return Result.fail("该单位名称已被使用！");
        }
        return Result.success("该单位名称未被使用");
    }

    @Override
    public SysOrganizationBean queryOrganizationById(Integer comId) {
        SysOrganizationBean sysOrganizationBean = organizationManageMapper.queryOrganizationById(comId);
        return sysOrganizationBean;
    }

    @Override
    public List<TreeRsult> findOrganizationComboBox() {
        return organizationManageMapper.findOrganizationComboBox();
    }

    @Override
    public List<SysOrganizationBean> queryAllCom() {
        return organizationManageMapper.queryAllCom();
    }
}
