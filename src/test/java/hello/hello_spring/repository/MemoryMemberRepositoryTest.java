package hello.hello_spring.repository;

import static org.assertj.core.api.Assertions.assertThat;

import hello.hello_spring.domain.Member;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository memoryMemberRepository = new MemoryMemberRepository();

    // 독립적으로 실행하지 않으면 findAll() 실행 후 findByName()을 실행함.
    // 하지만 findAll()에서 spring1을 먼저 저장하였음. 그리고 findByName()에서 spring1을 또 저장
    // 즉, spring1으로 저장된 객체가 두 가지가 있으므로, 오류가 발생함.
    // 테스트는 의존적으로 해야하기 때문에 데이터를 지워줘야함.
    @AfterEach
    public void afterEach() {
        memoryMemberRepository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        memoryMemberRepository.save(member);

        Member result = memoryMemberRepository.findById(member.getId()).get();
        // Assertions.assertEquals(result, member);
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        memoryMemberRepository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        memoryMemberRepository.save(member2);

        Member result = memoryMemberRepository.findByName("spring1").get();
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        memoryMemberRepository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        memoryMemberRepository.save(member2);

        List<Member> result = memoryMemberRepository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
