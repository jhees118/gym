package yuhan_3_2.EasyGym.controller;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yuhan_3_2.EasyGym.entity.GymPosition;
import yuhan_3_2.EasyGym.repository.GymPositionRepository;
import yuhan_3_2.EasyGym.service.GymPositionService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Controller


public class GymPositionController{

    @Autowired
    private GymPositionService gymPositionService;
    @Autowired
    private GymPositionRepository gymPositionRepository;
    @GetMapping("/view")
    public String view(Model model) {

        List<GymPosition> files = gymPositionRepository.findAll();
        model.addAttribute("all",files);
        return "view";
    }

    @GetMapping("/upload")
    public String testUploadForm() {

        return "/upload";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("files") List<MultipartFile> files) throws IOException {
        gymPositionService.saveFile(file);

        for (MultipartFile multipartFile : files) {
            gymPositionService.saveFile(multipartFile);
        }

        return "/upload";
    }
    @GetMapping("/images/{id}")
    @ResponseBody
    public UrlResource gymImage(@PathVariable("id") Long id, Model model) throws IOException{

        GymPosition file = gymPositionRepository.findById(id).orElse(null);
        return new UrlResource("file:" +file.getSavedPath());
    }

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @GetMapping("/video/{id}")
    public ResponseEntity<ResourceRegion> gymVideo(@PathVariable("id") Long id,@RequestHeader HttpHeaders headers) throws IOException {
       logger.debug("VideoController.getVideo");
    GymPosition file = gymPositionRepository.findById(id).orElse(null);
        UrlResource video = new UrlResource("file:"+file.getSavedPath());
        ResourceRegion resourceRegion;

        final long chunkSize = 1000000L;
        long contentLength = video.contentLength();

        Optional<HttpRange> optional = headers.getRange().stream().findFirst();
        HttpRange httpRange;
        if (optional.isPresent()) {
            httpRange = optional.get();
            long start = httpRange.getRangeStart(contentLength);
            long end = httpRange.getRangeEnd(contentLength);
            long rangeLength = Long.min(chunkSize, end - start + 1);
            resourceRegion = new ResourceRegion(video, start, rangeLength);
        } else {
            long rangeLength = Long.min(chunkSize, contentLength);
            resourceRegion = new ResourceRegion(video, 0, rangeLength);
        }

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(resourceRegion);
    }

}
