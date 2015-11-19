package com.letv.portal.cloudvm.test;

import com.google.common.collect.ImmutableSet;
import com.google.common.io.Closeables;
import com.google.inject.Module;
import com.letv.portal.service.openstack.util.tuple.Tuple3;
import org.jclouds.ContextBuilder;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.nova.v2_0.NovaApi;

import java.io.IOException;

/**
 * Created by zhouxianguang on 2015/6/5.
 */
public class TestNova {
    private final NovaApi api;

    public static void main(String[] args) throws Exception {
        for (Tuple3<String, String, String> tuple : MultiUser.getUserNameAndPassList()) {
            TestNova test = new TestNova(tuple);
            try {
                test.test();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                test.close();
            }
        }
    }

    private void test() {
    }

    private static long printTimeCost(String prompt, long time) {
        long currentTime = System.currentTimeMillis();
        System.out.print(prompt);
        System.out.print(currentTime - time);
        System.out.println();
        return System.currentTimeMillis();
    }

    TestNova(Tuple3<String, String, String> tuple) throws IOException, ClassNotFoundException {
        Iterable<Module> modules = ImmutableSet.<Module>of(new SLF4JLoggingModule());

        String provider = "openstack-nova";
        String endpoint = "http://10.58.241.211:5000/v2.0/";
        String identity = tuple._2; // tenantName:userName
        String credential = tuple._3;

        long beginTime = System.currentTimeMillis();
        long time = beginTime;

        ContextBuilder builder = ContextBuilder.newBuilder(provider);
        time = printTimeCost("ContextBuilder.newBuilder ", time);

        builder = builder.endpoint("http://10.58.241.211:5000/v2.0/");
        time = printTimeCost("builder.endpoint ", time);

//        builder = builder.credentials(identity, credential);
//        time = printTimeCost("builder.credentials ", time);

        builder = builder.modules(modules);
        time = printTimeCost("builder.modules ", time);

        api = builder.buildApi(NovaApi.class);
        printTimeCost("builder.buildApi ", time);

        long endTime = System.currentTimeMillis();
        System.out.print(tuple._1 + " ");
        System.out.print(endTime - beginTime);
        System.out.println("\n----------------------------");

        builder = builder.credentials(identity, credential);

        api.getServerApi("cn-beijing-1").list().concat().toList();
    }

    public void close() throws IOException {
        Closeables.close(api, true);
    }
}
