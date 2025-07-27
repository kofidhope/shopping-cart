package com.dhopecode.shoppingCart.service.user;

import com.dhopecode.shoppingCart.model.User;
import com.dhopecode.shoppingCart.requests.CreateUserRequest;
import com.dhopecode.shoppingCart.requests.UserUpdateRequest;

public interface iUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser (UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

}
