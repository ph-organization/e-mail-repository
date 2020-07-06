package com.puhui.email.mapper;

import com.puhui.email.entity.MailUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: 杨利华
 * @date: 2020/7/5
 */
@Repository
public interface MailUserMapper extends JpaRepository<MailUser,Integer> {


}
