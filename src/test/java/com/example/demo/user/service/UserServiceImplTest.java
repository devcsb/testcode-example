package com.example.demo.user.service;

class UserServiceImplTest {
    private UserServiceImpl userServiceImpl;

//    @BeforeEach
//    void init() {
//        FakeMailSender fakeMailSender = new FakeMailSender();
//        FakeUserRepository fakeUserRepository = new FakeUserRepository();
//        this.userServiceImpl = UserServiceImpl.builder()
//                .uuidHolder(new TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa"))
//                .clockHolder(new TestClockHolder(1693843933L))
//                .userRepository(fakeUserRepository)
//                .certificationServiceImpl(new CertificationService(fakeMailSender))
//                .build();
//        fakeUserRepository.save(User.builder()
//                .id(1L)
//                .email("devcsb119@gmail.com")
//                .nickname("tester")
//                .address("seoul")
//                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa")
//                .status(UserStatus.ACTIVE)
//                .lastLoginAt(0L)
//                .build());
//        fakeUserRepository.save(User.builder()
//                .id(2L)
//                .email("'devcsb1@gmail.com'")
//                .nickname("tester1")
//                .address("seoul")
//                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaab")
//                .status(UserStatus.PENDING)
//                .lastLoginAt(0L)
//                .build());
//    }
//
//    @Test
//    void getByEmail은_ACTIVE_상태인_유저를_찾아올_수_있다() throws Exception {
//        //given
//        String email = "devcsb119@gmail.com";
//
//        //when
//        User result = userServiceImpl.getByEmail(email);
//
//        //then
//        assertThat(result.getNickname()).isEqualTo("tester");
//    }
//
//    @Test
//    void getByEmail은_PENDING_상태인_유저는_찾아올_수_없다() throws Exception {
//        //given
//        String email = "devcsb1@gmail.com";
//        //when
//        //then
//        assertThatThrownBy(() -> userServiceImpl.getByEmail(email)).isInstanceOf(ResourceNotFoundException.class);
//    }
//
//    @Test
//    void getById는_ACTIVE_상태인_유저를_찾아올_수_있다() throws Exception {
//        //given
//        //when
//        User result = userServiceImpl.getById(1);
//
//        //then
//        assertThat(result.getNickname()).isEqualTo("tester");
//    }
//
//    @Test
//    void getById는_PENDING_상태인_유저는_찾아올_수_없다() throws Exception {
//        //given
//        //when
//        //then
//        assertThatThrownBy(() -> userServiceImpl.getById(2)).isInstanceOf(ResourceNotFoundException.class);
//    }
//
//    @Test
//    void userCreate_를_이용하여_유저를_생성할_수_있다() throws Exception {
//        //given
//        UserCreate userCreate = UserCreate.builder()
//                .email("devcsb2@gmail")
//                .address("seoul")
//                .nickname("tester3")
//                .build();
//
//        //when
//        User result = userServiceImpl.create(userCreate);
//
//        //then
//        assertThat(result.getId()).isNotNull();
//        assertThat(result.getStatus()).isEqualTo(UserStatus.PENDING);
//        assertThat(result.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa");
//    }
//
//    @Test
//    void userUpdate_를_이용하여_유저를_수정할_수_있다() throws Exception {
//        //given
//        UserUpdate userUpdate = UserUpdate.builder()
//                .address("busan")
//                .nickname("updater")
//                .build();
//
//        //when
//        userServiceImpl.update(1, userUpdate);
//
//        //then
//        User user = userServiceImpl.getById(1);
//        assertThat(user.getId()).isEqualTo(1);
//        assertThat(user.getAddress()).isEqualTo("busan");
//        assertThat(user.getNickname()).isEqualTo("updater");
//    }
//
//    @Test
//    void user를_로그인_시키면_마지막_로그인_시간이_변경된다() throws Exception {
//        //given
//        //when
//        userServiceImpl.login(1);
//
//        //then
//        User user = userServiceImpl.getById(1);
//        assertThat(user.getLastLoginAt()).isEqualTo(1693843933L);
//    }
//
//    @Test
//    void PENDING_상태의_사용자는_인증코드로_활성화시킬_수_있다() throws Exception {
//        //given
//        //when
//        userServiceImpl.verifyEmail(2, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaab");
//
//        //then
//        User user = userServiceImpl.getById(1);
//        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
//    }
//
//    @Test
//    void ACTIVE_상태의_사용자도_인증코드만_일치하다면_정상수행된다() throws Exception {
//        //given
//        //when
//        userServiceImpl.verifyEmail(1, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa");
//
//        //then
//        User user = userServiceImpl.getById(1);
//        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
//    }
//
//    @Test
//    void 인증코드가_불일치할시_유저의_인증상태_변경에_실패한다() throws Exception {
//        //given
//        //when
//        //then
//        assertThatThrownBy(() -> userServiceImpl.verifyEmail(1, "failaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa"))
//                .isInstanceOf(CertificationCodeNotMatchedException.class);
//    }
}