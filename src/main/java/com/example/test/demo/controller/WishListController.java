package com.example.test.demo.controller;

import com.example.test.demo.model.Wish;
import com.example.test.demo.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishList")
public class WishListController {
    @Autowired
    WishListService wishListService;

    @GetMapping("/all")
    public List<Wish> getWishList() throws Exception {
        return wishListService.getAllWishes();
    }
    @PostMapping("/create")
    public String createWishList(@RequestBody Wish wish) throws Exception {
        return wishListService.createWish(wish);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteWishList(@PathVariable int id) throws Exception {
        return wishListService.deleteWish(id);
    }
}
