package com.smarthome.spring.jpa.postgresql.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smarthome.services.ImageService;
import com.smarthome.spring.jpa.postgresql.model.Product;
import com.smarthome.spring.jpa.postgresql.repository.ProductRepository;

@Controller
public class ImageController {

    private final ImageService imageService;
    private final ProductRepository productRepository;

    public ImageController(ImageService imageService, ProductRepository productRepository) {
        this.imageService = imageService;
        this.productRepository = productRepository;
    }

    @GetMapping("recipe/{id}/image")
    public String showUploadForm(@PathVariable String id, Model model){
        model.addAttribute("product", productRepository.findById(Long.valueOf(id)));

        return "recipe/imageuploadform";
    }

    @PostMapping("recipe/{id}/image")
    public String handleImagePost(@PathVariable String id, @RequestParam("imagefile") MultipartFile file){

        imageService.saveImageFile(Long.valueOf(id), file);

        return "redirect:/recipe/" + id + "/show";
    }

    @GetMapping("recipe/{id}/recipeimage")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
        Optional<Product> product = productRepository.findById(Long.valueOf(id));

        if (product.get().getImage() != null) {
            byte[] byteArray = new byte[product.get().getImage().length];
            int i = 0;

            for (Byte wrappedByte : product.get().getImage()){
                byteArray[i++] = wrappedByte; //auto unboxing
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }
}
