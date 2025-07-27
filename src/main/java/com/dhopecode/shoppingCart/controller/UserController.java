package com.dhopecode.shoppingCart.controller;

import com.dhopecode.shoppingCart.exceptions.AlreadyExistException;
import com.dhopecode.shoppingCart.exceptions.ResourceNotFoundException;
import com.dhopecode.shoppingCart.model.User;
import com.dhopecode.shoppingCart.requests.CreateUserRequest;
import com.dhopecode.shoppingCart.requests.UserUpdateRequest;
import com.dhopecode.shoppingCart.response.ApiResponse;
import com.dhopecode.shoppingCart.service.user.UserService;
import com.dhopecode.shoppingCart.service.user.iUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final iUserService userService;

    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId){
        try {
            User user = userService.getUserById(userId);
            return ResponseEntity.ok(new ApiResponse("Success", user));
        }  catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request){
        try {
            User user = userService.createUser(request);
            return ResponseEntity.ok(new ApiResponse("User created Successfully", user));
        } catch (AlreadyExistException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request,@PathVariable Long userId){
        try {
            User user = userService.updateUser(request, userId);
            return ResponseEntity.ok(new ApiResponse("User updated Successfully", user));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("User deleted Successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
