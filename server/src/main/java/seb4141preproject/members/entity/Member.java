package seb4141preproject.members.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter //get 함수를 일괄적으로 만들어 준다.
@Setter
@NoArgsConstructor // 기본 생성자를 만들어준다
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 생성
@Entity
@Builder
@Table(name = "member")
public class Member {

    @Id // @Id는 해당 프로퍼티가 테이블의 primary key 역할이라는 것을 지정
    @Column(name ="member_id")
    @GeneratedValue(strategy =  GenerationType.IDENTITY) // ID가 자동으로 생성 및 증가한다.
    private long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 100, nullable = false, updatable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER) // 기본값 LAZY 대경님 요청으로 패치타입 변환
    private List<String> roles;

//    마이페이지에서 본인이 작성한 questions 확인 기능이 있을때 mapping 해도 상관 없을듯?

//    mappedBy 자신의 연관계의 주인이 아닌 것을 표시 하는 설정
//    @OneToMany(cascade ={CascadeType.ALL},mappedBy = "question")
//    //memeber테이블 에서 Question 일대 다 관계
//    @JsonIgnore // @JsonIgnore를 붙이면 데이터를 주고 받을 때 해당 데이터는 'lgnore'되어서 응답값에 보이지 않게 된다.
//    private List<Question> memberQuestions = new ArrayList<>();
//
//
//    @OneToMany(cascade = {CascadeType.ALL},mappedBy = "answer")
//    //member 테이블 에서 answer 일대 다 관계
//    @JsonIgnore
//    private List<Answer> memberAnswers = new ArrayList<>();
//
//    @OneToMany(cascade = {CascadeType.ALL},mappedBy = "vote")
//    //member 테이블에서 vote로 일대 다 관계
//    @JsonIgnore
//    private List<vote> membervote = new ArrayList<>();
}
