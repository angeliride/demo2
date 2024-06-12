package com.example.demo2;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@RestController
public class ImageController {


    public void increaseBrightness(BufferedImage image, int factor) {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int pixel = image.getRGB(x, y);
                pixel = brightenPixel(pixel, factor);
                image.setRGB(x, y, pixel);
            }
        }
    }


    private int brightenPixel(int pixel, int factor) {
        int mask = 255;
        int blue = pixel & mask;
        int green = (pixel >> 8) & mask;
        int red = (pixel >> 16) & mask;
        blue = brightenPixelPart(blue, factor);
        green = brightenPixelPart(green, factor);
        red = brightenPixelPart(red, factor);
        return blue + (green << 8) + (red << 16);
    }


    private int brightenPixelPart(int color, int factor) {
        color += factor;
        if (color > 255) {
            return 255;
        } else {
            return color;
        }
    }

    @GetMapping("/brightenImage")
    public ResponseEntity<String> increaseImageBrightness(@RequestBody ImageExample example) throws IOException {
        if (example.getImageBase() == null || example.getImageBase().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Image base64 string is null or empty");
        }

        try {
            byte[] imageBytes = Base64.getDecoder().decode(example.getImageBase());
            BufferedImage bf = ImageIO.read(new ByteArrayInputStream(imageBytes));

            increaseBrightness(bf, example.getFactor());

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write(bf, "png", stream);
            byte[] newImage = stream.toByteArray();

            String response = Base64.getEncoder().encodeToString(newImage);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Base64 input");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing image");
        }
    }


    @GetMapping("/brightenImageBinary")
    public ResponseEntity<byte[]> increaseImageBrightnessBinary(@RequestBody ImageExample request) throws IOException {
        byte[] imageBytes = Base64.getDecoder().decode(request.getImageBase());
        BufferedImage bf = ImageIO.read(new ByteArrayInputStream(imageBytes));
        increaseBrightness(bf, request.getFactor());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(bf, "png", stream);
        byte[] newImage = stream.toByteArray();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_PNG);
        httpHeaders.setContentLength(newImage.length);

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(newImage);
    }
}

