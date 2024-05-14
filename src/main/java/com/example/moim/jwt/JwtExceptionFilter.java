package com.example.moim.jwt;

import com.example.moim.exception.ErrorResult;
import com.example.moim.exception.InvalidTokenException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        }catch (ExpiredJwtException e){
            //토큰의 유효기간 만료
            setErrorResponse(response, new ErrorResult("login expired"));
        }catch (InvalidTokenException | IllegalArgumentException e){
            //유효하지 않은 토큰
            setErrorResponse(response, new ErrorResult(e.getMessage()));
        } catch (MalformedJwtException e){
            setErrorResponse(response, new ErrorResult(e.getMessage()));
        }
    }
    //new ErrorResult(e.getMessage()), HttpStatus.BAD_REQUEST
    private void setErrorResponse(
            HttpServletResponse response,
            ErrorResult errorResult
    ){
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(401);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try{
            response.getWriter().write(objectMapper.writeValueAsString(errorResult));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
