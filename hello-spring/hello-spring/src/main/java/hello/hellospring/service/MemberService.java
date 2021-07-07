package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

// test 생성 단축키 : Ctrl + Shift + T
public class MemberService {

    private final MemberRepository memberRepository;

    // Ganaration 단축키 : Alt + Insert

    /*
        test 코드에서 테스트를 독립적으로 실행하기 위해서 Dependency Injection(DI)을 수행한 결과이다.
        즉, memberRepository를 내부에서 생성하는 것이 아닌,
        외부에서 입력 받도록 변경한 것이다.
        그 결과 테스트 코드에서는 각 테스트 실행 전 먼저 MemberRepository를 새로 생성해서 먼저 전달해주도록
        변경하였다.
     */
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public Long join(Member member) {
        //같은 이름이 있는 중복 회원 허용 X
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
