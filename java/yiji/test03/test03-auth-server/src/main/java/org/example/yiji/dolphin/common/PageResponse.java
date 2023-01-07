package org.example.yiji.dolphin.org.examplemon;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Description:
 * @Author wanglin
 * @Date 2021/1/18 15:33
 */
public class PageResponse<T> {

    /**
     * 元素总数
     */
    private long TotalElements;
    /**
     * 页数
     */
    private int TotalPages;
    /**
     * 内容
     */
    private List<T> content;

    public long getTotalElements() {
        return TotalElements;
    }

    public void setTotalElements(long totalElements) {
        TotalElements = totalElements;
    }

    public int getTotalPages() {
        return TotalPages;
    }

    public void setTotalPages(int totalPages) {
        TotalPages = totalPages;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public PageResponse(Page<T> page) {
        this.TotalElements = page.getTotalElements();
        this.TotalPages = page.getTotalPages();
        this.content = page.getContent();
    }
    public PageResponse(PageResponse res,List<T> list) {
        this.TotalElements = res.getTotalElements();
        this.TotalPages = res.getTotalPages();
        this.content = list;
    }
    public PageResponse() {
    }
}
