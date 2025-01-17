package hello.hello_spring;

import hello.hello_spring.repository.JdbcMemberRepository;
import hello.hello_spring.repository.JdbcTemplateMemberRepository;
import hello.hello_spring.repository.JpaMemberRepository;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import hello.hello_spring.service.MemberService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    // DataSource에서 Autowired를 쓸 수 있는 이유: 스프링 부트가 application.properties에 있는 정보를 보고 DataSource를 빈으로 등록해놓기 때문
    // DataSource는 데이터베이스 커넥션을 획득할 때 사용하는 객체
    private DataSource dataSource;

    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    // @PersistenceContext
    private EntityManager em;

    // 생성자를 통해 DataSource를 주입받음
    // 생성자가 하나만 있으면 Autowired를 생략해도 됨
    // @Autowired
    /*public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }*/


    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    // 기존의 MemoryMemberRepository를 JdbcMemberRepository로 변경
    // MemberRepository 인터페이스를 그대로 두고 구현체만 바꿔 끼워넣을 수 있었음.
    // 이것이 다형성을 활용한 객체 지향 설계의 장점
    public MemberRepository memberRepository() {
        // return new JdbcMemberRepository(dat®aSource);
        // return new JdbcTemplateMemberRepository(dataSource);
        return new JpaMemberRepository(em);
    }
}
