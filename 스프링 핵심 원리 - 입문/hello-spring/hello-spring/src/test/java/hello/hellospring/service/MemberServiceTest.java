package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    // 아래의 경우 실제 memberService의 인스턴스와 memberRepository는 사실상 서로 다른 인스턴스이다. -> 새로 선언했기 때문에
    // 물론 지금의 경우에는 memoryMembetRepository가 static으로 선언되어 있어서 실행에 문제는 없지만 이 경우가 아니면 충돌이 발생한다.
    MemberService memberService;
    MemoryMemberRepository memberRepository = new MemoryMemberRepository();

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    /**
     * 기본적으로 Test를 작성할 때 아래처럼 given, when, then 패턴을 기본으로 접근하면 편리하다.
     */
    @Test
    void join() {
        //given
        Member member = new Member();
        member.setName("spring");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void Duplicate_join_exception() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        // Ctrl + Alt + V : 변수 선언
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하고 있는 회원 이름입니다.");

            /*

                try {
                    memberService.join(member2);
                    fail();
                }
                catch (IllegalStateException e) {
                    assertThat(e.getMessage()).isEqualTo("이미 존재하고 있는 회원 이름입니다.");
                }
             */

        //then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}