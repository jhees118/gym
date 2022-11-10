package yuhan_3_2.EasyGym.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table(name = "video_board")
public class VideoBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="video_ori_name")
    private String orgNm;
    @Column(name="video_name")
    private String savedNm;
    @Column(name="video_url")
    private String savedPath;

    @NotNull
    @Size(min = 1,message = "내용을 입력해주세요")
    @Column(name="video_content")
    private String content;
    @NotNull
    @Size(min=2,max=20,message = "제목은 2글자에서 20글자여야합니다.")
    @Column(name="video_title")
    private String title;

    @Column(name="video_heart_count")
    private int videoHeartCount;
    @Column(name="video_view_count")
    private int videoViewCount;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @OneToMany(mappedBy = "videoBoard",cascade = CascadeType.REMOVE)
    private List<VideoHeart> videoHearts = new ArrayList<>();
    @OneToMany(mappedBy = "videoBoard",cascade = CascadeType.REMOVE)
    private List<VideoComment> videoComments = new ArrayList<>();


}