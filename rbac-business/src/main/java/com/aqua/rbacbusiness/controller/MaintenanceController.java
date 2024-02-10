package com.aqua.rbacbusiness.controller;

import com.aqua.rbacbusiness.model.dto.firefacility.FireFacilityQueryRequest;
import com.aqua.rbacbusiness.model.dto.firemaintenance.FireMaintenanceAddRequest;
import com.aqua.rbacbusiness.model.entity.FireData;
import com.aqua.rbacbusiness.model.entity.FireFacility;
import com.aqua.rbacbusiness.model.entity.FireMaintenance;
import com.aqua.rbacbusiness.model.vo.FireDataVO;
import com.aqua.rbacbusiness.model.vo.FireFacilityVO;
import com.aqua.rbacbusiness.serivce.FireDataService;
import com.aqua.rbacbusiness.serivce.FireFacilityService;
import com.aqua.rbacbusiness.serivce.FireMaintenanceService;
import com.aqua.rbaccommon.common.BaseResponse;
import com.aqua.rbaccommon.common.ErrorCode;
import com.aqua.rbaccommon.common.ResultUtils;
import com.aqua.rbaccore.annotation.AuthCheck;
import com.aqua.rbaccore.exception.BusinessException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author water king
 * @time 2024/2/10
 */
@RestController
@RequestMapping("/role/maintenance")
@Slf4j
public class MaintenanceController {

    @Resource
    private FireMaintenanceService fireMaintenanceService;

    @Resource
    private FireFacilityService fireFacilityService;

    @Resource
    private FireDataService fireDataService;

    /**
     * 添加日常维护记录
     * @param fireMaintenanceAddRequest
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(permissionName = "添加日常维护记录的权限", requirePermission = "fireMaintenance:add")
    public BaseResponse<Long> addFireMaintenance(@RequestBody FireMaintenanceAddRequest fireMaintenanceAddRequest) {
        if (fireMaintenanceAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        FireMaintenance fireMaintenance = new FireMaintenance();
        BeanUtils.copyProperties(fireMaintenanceAddRequest, fireMaintenance);
        boolean result = fireMaintenanceService.save(fireMaintenance);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(fireMaintenance.getId());
    }

    /**
     * 分页查询消防设备信息
     * @param fireFacilityQueryRequest
     * @return
     */
    @GetMapping("/list/page")
    @AuthCheck(permissionName = "分页查询消防设备信息的权限", requirePermission = "fireFacility:selectByPage")
    public BaseResponse<Page<FireFacilityVO>> listFireFacilityByPage(FireFacilityQueryRequest fireFacilityQueryRequest) {
        long current = 1;
        long size = 5;
        FireFacility fireFacilityQuery = new FireFacility();
        if (fireFacilityQueryRequest != null) {
            BeanUtils.copyProperties(fireFacilityQueryRequest, fireFacilityQuery);
            current = fireFacilityQueryRequest.getCurrent();
            size = fireFacilityQueryRequest.getPageSize();
        }
        QueryWrapper<FireFacility> queryWrapper = new QueryWrapper<>(fireFacilityQuery);
        Page<FireFacility> fireFacilityPage = fireFacilityService.page(new Page<>(current, size), queryWrapper);
        Page<FireFacilityVO> fireFacilityVOPage = new PageDTO<>(fireFacilityPage.getCurrent(), fireFacilityPage.getSize(), fireFacilityPage.getTotal());
        List<FireFacilityVO> fireFacilityVOList = fireFacilityPage.getRecords().stream().map(fireFacility -> {
            FireFacilityVO fireFacilityVO = new FireFacilityVO();
            BeanUtils.copyProperties(fireFacility, fireFacilityVO);
            return fireFacilityVO;
        }).collect(Collectors.toList());
        fireFacilityVOPage.setRecords(fireFacilityVOList);
        return ResultUtils.success(fireFacilityVOPage);
    }

    /**
     * 获取最近五条报警信息
     * @return
     */
    @GetMapping("/top/data/list")
    @AuthCheck(permissionName = "获取最近报警信息", requirePermission = "invoke:recent5")
    public BaseResponse<List<FireDataVO>> listTopInvokeDetailData() {
        // 修改查询条件，按时间降序排列，限制结果为前五条数据
        QueryWrapper<FireData> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("alarmTime").last("LIMIT 5");
        List<FireData> list = fireDataService.list(queryWrapper);

        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        List<FireDataVO> fireDataVOList = list.stream().map(fireData -> {
            FireDataVO fireDataVO = new FireDataVO();
            BeanUtils.copyProperties(fireData, fireDataVO);
            return fireDataVO;
        }).collect(Collectors.toList());

        return ResultUtils.success(fireDataVOList);
    }

   // TODO (aqua 2024/2/11 0:13) 差一个接收消防设施报警信息的方法

}
