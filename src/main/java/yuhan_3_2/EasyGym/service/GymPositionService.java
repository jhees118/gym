package yuhan_3_2.EasyGym.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yuhan_3_2.EasyGym.entity.FreeBoard;
import yuhan_3_2.EasyGym.entity.GymPosition;
import yuhan_3_2.EasyGym.entity.User;
import yuhan_3_2.EasyGym.repository.GymPositionRepository;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;
import java.nio.file.Path;
@RequiredArgsConstructor
@Service
public class GymPositionService {

    @Value("${gymPosition.dir}")
    private String gymPositionDir;

    @Autowired
    private GymPositionRepository gymPositionRepository;

    public Long saveFile(MultipartFile files) throws IOException {
        if (files.isEmpty()) {
            return null;
        }

        // 원래 파일 이름 추출
        String origName = files.getOriginalFilename();

        // 파일 이름으로 쓸 uuid 생성
        String uuid = UUID.randomUUID().toString();

        // 확장자 추출(ex : .png)
        String extension = origName.substring(origName.lastIndexOf("."));

        // uuid와 확장자 결합
        String savedName = uuid + extension;

        // 파일을 불러올 때 사용할 파일 경로
        String savedPath = gymPositionDir + savedName;

        // 파일 엔티티 생성
        GymPosition file = GymPosition.builder()
                .orgNm(origName)
                .savedNm(savedName)
                .savedPath(savedPath)
                .build();

        // 실제로 로컬에 uuid를 파일명으로 저장
        files.transferTo(new File(savedPath));

        // 데이터베이스에 파일 정보 저장
        GymPosition savedFile = gymPositionRepository.save(file);

        return savedFile.getId();
    }



}