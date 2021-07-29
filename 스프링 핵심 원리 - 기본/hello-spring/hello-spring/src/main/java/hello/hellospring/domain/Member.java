package hello.hellospring.domain;

import javax.persistence.*;

/**
 * ORM : Object Relational Mapping
 * 객체와 관계형 데이터베이스 테이블을 매핑해주는 개념
 * 해당 매핑을 annotation 을 활용해서 수행한다.
 */

@Entity
public class Member {

    // IDENTITY : DB가 알아서 생성해주는 것
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
