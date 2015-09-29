package com.letv.portal.service.openstack.util;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;

public class Contants {
    public static final String OPEN_STACK_USER_AGENT = "Matrix-Portal";
    public static final Iterable<Module> jcloudsContextBuilderModules = ImmutableSet
            .<Module>of(new SLF4JLoggingModule());
}
