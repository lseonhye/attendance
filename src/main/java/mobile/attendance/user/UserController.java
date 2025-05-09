package mobile.attendance.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody final UserRequest request) {
        User user = new User(
                request.getUserId(), request.getUserNumber(), request.getUserPassword(), request.getUserName(), request.getUserRank());

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers(final UserSearchCondition condition) {

        List<User> users = userService.findCustomers(condition);

        return users.size() > 0
                ? ResponseEntity.status(HttpStatus.OK).body(users)
                : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // id로 고객 검색 API - GET
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") final String id) {

        Optional<User> user = userService.findUserById(id);
        return user.isPresent() ? ResponseEntity.ok(user.get()) : ResponseEntity.notFound().build();
    }

    // 고객 정보 수정 API - UPDATE
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") final String id,
                                                 @RequestBody final UserRequest request
    ) {
        User updateUser = new User(id, request.getUserNumber(), request.getUserPassword(), request.getUserName(), request.getUserRank());
        userService.updateUser(updateUser);
        return ResponseEntity.ok("성공적으로 고객 정보가 수정되었습니다.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeUser(@PathVariable("id") final String id) {
        userService.removeUser(id);
        return ResponseEntity.ok("성공적으로 고객 정보가 삭제되었습니다.");
    }

}
