package com.bitirmeproje.controller;

import com.bitirmeproje.dto.UserDto;
import com.bitirmeproje.model.User;
import com.bitirmeproje.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public UserDto GetUser(){
        User user = userService.FindById(2);
        System.out.println(user.getKullaniciId());
        System.out.println(user.getKullaniciTakmaAd());
        System.out.println(user.getePosta());
        UserDto userDto = new UserDto();
        userDto.setePosta(user.getePosta());
        userDto.setKullaniciId(user.getKullaniciId());
        userDto.setKullaniciBio(user.getKullaniciBio());
        userDto.setKullaniciDogumTarihi(user.getKullaniciDogumTarihi());
        return userDto;
    }

    @GetMapping("/all")
    public List<UserDto> findAll(){
        List<UserDto> userDtos = userService.findAll();
        return userService.findAll();
    }

    @GetMapping("/allTakmaAd")
    public List<String> findAllTakmaAd(){
        return userService.findAllByKullaniciTakmaAd();
    }

    @GetMapping("/allDate")
    public List<String> findAllDate(){
        return userService.findAllByDogumTarihi();
    }

    @PostMapping("/saveKullanici")
    public String saveKullanici(@RequestBody User user){
        userService.save(user);
        return "success";
    }

    @GetMapping("/profil/{kullaniciAdi}")
    public Optional<User> findProfil(@PathVariable String kullaniciAdi){
        return userService.findByKullaniciTakmaAd(kullaniciAdi);
    }

    @GetMapping("/kullanicilar")
    public String requestParamDeneme(@RequestParam String kullaniciAdi){
        return kullaniciAdi;
    }

    @GetMapping("/profile/{kullaniciAdi}")
    public String requestParamDeneme2(@PathVariable String kullaniciAdi, @RequestParam String kullaniciId, @RequestParam String kullaniciBio){
        return kullaniciAdi + " " + kullaniciId + " " + kullaniciBio;
    }
}
