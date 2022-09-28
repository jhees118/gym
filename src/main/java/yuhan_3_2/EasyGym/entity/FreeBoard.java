package yuhan_3_2.EasyGym.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    @Size(min = 0,max =1000 ,message = "글자가 1000자 이상입니다.")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
