//package com.bitirmeproje.controller;
//
//import com.bitirmeproje.model.User;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//public class Deneme {
//
//    @GetMapping("deneme")
//    public ResponseEntity<Map<String, String>> denemeGet() {
//        HashMap<String,String> deneme = new HashMap<>();
//        deneme.put("isim","fatih");
//        deneme.put("soyisim","çokan");
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(deneme);
//    }
//
//    @PostMapping("denemePost")
//    public void denemePost(@RequestBody User user) {
//        System.out.println(user.getIsim());
//        System.out.println(user.getSoyisim());
//    }
//}
