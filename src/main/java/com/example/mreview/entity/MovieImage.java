package com.example.mreview.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "movie") // 연관 관계 주의
public class MovieImage { // 이미지에 대한 정보

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inum;

    // 고유 번호
    private String uuid;

    private String imgName;

    // 년/월/일
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;
}
