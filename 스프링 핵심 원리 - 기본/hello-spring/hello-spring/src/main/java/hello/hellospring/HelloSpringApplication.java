package hello.hellospring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 프로젝트 진행도 체크
 * 1. 회원 가입 : 진행 중
 * 2. 계좌 관리 : 준비
 * 3. 디비 관리 : 준비
 */
@SpringBootApplication
public class HelloSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloSpringApplication.class, args);
	}

}
