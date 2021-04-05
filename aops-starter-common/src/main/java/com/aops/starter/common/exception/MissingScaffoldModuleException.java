package com.aops.starter.common.exception;

import java.util.List;

public class MissingScaffoldModuleException extends RuntimeException{
    private final List<String> missingModules;

    public MissingScaffoldModuleException(List<String> missingModules){
        this.missingModules = missingModules;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("missing scaffold modules!\n\n");
        sb.append("请在pom.xml文件中增加如下依赖：\n\n");
        for (String missingModule : missingModules){
            sb.append("   <dependency> \n");
            sb.append("        <groupId>com.pingan.property</groupId>\n");
            sb.append("        <artifactId>").append(missingModule).append("</artifactId>\n");
            sb.append("   </dependency> \n\n");
        }
        return sb.toString();
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
