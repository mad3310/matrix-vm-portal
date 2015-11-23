package com.letv.portal.service.openstack.util.tuple;

/**
 * Created by zhouxianguang on 2015/11/2.
 */
public class Tuple3<T1, T2, T3> {
    public T1 _1;
    public T2 _2;
    public T3 _3;

    public Tuple3() {
    }

    public Tuple3(T1 _1, T2 _2, T3 _3) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
    }
}
