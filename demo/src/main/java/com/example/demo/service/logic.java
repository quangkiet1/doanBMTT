package com.example.demo.service;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.user;
import com.example.demo.repository.UserRepository;
import java.util.List;
import com.example.demo.security.AESUtil;

@Service
public class logic {
    @Autowired
    private UserRepository userRepo;

    public List<user> getAllUsers() {
    List<user> users = userRepo.findAll();
    for (user u : users) {
        try {
            String pw = u.getPassword();
            if (pw != null && !pw.isBlank()) {
                // kiểm tra có thể giải mã không
                u.setPassword(AESUtil.decrypt(pw));
            }
        } catch (Exception e) {
            // nếu lỗi thì giữ nguyên (không crash app)
            System.err.println("Lỗi giải mã cho user " + u.getUsername() + ": " + e.getMessage());
        }
    }
    return users;
}


    public user addUser(user user) {
        // Mã hóa mật khẩu trước khi lưu
        user.setPassword(AESUtil.encrypt(user.getPassword()));
        return userRepo.save(user);
    }
    
    public user findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

}


