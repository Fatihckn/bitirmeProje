package com.bitirmeproje.controller;

import com.bitirmeproje.dto.user.UserAllDto;
import com.bitirmeproje.dto.user.UserUpdateDto;
import com.bitirmeproje.service.admin.IAdminUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/user")
public class AdminUserController {

    private final IAdminUserService adminUserService;

    public AdminUserController(IAdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    // Bütün kullanıcılar getiriliyor.
    @GetMapping("/allUsers")
    public List<UserAllDto> findAll(){return adminUserService.findAll();}

    // Aradığın kullanıcının bilgileri getiriliyor
    @GetMapping("/arama")
    public ResponseEntity<List<UserAllDto>> searchUsers(@RequestParam String query) {

        List<UserAllDto> users = adminUserService.searchUsers(query);
        return ResponseEntity.ok(users);
    }

    // Kullanıcı bilgilerini yenile(burada parametreden gelen id adminin değil, değişiklik yapmak istediği kullanıcıya ait)
    @PutMapping("/{id}/kullanici-bilgi-yenile")
    public ResponseEntity<String> updateUser(@PathVariable int id, @RequestBody UserUpdateDto userUpdateDto){

        adminUserService.updateUser(id, userUpdateDto);
        return ResponseEntity.ok("Kullanıcı bilgileri başarıyla güncellendi.");
    }

}
