package com.example.demo.user.service;

import com.example.demo.mock.FakeMailSender;
import com.example.demo.user.service.port.CertificationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CertificationServiceTest {

    @Test
    void 이메일과_컨텐츠가_제대로_만들어져서_보내지는지_테스트한다() throws Exception {
        //given
        FakeMailSender fakeMailSender = new FakeMailSender();
        CertificationService certificationService = new CertificationService(fakeMailSender);

        //when
        certificationService.send("devcsb1@gmail.com", 1, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaab");

        //then
        Assertions.assertThat(fakeMailSender.email).isEqualTo("devcsb1@gmail.com");
        Assertions.assertThat(fakeMailSender.title).isEqualTo("Please certify your email address");
        Assertions.assertThat(fakeMailSender.content).isEqualTo(
                "Please click the following link to certify your email address: http://localhost:8080/api/users/1/verify?certificationCode=aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaab");
    }
}
