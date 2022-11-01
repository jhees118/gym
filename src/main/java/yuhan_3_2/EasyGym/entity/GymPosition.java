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
    @Column(name="gym_name")
    private String gymName;
    @Column(name="gym_ori_name")
    private String gymOriName;
    @Column(name="gym_url")
    private String gymUrl;
}