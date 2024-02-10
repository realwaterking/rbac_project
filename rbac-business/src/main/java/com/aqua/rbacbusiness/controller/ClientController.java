package com.aqua.rbacbusiness.controller;

import com.aqua.rbacbusiness.model.vo.FireFacilityInfoVO;
import com.aqua.rbacbusiness.serivce.FireFacilityService;
import com.aqua.rbacbusiness.serivce.FireMaintenanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author water king
 * @time 2024/2/10
 */
@RestController
@RequestMapping("/role/client")
@Slf4j
public class ClientController {

    @Resource
    private FireFacilityService fireFacilityService;

    @Resource
    private FireMaintenanceService fireMaintenanceService;

    /**
     * 根据用户所在地查询消防设备信息以及最近一次维护时间
     * @param location 用户所在地
     * @return 消防设备信息列表
     */
    @GetMapping("/fireFacilities")
    public List<FireFacilityInfoVO> getFireFacilitiesByLocation(@RequestParam String location) {
        // 根据用户地址查询消防设备
        List<FireFacilityInfoVO> fireFacilities = fireFacilityService.getFireFacilities(location);

//        // 遍历每个设施并获取其最近的维护日期和维护人员信息
//        return fireFacilities.stream()
//                .filter(fireFacility -> location.contains(fireFacility.getLocation()))
//                .map(fireFacility -> {
//                    FireFacilityInfoVO fireFacilityInfo = new FireFacilityInfoVO();
//                    BeanUtils.copyProperties(fireFacility, fireFacilityInfo);
//                    fireFacilityInfo.setLastInspectionDate(fireMaintenanceService.getLatestMaintenanceDate(fireFacility.getId()));
//                    fireFacilityInfo.setMaintenancePerson(fireUserService.getMaintenancePersonByDeviceId(fireFacility.getId()));
//                    return fireFacilityInfo;
//                })
//                .collect(Collectors.toList());
//    }
        return fireFacilities;
    }
}
