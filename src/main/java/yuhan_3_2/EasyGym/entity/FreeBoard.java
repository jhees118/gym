package yuhan_3_2.EasyGym.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="free_board")
public class FreeBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min=2,max=20,message = "제목은 2글자에서 20글자여야합니다.")
    private String title;
    @NotNull
    @Size(min = 1,message = "내용을 입력해주세요")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "freeBoard",cascade = CascadeType.REMOVE)
    private List<Heart> hearts = new ArrayList<>();

    @OrderBy("id desc")
    @JsonIgnoreProperties({"freeBoard"})
    @OneToMany(mappedBy = "freeBoard",cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

}
