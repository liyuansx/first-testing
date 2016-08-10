package com.ningpai.site.goods.bean;

import java.util.Date;

/**
 * 用户分享关注实体Bean
 * Created by tianchanglong on 2015/10/15.
 */
public class ShareCommentAtte {
    //主键Id
    private Long attId;
    //用户Id
    private Long customerId;
    //评论Id
    private Long commentid;
    //用户分享Id
    private Long publicUserId;
    //创建时间
    private Date createTime;
    // 修改时间
    private Date modifyTime;
    // 删除时间
    private Date delTime;
    // 删除标记
    private String delFlag;

    public Long getPublicUserId() {
        return publicUserId;
    }

    public void setPublicUserId(Long publicUserId) {
        this.publicUserId = publicUserId;
    }

    public Long getAttId() {
        return attId;
    }

    public void setAttId(Long attId) {
        this.attId = attId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCommentid() {
        return commentid;
    }

    public void setCommentid(Long commentid) {
        this.commentid = commentid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getDelTime() {
        return delTime;
    }

    public void setDelTime(Date delTime) {
        this.delTime = delTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
