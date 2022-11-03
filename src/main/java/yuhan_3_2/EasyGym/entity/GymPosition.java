package yuhan_3_2.EasyGym.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private
    String gymTitle;
    @Builder
    public GymPosition(Long id, String orgNm, String savedNm, String savedPath,String position,String content,String gymTitle) {
        this.id = id;
        this.orgNm = orgNm;
        this.savedNm = savedNm;
        this.savedPath = savedPath;
        this.position = position;
        this.content = content;
        this.gymTitle = gymTitle;
    }
}