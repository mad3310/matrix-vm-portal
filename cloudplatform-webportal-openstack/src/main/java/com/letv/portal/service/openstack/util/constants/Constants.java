package com.letv.portal.service.openstack.util.constants;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;
import com.letv.portal.service.openstack.billing.BillingResource;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;

import java.util.regex.Pattern;

public class Constants {
    public static final String OPEN_STACK_USER_AGENT = "Matrix-Portal";
    public static final Iterable<Module> jcloudsContextBuilderModules = ImmutableSet
            .<Module>of(new SLF4JLoggingModule());
    public static final Pattern userAdminPasswordPattern = Pattern.compile(ValidationRegex.password);
    public static final int nameMaxLength = 128;
}
