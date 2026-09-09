package com.nutribite.ai.security;

import com.nutribite.ai.model.User; import com.nutribite.ai.repository.UserRepository;
import jakarta.servlet.*; import jakarta.servlet.http.*; import org.springframework.lang.NonNull; import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; import org.springframework.security.core.authority.SimpleGrantedAuthority; import org.springframework.security.core.context.SecurityContextHolder; import org.springframework.stereotype.Component; import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException; import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
 private final JwtService jwtService; private final UserRepository userRepository;
 public JwtAuthenticationFilter(JwtService j,UserRepository u){jwtService=j;userRepository=u;}
 @Override protected void doFilterInternal(@NonNull HttpServletRequest req,@NonNull HttpServletResponse res,@NonNull FilterChain chain)throws ServletException,IOException{
  String header=req.getHeader("Authorization");
  if(header==null||!header.startsWith("Bearer ")){chain.doFilter(req,res);return;}
  String token=header.substring(7).trim();
  try{String email=jwtService.extractEmail(token); if(email!=null&&SecurityContextHolder.getContext().getAuthentication()==null&&jwtService.isTokenValid(token,email)){
    User user=userRepository.findByEmail(email).orElse(null); if(user!=null){var auth=new UsernamePasswordAuthenticationToken(email,null,List.of(new SimpleGrantedAuthority("ROLE_"+user.getRole().name())));SecurityContextHolder.getContext().setAuthentication(auth);}}
  }catch(Exception ignored){}
  chain.doFilter(req,res);
 }
}
