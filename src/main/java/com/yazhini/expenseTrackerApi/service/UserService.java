package com.yazhini.expenseTrackerApi.service;

import com.yazhini.expenseTrackerApi.entity.UserEntity;
import com.yazhini.expenseTrackerApi.entity.UserModel;

public interface UserService {
    UserEntity createUser(UserModel user);

    UserEntity read (Long id);

    UserEntity update(UserEntity user, Long id);

    void delete (Long id);

    UserEntity getLoggedInUser();
}
