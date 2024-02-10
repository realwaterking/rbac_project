package com.aqua.rbacbusiness.controller;

import com.aqua.rbacbusiness.mapper.FireDataMapper;
import com.aqua.rbacbusiness.model.entity.FireData;
import com.aqua.rbacbusiness.model.entity.FireMaintenance;
import com.aqua.rbacbusiness.model.vo.DataInfoVO;
import com.aqua.rbacbusiness.model.vo.FireDataVO;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author water king
 * @time 2024/2/8
 */
@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {

    @Resource
    private FireDataMapper fireDataMapper;

    @Resource
    private FireDataService fireDataService;

    @Resource
    private FireMaintenanceService fireMaintenanceService;

    @Resource
    private FireFacilityService fireFacilityService;

    /**
     * 获取报警数前三的设备信息
     * @return
     */
    @GetMapping("/top/data/invoke")
    @AuthCheck(permissionName = "查看报警数前三的设备", requirePermission = "invoke:top3")
    public BaseResponse<List<DataInfoVO>> listTopInvokeFireData() {
        List<FireData> fireDataList = fireDataMapper.listTopInvokeFireData(3);
        Map<Long, List<FireData>> fireDataObjMap = fireDataList.stream()
                .collect(Collectors.groupingBy(FireData::getId));
        QueryWrapper<FireData> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", fireDataObjMap.keySet());
        List<FireData> list = fireDataService.list(queryWrapper);

        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        List<DataInfoVO> dataInfoVOList = list.stream().map(fireData -> {
            DataInfoVO dataInfoVO = new DataInfoVO();
            BeanUtils.copyProperties(fireData, dataInfoVO);
            int totalNum = fireDataObjMap.get(fireData.getId()).get(0).getTotalNum();
            dataInfoVO.setTotalNum(totalNum);
            return dataInfoVO;
        }).collect(Collectors.toList());

        return ResultUtils.success(dataInfoVOList);
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

    /**
     * 获取连接状态统计信息
     * @return
     */
    @GetMapping("/connection/status/count")
    @AuthCheck(permissionName = "获取连接状态统计信息", requirePermission = "invoke:connectionStatusCount")
    public BaseResponse<Map<String, Integer>> getConnectionStatusCount() {
        // 查询所有设备数据
        List<FireData> fireDataList = fireDataService.list();

        // 初始化统计数据
        int normalCount = 0;
        int unstableCount = 0;
        int failedCount = 0;

        // 遍历设备数据，统计连接状态
        for (FireData fireData : fireDataList) {
            int connectionStatus = fireData.getConnectionStatus();
            if (connectionStatus == 0) {
                normalCount++;
            } else if (connectionStatus == 1) {
                unstableCount++;
            } else if (connectionStatus == 2) {
                failedCount++;
            }
            // 如果连接状态无效，不做任何处理
        }

        // 将统计结果放入Map中
        Map<String, Integer> statusCount = new HashMap<>();
        statusCount.put("normalCount", normalCount);
        statusCount.put("unstableCount", unstableCount);
        statusCount.put("failedCount", failedCount);

        return ResultUtils.success(statusCount);
    }

    /**
     * 获取近一个月内维护过和未维护过的设备数量
     * @return
     */
    @GetMapping("/maintenance/count")
    @AuthCheck(permissionName = "获取近一个月维护信息统计", requirePermission = "invoke:recentMaintenanceCount")
    public BaseResponse<Map<String, Long>> getMaintenanceCount() {
        // 计算最近一个月的起始日期
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        Date startDate = java.sql.Date.valueOf(oneMonthAgo);

        // 查询近一个月维护过的设备数量
        QueryWrapper<FireMaintenance> maintenanceQuery = new QueryWrapper<>();
        maintenanceQuery.ge("maintenanceDate", startDate);
        Long maintainedCount = fireMaintenanceService.count(maintenanceQuery);

        // 查询设备总数
        Long totalDeviceCount = fireFacilityService.count();

        // 计算近一个月未维护过的设备数量
        Long notMaintainedCount = totalDeviceCount - maintainedCount;

        // 将结果放入Map中
        Map<String, Long> result = new HashMap<>();
        result.put("maintainedCount", maintainedCount);
        result.put("notMaintainedCount", notMaintainedCount);

        return ResultUtils.success(result);
    }


}
//    public BaseResponse<List<>>
//
//
//    @GetMapping("/top/interface/invoke")
//    @AuthCheck(mustRole = "admin")
//    public BaseResponse<List<InterfaceInfoVO>> listTopInvokeInterfaceInfo() {
//        List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoMapper.listTopInvokeInterfaceInfo(3);
//        Map<Long, List<UserInterfaceInfo>> interfaceInfoIdObjMap = userInterfaceInfoList.stream()
//                .collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));
//        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
//        queryWrapper.in("id", interfaceInfoIdObjMap.keySet());
//        List<InterfaceInfo> list = interfaceInfoService.list(queryWrapper);
//        if (CollectionUtils.isEmpty(list)) {
//            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
//        }
//        List<InterfaceInfoVO> interfaceInfoVOList = list.stream().map(interfaceInfo -> {
//            InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
//            BeanUtils.copyProperties(interfaceInfo, interfaceInfoVO);
//            int totalNum = interfaceInfoIdObjMap.get(interfaceInfo.getId()).get(0).getTotalNum();
//            interfaceInfoVO.setTotalNum(totalNum);
//            return interfaceInfoVO;
//        }).collect(Collectors.toList());
//        return ResultUtils.success(interfaceInfoVOList);
//    }
}
