package yuhan_3_2.EasyGym.controller;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import yuhan_3_2.EasyGym.entity.GymPosition;
import yuhan_3_2.EasyGym.service.GymPositionService;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;


@RequiredArgsConstructor
@Controller
public class GymPositionController{

    @Autowired
    private GymPositionService gymPositionService;




    @GetMapping("/gymUpload")
    public String uploadFile(@RequestPart MultipartFile file) throws IOException {
        GymPosition gymPosition = new GymPosition();
        String sourceFileName = file.getOriginalFilename();
        String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();

        FilenameUtils.removeExtension(sourceFileName);

        File destinationFile;
        String destinationFileName;
        String fileUrl = "C:\\Users\\YUHAN\\Desktop\\gym\\gym\\src\\main\\resources\\static\\gym\\";

        do{
            destinationFileName = RandomStringUtils.randomAlphanumeric(32)+"."+sourceFileNameExtension;
            destinationFile = new File(fileUrl + destinationFileName);
        }while (destinationFile.exists());

        destinationFile.getParentFile().mkdirs();
        gymPosition.setGymName(destinationFileName);
        gymPosition.setGymOriName(sourceFileName);
        gymPosition.setGymUrl(fileUrl);
        gymPositionService.gymSave(gymPosition);

        return "redirect:/gymUpload";
    }

}
