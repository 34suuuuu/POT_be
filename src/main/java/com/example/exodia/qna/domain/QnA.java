package com.example.exodia.qna.domain;


import com.example.exodia.board.domain.BoardFile;
import com.example.exodia.comment.domain.Comment;
import com.example.exodia.common.domain.BaseTimeEntity;
import com.example.exodia.common.domain.DelYN;
import com.example.exodia.department.domain.Department;
import com.example.exodia.qna.dto.QnAAtoUpdateDto;
import com.example.exodia.qna.dto.QnAListResDto;
import com.example.exodia.qna.dto.QnAQtoUpdateDto;
import com.example.exodia.user.domain.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "qna")
public class QnA extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(length = 3000, nullable = false)
    private String questionText;

    @Column(length = 3000)
    private String answerText;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User questioner;

    @ManyToOne
    @JoinColumn(name = "answerer_id")
    private User answerer;

    @Column
    private LocalDateTime answeredAt;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private DelYN delYN = DelYN.N;


    @OneToMany(mappedBy = "qna", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @Column(name = "secret_board", nullable = false)
    private Boolean secretBoard = false;

    @Column(name = "anonymous", nullable = false)
    private Boolean anonymous = false;

    @Builder.Default
    @OneToMany(mappedBy = "qna", cascade = CascadeType.ALL)
    private List<BoardFile> answererFiles = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "qna", cascade = CascadeType.ALL)
    private List<BoardFile> questionerFiles = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public QnAListResDto listFromEntity() {
        return QnAListResDto.builder()
                .id(this.id)
                .questionUserName(this.questioner.getName()) // 질문자 이름
                .title(this.getTitle())
                .createdAt(this.getCreatedAt())
                .updatedAt(this.getUpdatedAt())
                .answeredAt(this.answeredAt)
                .secretBoard(this.secretBoard)
                .anonymous(this.anonymous)
                .build();
    }


    public void updateDelYN(DelYN delYN){
        this.delYN = delYN;
    }

    public void QnAQUpdate(QnAQtoUpdateDto dto) {
        // 질문 제목과 질문 내용 업데이트
        this.title = dto.getTitle();
        this.questionText = dto.getQuestionText();

        // 비밀 여부 및 익명 여부 업데이트
        this.secretBoard = dto.getSecretBoard();
        this.anonymous = dto.getAnonymous();

        // 업데이트 시간을 현재 시간으로 설정
        this.setUpdatedAt(LocalDateTime.now());
    }


    public void QnAAUpdate(QnAAtoUpdateDto dto) {
        // 답변 내용 업데이트
        this.answerText = dto.getAnswerText();

        // 답변 시간이 현재 시간으로 설정
        this.answeredAt = LocalDateTime.now();

        // 업데이트 시간을 현재 시간으로 설정 (질문 또는 답변 수정 시)
        this.setUpdatedAt(LocalDateTime.now());
    }

}
