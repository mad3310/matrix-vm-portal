package com.letv.portal.service.openstack.util.tuple;

/**
 * Created by zhouxianguang on 2015/11/2.
 */
public class Tuple2<T1,T2> {
    public T1 _1;
    public T2 _2;

    public Tuple2() {
    }

    public Tuple2(T1 _1, T2 _2) {
        this._1 = _1;
        this._2 = _2;
    }
}
