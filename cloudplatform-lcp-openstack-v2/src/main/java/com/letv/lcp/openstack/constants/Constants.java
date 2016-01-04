package com.letv.lcp.openstack.constants;

import java.util.regex.Pattern;

import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;

public class Constants {
    public static final String OPEN_STACK_USER_AGENT = "Matrix-Portal";
    public static final Iterable<Module> jcloudsContextBuilderModules = ImmutableSet
            .<Module>of(new SLF4JLoggingModule());
    public static final Pattern userAdminPasswordPattern = Pattern.compile(ValidationRegex.password);
    public static final int nameMaxLength = 128;
}
