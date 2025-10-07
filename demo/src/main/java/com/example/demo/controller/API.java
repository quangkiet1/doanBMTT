package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.user;
import com.example.demo.security.AESUtil;
import com.example.demo.service.logic;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class API {

    @Autowired
    private logic userService;

    // Lấy toàn bộ danh sách user
    @GetMapping
    public List<user> getUsers() {
        return userService.getAllUsers();
    }

    // Thêm user mới (tự động mã hóa password)
    @PostMapping
    public user addUser(@RequestBody user user) {
        return userService.addUser(user);
    }

    // API đăng nhập
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody user loginUser) {
        user existingUser = userService.findByUsername(loginUser.getUsername());
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Tài khoản không tồn tại");
        }

        String decryptedPassword = AESUtil.decrypt(existingUser.getPassword());
        if (decryptedPassword.equals(loginUser.getPassword())) {
            return ResponseEntity.ok(existingUser); // Đăng nhập thành công
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Sai mật khẩu");
        }
    }
}
