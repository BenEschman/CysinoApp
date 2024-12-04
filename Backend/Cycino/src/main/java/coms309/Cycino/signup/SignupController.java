package coms309.Cycino.signup;

import coms309.Cycino.login.LoginInfo;
import coms309.Cycino.users.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Api(value = "SignUp Controller", description = "Controller used for signup")
public class SignupController {


    @Autowired

    private SignupService signupService;

    @ApiOperation(value = "Register user", response = Iterable.class, tags = "signUp")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 404, message = "not found!!!") })

    @PostMapping("/signup/register")
    public Map<String, Object> signup(@RequestBody LoginInfo user){
        return signupService.signup(new LoginInfo(user.getUsername(), user.getPassword()));
    }
}
