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

@Service
@Transactional
public class GymPositionService {

    @Autowired
    GymPositionRepository gymPositionRepository;

    @Transactional
    public void gymSave(GymPosition gymPosition){
        GymPosition f = new GymPosition();

        f.setGymName(gymPosition.getGymName());
        f.setGymOriName(gymPosition.getGymOriName());
        f.setGymUrl(gymPosition.getGymUrl());

        gymPositionRepository.save(f);
    }




}