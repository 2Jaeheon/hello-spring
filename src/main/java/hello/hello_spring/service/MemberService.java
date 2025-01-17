package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    // Dependency Injection (DI)
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    /**
     * 회원 가입
     *
     * @param member
     * @return
     */
    public Long join(Member member) {
        // 같은 이름이 있는 중복 회원 안 됨
        // Optional로 한 번 감싼 것. 그러면 다양한 처리가 가능해서 편해진다.
        // null의 가능성이 존재할 때 Optional로 감싼 것. get() 메서드를 통해서 꺼낼 수 있지만 권장하지 않음.
        // 보통 getOrElse() 를 통해서 조건 처리를 함.
        // 여기서는 반환형이 Optional이므로 바로 사용 가능함.
        // findByName()로직이 복잡하여 메서드로 뽑아낼것.
        // control + t -> 리펙토링 옵션 -> extract method를 통해서 메서드를 따로 뽑아냄.
        // 자동완성 = 커맨드 옵션 v
        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
            .ifPresent(m -> {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            });
    }

    /**
     * 전체 회원 조회
     *
     * @return
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 조회
     *
     * @param memberId
     * @return
     */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
