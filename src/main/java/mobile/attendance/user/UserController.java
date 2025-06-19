package mobile.attendance.user;

import mobile.attendance.user.dto.UserRequest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRequest req) {
        try {
            User user = new User(
                    req.getUserId(),
                    req.getUserNumber(),
                    req.getUserPassword(),
                    req.getUserName(),
                    req.getUserRank()
            );

            User saved = userService.createUser(user);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(saved);

        } catch (DuplicateKeyException ex) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers(final UserSearchCondition condition) {
        List<User> users = userService.findUsers(condition);
        return users.size() > 0 ? ResponseEntity.ok(users) : ResponseEntity.noContent().build();
    }

    // id로 사용자 검색 API - GET
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") final String id) {
        return ResponseEntity.ok(userService.findUserById(id).get());
    }

    // 사용자 정보 수정 API - UPDATE
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") final String id,
                                             @RequestBody final UserRequest request
    ) {
        User updateUser = new User(
                id,
                request.getUserNumber(),
                request.getUserPassword(),
                request.getUserName(),
                request.getUserRank()
        );
        userService.updateUser(updateUser);
        return ResponseEntity.ok("성공적으로 사용자 정보가 수정되었습니다.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeUser(@PathVariable("id") final String id) {
        userService.removeUser(id);
        return ResponseEntity.ok("성공적으로 사용자 정보가 삭제되었습니다.");
    }
}
