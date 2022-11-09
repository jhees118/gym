package yuhan_3_2.EasyGym.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table(name = "gym_position")
public class GymPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="gym_ori_name")
    private String orgNm;
    @Column(name="gym_name")
    private String savedNm;
    @Column(name="gym_url")
    private String savedPath;

    @Column(name="gym_img_ori_name")
    private String imgOrgNm;
    @Column(name="gym_img_name")
    private String imgSavedNm;
    @Column(name="gym_img_url")
    private String imgSavedPath;

    @Column(name="position")
    private String position;
    @Column(name="gym_content")
    private String content;
    @Column(name="gym_title")
    private String title;
    @Column(name="gym_method")
    private String method;
    @Column(name="gym_notes")
    private String notes;
    @Column(name="gym_effect")
    private String effect;
    @Column(name="gym_heart_count")
    private int gymHeartCount;
    @Column(name="gym_view_count")
    private int gymViewCount;


    @OneToMany(mappedBy = "gymPosition",cascade = CascadeType.REMOVE)
    private List<GymHeart> gymHearts = new ArrayList<>();
    @OneToMany(mappedBy = "gymPosition",cascade = CascadeType.REMOVE)
    private List<GymComment> gymComments = new ArrayList<>();


}