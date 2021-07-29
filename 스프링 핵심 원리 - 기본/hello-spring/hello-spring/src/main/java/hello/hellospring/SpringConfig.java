package hello.hellospring;

import hello.hellospring.aop.TimeTraceAop;
import hello.hellospring.domain.Member;
import hello.hellospring.repository.JdbcTemplateMemberRepository;
import hello.hellospring.repository.JpaMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.sql.DataSource;

/**
 * Component 스캔 방식이 아닌, 자바 코드를 이용한 스프링 빈 등록 방식입니다.
 * 이런 식으로 Configuration annotation 이 있다면 스프링이 시작될 때 해당 파일로 접근해서
 * Bean annotation 들을 스프링 빈에 등록을 한다.
 * 이때 Bean annotation 안에서 호출되는 객체들은 전부 스프링 빈에 등록되어 있는
 * 객체들을 불러온다.
 * 대신에 Controller annotation 은 컴포넌트 스캔 방식을 사용해야 한다.
 * <p>
 * Component 스캔 방식 vs 코드 선언 방식
 * 1. 정형화된 경우 : Component 스캔 방식
 * 2. 정형화되어 있지 않은 경우 : 코드 선언 방식
 * 3. 정리 : 즉, 추후 변형 될 수도 있는 경우에는 코드 선언 방식을 사용 -> 설정 파일 하나만 수정하면 되기 때문이다.
 * <p>
 * 해당 예제에서는 설계 단계에서는 아직 데이터 저장 개체를 설정하지 못했다는 가정으로 진행되기 때문에
 * 초기에는 단순 MemoryMember Repository 를 따로 만들어서 사용하다가,
 * 추후 DB에 연결해주는 방식으로 변경하는 식으로 진행되기 때문에
 * 변경을 위해서 코드 선언 방식으로 진행.
 */

@Configuration
public class SpringConfig {

    /* JdbcTemplate 사용 경우
    순수 JDBC 와 비교해서 반복되는 코드들이 많이 줄어들었다.
    sql을 직접 작성해야 한다.
    private final DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
     */

    /* Jpa 사용 경우
    기본적인 CRUD를 구현해야 하지만 sql문을 직접 작성할 필요가 없다.
    private EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em){
        this.em = em;
    }
     */

    // SpringDataJpa 사용 경우
    // 구현문 자체를 작성할 필요가 없었고, interface 만으로 끝이났다.
    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }

    /*
    @Bean
    public MemberRepository memberRepository() {
        return new JpaMemberRepository(em);
    }
     */
}