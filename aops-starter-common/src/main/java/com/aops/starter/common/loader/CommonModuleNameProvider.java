package com.aops.starter.common.loader;

import com.aops.starter.common.CommonConstants;
import com.aops.starter.common.version.ModuleNameProvider;

public class CommonModuleNameProvider implements ModuleNameProvider {
    @Override
    public String getModuleName() {
        return CommonConstants.COMMON_MODULE;
    }
}
