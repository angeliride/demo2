package com.example.demo2;

import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
public class RectangleController {
    //Zadanie 3
    List<Rectangle> rectangles = new ArrayList<>();

    public RectangleController() {
        rectangles.add(new Rectangle(10, 20, 100, 200, "red"));
        rectangles.add(new Rectangle(150, 50, 300, 100, "blue"));
    }

    @GetMapping("rectangle")
    public Rectangle getRectangle() {
        Rectangle rectangle = new Rectangle(20, 10, 100, 213, "red");
        return rectangle;
    }  //dostaliśmy JSON

    //SVG
    @GetMapping("toSvg")
    public String toSvg() {
        return generateSvg(rectangles);
    }

    public String generateSvg(List<Rectangle> rectangles) {
        final int SVG_WIDTH = 1000;
        final int SVG_HEIGHT = 1000;

        StringBuilder rectanglesSvg = new StringBuilder();
        rectanglesSvg.append(String.format("<svg width=\"1000\" height=\"1000\">", SVG_WIDTH, SVG_HEIGHT));
        for (Rectangle rect : rectangles) {
            rectanglesSvg.append(formatRectangle(rect));
        }
        rectanglesSvg.append("</svg>");
        return rectanglesSvg.toString();
    }

    private String formatRectangle(Rectangle rect) {
        return String.format("<rect width=\"%d\" height=\"%d\" x=\"%d\" y=\"%d\" fill=\"%s\"/>",
                rect.getWidth(), rect.getHeight(), rect.getX(), rect.getY(), rect.getColor());
    }


    //zadanie 4
    @PostMapping("addRectangle")
    public int addRectangle() {
        Rectangle rectangle = new Rectangle(20, 10, 100, 213, "red");
        rectangles.add(rectangle);
        return rectangles.size();
    }

    @GetMapping("rectangles")
    public List<Rectangle> getRectangles() {
        return rectangles;
    }
    //zadanie5
    @GetMapping("rectangle/{id}")
    public Rectangle getRectangle(@PathVariable Long id) throws IOException {

        return rectangles.get(id.intValue());
    }
    @PutMapping("rectangle/{id}")
    public void updateRectangle (@PathVariable int id, @RequestBody Rectangle rectangle) {
        rectangles.set(id, rectangle);
    }
    @DeleteMapping("rectangle/{id}")
    public void deleteRectangle(@PathVariable int id) {
        rectangles.remove(id);
    }
}
