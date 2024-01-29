package com.project.plateapi.user;

import com.project.plateapi.comment.Comment;
import com.project.plateapi.security.custom.dto.CustomUser;
import com.project.plateapi.user.dto.UserRequestDto;
import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/api/users")
    public ResponseEntity<?> createUser(@Valid @RequestBody Users user) {
        boolean result = service.createUser(user);
        if(result){
            log.info("회원가입 성공");
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } else{
            log.info("회원가입 실패");
            return new ResponseEntity<>("Failure", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/users/{id}")
    public Users retrieveUser(@PathVariable Long id) {
        return service.findById(id);
    }

    @Secured("USER")
    @GetMapping("/api/users/info")
    public ResponseEntity<?> retrieveUserInfo(@AuthenticationPrincipal CustomUser customUser) {
        return service.retrieveUserInfo(customUser);
    }

    @Secured("ADMIN")
    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        boolean result = service.deleteUser(id);

        if(result){
            log.info("삭제 성공");
            return new ResponseEntity<>("Successfully deleted",HttpStatus.OK);
        }else {
            log.info("삭제 실패");
            return new ResponseEntity<>("Failure",HttpStatus.BAD_REQUEST);
        }
    }

    @Secured("USER")
    @PutMapping("/api/users/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody UserRequestDto dto) {
        service.updateUser(id, dto);
    }

    @GetMapping("/api/users/{id}/comments")
    public List<Comment> retrieveCommentsForUser(@PathVariable Long id) {
        Users user = service.findById(id);

        return user.getComments();

    }

}
