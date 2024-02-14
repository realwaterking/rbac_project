package com.aqua.rbaccore.service.impl;

import com.aqua.rbaccore.annotation.AuthCheck;
import com.aqua.rbaccore.mapper.PermissionMapper;
import com.aqua.rbaccore.model.entity.Permission;
import com.aqua.rbaccore.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
* @author 70742
* @description 针对表【permission(权限信息表)】的数据库操作Service实现
* @createDate 2024-02-05 23:26:00
*/
@Service
@Slf4j
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
    implements PermissionService{

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public String load() {
        List<String> permissions = permissionMapper.selectPermissions();
        Map<String, Object> map = applicationContext.getBeansWithAnnotation(RestController.class);
        Collection<Object> controllers = map.values();
        for (Object controller : controllers) {
            Method[] methods = controller.getClass().getDeclaredMethods();
            for (Method method : methods) {
                AuthCheck annotation = method.getAnnotation(AuthCheck.class);
                System.out.println(annotation);
                if (annotation != null) {
                    String permissionName = annotation.permissionName();
                    String requirePermission = annotation.requirePermission();

                    if (!permissions.contains(requirePermission)) {
                        Permission permission = new Permission();
                        permission.setPermissionName(permissionName);
                        permission.setRequiredPermission(requirePermission);
                        permissionMapper.insert(permission);
                    }
                }
            }
        }
        return "ok";
    }
}




