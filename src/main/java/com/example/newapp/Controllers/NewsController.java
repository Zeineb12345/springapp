package com.example.newapp.Controllers;

import com.example.newapp.Entities.News;
import com.example.newapp.Repositories.NewsRepository;
 import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class NewsController {
    private final NewsRepository newsRepository;

    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/upload";

    public NewsController(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @PostMapping("/ad")
    public String addBlog(@Valid News news, BindingResult result,
                          @RequestParam("files") MultipartFile[] files) {
        /// part upload
        StringBuilder fileName = new StringBuilder();
        MultipartFile file = files[0];
        Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
        fileName.append(file.getOriginalFilename());
        try {
            Files.write(fileNameAndPath, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        news.setPhoto(fileName.toString());
        newsRepository.save(news);
        return "listnew";


    }

    @GetMapping("/list")
//@ResponseBody
    public String listAct(Model model) {
        model.addAttribute("news", newsRepository.findAll());

        return "listnew";
    }


    @GetMapping("/add")
    public String ShowAddForm(News news, Model model) {
        model.addAttribute("news", new News());

        return "addnew";
    }

    @GetMapping("delete/{id}")
    public String deleteProvider(@PathVariable("id") long id, Model model) {

        //long id2 = 100L;
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException());

        newsRepository.delete(news);
        model.addAttribute("articles", newsRepository.findAll());
        return "/listnew";
    }

    @GetMapping("show/{id}")
    public String showDetails(@PathVariable("id") long id, Model model) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException());

        model.addAttribute("news", news);

        return "showNews";
    }
}
