
package com.example.qrscanner;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class QRController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/scan")
    public String scanQRCode(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {
            Result result = new MultiFormatReader().decode(bitmap);
            model.addAttribute("qrResult", result.getText());
        } catch (NotFoundException e) {
            model.addAttribute("qrResult", "QR Code not found.");
        }

        return "result";
    }
}
