package yuhan_3_2.EasyGym.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import yuhan_3_2.EasyGym.entity.FreeBoard;
import yuhan_3_2.EasyGym.entity.GymPosition;
import yuhan_3_2.EasyGym.entity.User;
import yuhan_3_2.EasyGym.entity.VideoBoard;
import yuhan_3_2.EasyGym.repository.UserRepository;
import yuhan_3_2.EasyGym.repository.VideoBoardRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class VideoBoardService {
    @Value("${userVideo.dir}")
    private String userVideoDir;

    @Autowired
    private VideoBoardRepository videoBoardRepository;
    @Autowired
    private UserRepository userRepository;

    public Long saveVideoFile(MultipartFile file, VideoBoard videoBoard, String username) throws IOException {
        if (file.isEmpty()) {
            return null;
        }
        User user = userRepository.findByUsername(username);
        // 원래 파일 이름 추출
        String origName = file.getOriginalFilename();

        // 파일 이름으로 쓸 uuid 생성
        String uuid = UUID.randomUUID().toString();

        // 확장자 추출(ex : .png)
        String extension = origName.substring(origName.lastIndexOf("."));

        // uuid와 확장자 결합
        String savedName = uuid + extension;

        // 파일을 불러올 때 사용할 파일 경로
        String savedPath = userVideoDir + savedName;


        // 파일 엔티티 생성
        VideoBoard videoBoard1 = new VideoBoard();
        videoBoard1.setOrgNm(origName);
        videoBoard1.setVideoHeartCount(0);
        videoBoard1.setSavedPath(savedPath);
        videoBoard1.setContent(videoBoard.getContent());
        videoBoard1.setTitle(videoBoard.getTitle());
        videoBoard1.setSavedNm(savedName);
        videoBoard1.setVideoViewCount(0);
        videoBoard1.setUser(user);


        // 실제로 로컬에 uuid를 파일명으로 저장
        file.transferTo(new File(savedPath));

        // 데이터베이스에 파일 정보 저장
      VideoBoard savedFile = videoBoardRepository.save(videoBoard1);

        return savedFile.getId();
    }
    public VideoBoard videoView(Long id){

        return videoBoardRepository.findById(id).get();
    }
    public void videoDelete(Long id){
        videoBoardRepository.deleteById(id);
    }

    public List<VideoBoard> videoList(){
        return videoBoardRepository.findAll();
    }

    public Page<VideoBoard> videoHeartList(Pageable pageable){ return videoBoardRepository.findAll(pageable); }//좋아요순 리스트를위한 pageable

}
