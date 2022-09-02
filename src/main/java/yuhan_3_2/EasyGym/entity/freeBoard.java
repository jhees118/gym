package yuhan_3_2.EasyGym.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name="freeBoard")
public class freeBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Size(min=2,max=20,message = "2글자이상20글자이하여야합니다.")
    private String title;
    private String content;
}
