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
    @Column(name="position")
    private String position;
    @Column(name="content")
    private String content;
    @Column(name="gym_title")
    private String title;
    @Column(name="gym_heart_count")
    private int gymHeartCount;


    @OneToMany(mappedBy = "gymPosition",cascade = CascadeType.REMOVE)
    private List<GymHeart> gymHearts = new ArrayList<>();
    @OneToMany(mappedBy = "gymPosition",cascade = CascadeType.REMOVE)
    private List<GymComment> gymComments = new ArrayList<>();


}