package yuhan_3_2.EasyGym.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="freeBoard")
public class freeBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;
}
