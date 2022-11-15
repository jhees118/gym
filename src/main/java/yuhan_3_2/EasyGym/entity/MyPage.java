package yuhan_3_2.EasyGym.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.text.DateFormat;
import java.util.Date;

@Entity
@Data
@Table(name="my_page")
public class MyPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String day;


    @Positive
    @Min(1)@Max(9999)
    private int calorie;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;




}
