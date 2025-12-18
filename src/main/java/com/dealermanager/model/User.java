package com.dealermanager.model;

import com.dealermanager.constants.Constants;
import java.util.Objects;
import java.util.Optional;

import com.dealermanager.entity.UserEntity;
import lombok.Data;

@Data
public class User {

    UserEntity entity;

    String identity;

    public User(UserEntity userEntity){
        this.entity = userEntity;
    }

    public Long getUserId() {
        return entity.getId();
    }

    public String getMobile() {
        return entity.getMobile();
    }

    public Long getCompanyID() {
        return entity.getCompanyID();
    }

    /**
     * 判断是否为正式注册已审核用户
     *
     * @return true/false
     */
    public boolean isFormalUser() {
        return getEntity() != null && Objects.equals(getEntity().getIsValid(), Constants.VALID_STATUS) ;
    }
}
