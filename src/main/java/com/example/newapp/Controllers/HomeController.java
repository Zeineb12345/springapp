package com.example.newapp.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String home() {
        return "index.html";
    }

    public String Login() {
        return "**";
    }

    public String Registration() {
        return "**";
    }
    @RequestMapping("/contact")
    public String contact(){
        return "contact.html";
}

    @RequestMapping("/about")
    public String about(){
        return "about.html";}
    @RequestMapping("/news")
    public String news(){
        return "newss.html";
}
   }