package com.inn.cafe.jwt.service;
import com.inn.cafe.POJO.User;
import com.inn.cafe.POJO.auth.Token;
import com.inn.cafe.constant.BaseConstant;
import com.inn.cafe.reponse.LoginResponse;
import com.inn.cafe.repository.TokenRepository;
import com.inn.cafe.repository.UserRepository;
import com.inn.cafe.request.LoginRequest;
import com.inn.cafe.request.SignupRequest;
import com.inn.cafe.utils.BaseResponse;
import com.inn.cafe.utils.CafeUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;


@Slf4j
@AllArgsConstructor

@Service
public class AuthService implements BaseConstant {
    @Autowired
    private  final UserRepository repo;
    @Autowired
    private  final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private  final  JwtService jwtService ;
    @Autowired
    private  final AuthenticationManager authenticationManager;
    @Autowired
    private  final TokenRepository tokenRepository;

    private  CafeUtils cafeUtils;

//    public AuthService(UserRepository repo, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, TokenRepository tokenRepository) {
//        this.repo = repo;
//        this.passwordEncoder = passwordEncoder;
//        this.jwtService = jwtService;
//        this.authenticationManager = authenticationManager;
//        this.tokenRepository = tokenRepository;
//    }



    ///////////// REGISTER ////////////////
    public ResponseEntity<BaseResponse>register(SignupRequest request){

        log.info("Inside signup{}",request);


       try{

           if(!repo.findByUsername(request.getUsername()).isPresent()){

               if(validateSignUp(request)){

                   User user= repo.save(getUserFromRequest(request));

                   String jwt=jwtService.generateToken(user.getUsername(),user.getRole().toString());
                   saveToken(jwt,user);
                   return new ResponseEntity<>(cafeUtils.generateSuccessResponse(null,REGISTER_MESSAGE,REGISTER_MESSAGE_BN), HttpStatus.OK) ;


               }else {

                   return new ResponseEntity<>(cafeUtils.generateSuccessResponse(null,INVALID_DATA,INVALID_DATA_BN), HttpStatus.BAD_REQUEST) ;


               }


           }
           else {
               return new ResponseEntity<>(cafeUtils.generateSuccessResponse(null,USER_EXIST,USER_EXIST_BN), HttpStatus.BAD_REQUEST) ;
           }
       }catch (Exception e){
           return new ResponseEntity<>(cafeUtils.generateErrorResponse(e),HttpStatus.INTERNAL_SERVER_ERROR);
       }

    }



    //////////// LOGIN //////
    public  ResponseEntity<BaseResponse> login(LoginRequest request){


        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            User user=repo.findByUsername(request.getUsername()).orElseThrow();
            String token=jwtService.generateToken(user.getUsername(),user.getRole().toString());
            System.out.println("User ID : "+user.getId());
            List<Token> tokens=  tokenRepository.findAllTokenByUserId(user.getId());
            BaseResponse res=new BaseResponse();
            res.setStatus(true);
            res.setData(new LoginResponse(token,jwtService.extractUsername(token),jwtService.extractRole(token)));
            return  new ResponseEntity<>(cafeUtils.generateSuccessResponse(res,"Successfully login",""),HttpStatus.OK);

//            return new   AuthenticationResponse(token);
        }catch (Exception e){
            BaseResponse res  = new BaseResponse();
            res.setStatus(false);
            res.setMessage("Wrong username or password");
            return ResponseEntity.ok(cafeUtils.generateErrorResponse(e));
        }
    }



    ////////////// TOKEN SAVE ////////////
    private  void  saveToken(String jwt,User user){
        Token token=new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);

    }

        private  boolean validateSignUp(SignupRequest user){

        if(!(user.getName() ==null)    && !(user.getContactNumber() ==null) && !(user.getUsername() ==null) && !(user.getRole().toString() ==null)
        && !(user.getPassword()==null)){

            System.out.println("User Valid");
            return  true;
        }
            System.out.println("User InValid");

        return  false;
    }


    public User getUserFromRequest(SignupRequest request){
        User user=new User();
        user.setName(request.getName());
        user.setContactNumber(request.getContactNumber());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(request.getStatus());
        user.setRole(request.getRole());
        jwtService.generateToken(user.getUsername(),user.getRole().toString());
        return  user;
    }


















}
