package com.aqua.rbacbusiness.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author water king
 * @time 2024/2/8
 */
@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {

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
