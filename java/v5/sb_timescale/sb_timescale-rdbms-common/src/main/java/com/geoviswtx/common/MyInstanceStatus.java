package com.geoviswtx.common;

import com.google.common.collect.Lists;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Status of the job instance
 *
 * @author tjq
 * @since 2020/3/17
 */
@Getter
@AllArgsConstructor
public enum MyInstanceStatus {
    /**
     *
     */
    WAITING_DISPATCH(1, "等待派发"),
    WAITING_WORKER_RECEIVE(2, "等待Worker接收"),
    RUNNING(3, "运行中"),
    FAILED(4, "失败"),
    SUCCEED(5, "成功"),
    CANCELED(9, "取消"),
    STOPPED(10, "手动停止"),
    TIMEOUT(11, "超时");

    private final int v;
    private final String des;

    /**
     * 广义的运行状态
     */
    public static final List<Integer> GENERALIZED_RUNNING_STATUS = Lists.newArrayList(WAITING_DISPATCH.v, WAITING_WORKER_RECEIVE.v, RUNNING.v);
    /**
     * 结束状态
     */
    public static final List<Integer> FINISHED_STATUS = Lists.newArrayList(FAILED.v, SUCCEED.v, CANCELED.v, STOPPED.v);

    public static MyInstanceStatus of(int v) {
        for (MyInstanceStatus is : values()) {
            if (v == is.v) {
                return is;
            }
        }
        throw new IllegalArgumentException("InstanceStatus has no item for value " + v);
    }
}
