package coms309.Cycino.users;

import coms309.Cycino.stats.UserStatsController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import coms309.Cycino.follow.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "UserController", description = "User controller to CRUDL user information")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "get all users", response = Iterable.class, tags = "getUsers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @RequestMapping(method = RequestMethod.GET, path = "/users")
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @ApiOperation(value = "create user", response = Iterable.class, tags = "create")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @PutMapping("/users/update/{id}")
    public Map<String, Object> create(@RequestBody User user, @PathVariable long id){
        Map<String, Object> result = new HashMap<>();
        if (userService.create(user, id)){
            result.put("status", "200 OK");
        } else {
            result.put("status", "400 Bad Request"); //User probably does not exist
        }
        return result;
    }

    @ApiOperation(value = "get specific user", response = Iterable.class, tags = "getUser")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable long id){
        return userService.getUser(id);
    }


}
