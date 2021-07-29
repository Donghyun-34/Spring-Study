package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

// test 생성 단축키 : Ctrl + Shift + T
//@Service // Spring 이 해당 어노테이션이 있는 코드를 service에 해당하는 코드라는 것을 인식할 수 있도록 해준다.
// -> 이러한 방식을 "component 스캔과 자동 의존관계 설정"이라 한다.
// 스프링은 스프링 컨테이너에 스프링 빈을 등록할 때 기본으로 싱글톤(유일)으로 등록하고 공유한다.
// JPA 는 반드시 서비스 계층에 트랜잭션이 존재해야 한다. -> 즉, 모든 변경은 트랜잭션 안에서만 발생해야 한다.

@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    // Generation 단축키 : Alt + Insert

    /*
        test 코드에서 테스트를 독립적으로 실행하기 위해서 Dependency Injection(DI)을 수행한 결과이다.
        즉, memberRepository를 내부에서 생성하는 것이 아닌,
        외부에서 입력 받도록 변경한 것이다.
        그 결과 테스트 코드에서는 각 테스트 실행 전 먼저 MemberRepository를 새로 생성해서 먼저 전달해주도록
        변경하였다.
     */
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public Long join(Member member) {
        //같은 이름이 있는 중복 회원 허용 X
        /* 수동 시간 측정 방식
        long start = System.currentTimeMillis();

        try{

        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("join = " + timeMs + "ms");
        }
         */
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    // refactor this 단축키 : Ctrl + Alt + Shift + T
    private void validateDuplicateMember(Member member) {
        Optional<Member> result = memberRepository.findByName((member.getName()));
        result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하고 있는 회원 이름입니다.");
        });
        /* 위와 동일한 명령. but 단축버전
            memberRepository.findByName((member.getName()))
                    .ifPresent(m -> {
                        throw new IllegalStateException("이미 존재하고 있는 회원 이름입니다.");
                    });
         */
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}

/*
 * 회원가입, 회원 조회 와 같은 기능들은 "핵심 관심 사항(core concern)"이다.
 * 그리고 시간 측정은 "공통 관심 사항(cross-cutting concern)"이다.
 */