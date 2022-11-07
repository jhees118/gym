package yuhan_3_2.EasyGym.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yuhan_3_2.EasyGym.entity.Comment;
import yuhan_3_2.EasyGym.entity.FreeBoard;
import yuhan_3_2.EasyGym.entity.GymPosition;
import yuhan_3_2.EasyGym.entity.User;
import yuhan_3_2.EasyGym.repository.GymPositionRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class GymPositionService {

    @Value("${gymPosition.dir}")
    private String gymPositionDir;

    @Autowired
    private GymPositionRepository gymPositionRepository;

    public Long saveGymFile(MultipartFile files, GymPosition gymPosition,String username) throws IOException {
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
        GymPosition file = new GymPosition();
                file.setOrgNm(origName);
                file.setGymHeartCount(0);
                file.setSavedPath(savedPath);
                file.setPosition(gymPosition.getPosition());
                file.setContent(gymPosition.getContent());
                file.setTitle(gymPosition.getTitle());
                file.setSavedNm(savedName);
                file.setGymViewCount(0);

        // 실제로 로컬에 uuid를 파일명으로 저장
        files.transferTo(new File(savedPath));

        // 데이터베이스에 파일 정보 저장
        GymPosition savedFile = gymPositionRepository.save(file);

        return savedFile.getId();
    }
    public List<GymPosition> gymArmList(){
        return gymPositionRepository.findByPosition("arm");
    }
    public List<GymPosition> gymBackList(){
        return gymPositionRepository.findByPosition("back");
    }
    public List<GymPosition> gymChestList(){
        return gymPositionRepository.findByPosition("chest");
    }
    public List<GymPosition> gymShoulderList(){
        return gymPositionRepository.findByPosition("shoulder");
    }
    public List<GymPosition> gymAbsList(){
        return gymPositionRepository.findByPosition("abs");
    }
    public List<GymPosition> gymLegList(){
        return gymPositionRepository.findByPosition("leg");
    }

    public Page<GymPosition> gymLegHeartList(Pageable pageable){ return gymPositionRepository.findByPosition("leg",pageable); }//좋아요순 리스트를위한 pageable

    public GymPosition view(Long id){

        return gymPositionRepository.findById(id).get();
    }


}