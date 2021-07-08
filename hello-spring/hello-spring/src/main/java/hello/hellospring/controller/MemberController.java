package hello.hellospring.controller;


import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController{

    private final MemberService memberService;

    /*
        Autowired : controller가 생성될 때 스프링 빈에 등록되어 있는 member 객체를 자동으로 가져와서 넣어준다.
        이것이 바로 의존성 주입(DI)이다.
     */
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);

        return "members/memberList"; }
}


/**
 * DI의 유형에는 3가지가 있다.
 * 1. 필드 주입 : 선언부에서 바로 Autowired annotation 를 선언하는 것.
 *  -> 이는 선언을 제외하면 중간에 접근할 방법이 없기 때문에 추천하지 않는다.
 *  ex) @Autowired private MemberService memberService
 * 2. setter 주입 : 생성자가 아닌 따로 의존성을 정의해주는 setter 를 만들어서 거기에 Autowired annotation 을 선언한다.
 *  -> 단점 : public 으로 열려있어야 하기 때문에, 노출의 위험이 있다.
 *  ex)private MemberService memberService;
 *
 *     @Autowired
 *     public void setMemberService(MemberService memberService) {
 *         this.memberService = memberService;
 *     }
 * 3. 생성자 주입 : 생성자에 바로 Autowired annotation 을 선언하는 것.
 *  -> 그렇기 때문에 의존성 주입이 실행 중에 동적으로 변경되는 경우는 없기 때문에,
 *  생성자로 처음 실행 될 때만 정의되도록 생성자 주입 방법을 권장한다.
 */