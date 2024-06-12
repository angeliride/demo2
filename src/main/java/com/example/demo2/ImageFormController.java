package com.example.demo2;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

@Controller
@RequestMapping("/")
public class ImageFormController {
    private final ImageController imageController;

    public ImageFormController(ImageController imageController) {
        this.imageController = imageController;
    }

    @GetMapping
    public String get(){
        return "index";
    }

    @PostMapping("/upload")
    public String handleFileUpload(Model model, MultipartFile image) {

        return "image";
    }

}
