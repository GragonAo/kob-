package com.kob.backend.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BaseRequest {
    @Data
    public static class PageParams {
        @NotNull
        private Integer page = 1;
        @NotNull
        private Integer pageSize = 10;
    }
    @Data
    public static class PageResult<T> {
        /** 列表数据 */
        private T items;
        /** 总条数 */
        private Integer counts;
        /** 当前页数 */
        private Integer page;
        /** 总页数 */
        private Integer pages;
        /** 每页条数 */
        private Integer pageSize;
    }
}
