package com.puhui.email.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @description:  用于修改多个用户传参
 * @author: 杨利华
 * @date: 2020/7/6
 */
public class MailUserList  implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<MailUser> list;

    public List<MailUser> getList() {
        return list;
    }
    public void setList(List<MailUser> list) {
        this.list = list;
    }
}
