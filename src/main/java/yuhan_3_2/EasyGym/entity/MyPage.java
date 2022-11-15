package yuhan_3_2.EasyGym.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name="my_page")
public class MyPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String year;
    @NotNull
    private String month;
    @NotNull
    private String day;
    @NotNull
    private String content;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
