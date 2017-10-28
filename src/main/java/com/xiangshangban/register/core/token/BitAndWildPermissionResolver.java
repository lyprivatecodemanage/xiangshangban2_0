package com.xiangshangban.register.core.token;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

/**
 * Created by sang on 17-3-19.
 */
public class BitAndWildPermissionResolver implements PermissionResolver {
    public Permission resolvePermission(String permissionString) {
        //请求参数是否已 $ 符号开头
        if (permissionString.startsWith("$")) {
            return new BitPermission(permissionString);
        }
        return new WildcardPermission(permissionString);
    }
}
