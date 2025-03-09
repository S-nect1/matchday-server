package com.example.moim.config.security;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//@RequiredArgsConstructor
//@Slf4j
@Deprecated
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
//
//    private final AuthenticationManager authenticationManager;
//    private final JWTUtil jwtUtil;
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        setUsernameParameter("email");
//        LoginInput loginDTO;
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            loginDTO = objectMapper.readValue(StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8), LoginInput.class);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        //클라이언트 요청에서 email, password 추출
//        String email = loginDTO.getEmail();
//        String password = loginDTO.getPassword();
//        //스프링 시큐리티에서 email password를 검증하기 위해서는 token에 담아야 함
//        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);
//
//        //token에 담은 검증을 위한 AuthenticationManager로 전달
//        return authenticationManager.authenticate(authToken);
//    }
//
//    //로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
//        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
//        response.addHeader("Authorization", "Bearer " + jwtUtil.createAccessToken(userDetailsImpl));
////
////        //응답 설정
////        response.setHeader("access", access);
////        response.addCookie(createCookie("refresh", refresh));
////        response.setStatus(HttpStatus.OK.value());
//    }
//
//    //로그인 실패시 실행하는 메소드
//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
//        log.info("failed = {}", failed);
//        response.setStatus(401);
//    }
//
}