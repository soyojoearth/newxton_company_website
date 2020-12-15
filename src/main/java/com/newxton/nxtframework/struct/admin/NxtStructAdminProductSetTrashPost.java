package com.newxton.nxtframework.struct.admin;

import java.util.List;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/12/4
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
public class NxtStructAdminProductSetTrashPost {
    /**
     * 产品id
     */
    private Long id;
    private Boolean isTrash;
    /**
     * 批量产品id数组，例如：[1,2,3]
     */
    private List<Long> idList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getTrash() {
        return isTrash;
    }

    public void setTrash(Boolean trash) {
        isTrash = trash;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }
}
