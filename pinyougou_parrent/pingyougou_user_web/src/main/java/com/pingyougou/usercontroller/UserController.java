package com.pingyougou.usercontroller;
import java.util.List;

import com.pingyougou.pojos.TbUser;
import com.pingyougou.userservice.UserService;

import entity.ErrorResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import com.alibaba.dubbo.config.annotation.Reference;



import entity.Error;
import entity.Result;

import javax.validation.Valid;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Reference
	private UserService userService;


	/**
	 * 增加
	 * @param user
	 * @param smscode  这个就是表示页面传递的验证码数据
	 * @return
	 */
	@RequestMapping("/add/{smscode}")
	public ErrorResult add(@Valid @RequestBody TbUser user, @PathVariable(name="smscode") String smscode, BindingResult bindingResult){
		try {
			//1.判断是否验证码正确 如果 不正确 返回

            if(bindingResult.hasErrors()){
                ErrorResult result = new ErrorResult(false,"失败");
                List<FieldError> fieldErrors = bindingResult.getFieldErrors();
                for (FieldError fieldError : fieldErrors) {
                    result.getErrorsList().add(new Error(fieldError.getField(),fieldError.getDefaultMessage()));
                }
                return result;
            }

			if(!userService.checkSmsCode(user.getPhone(),smscode)){
				return new ErrorResult(false,"验证码错误");
			}

			userService.add(user);
			return new ErrorResult(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorResult(false, "增加失败");
		}
	}

    @RequestMapping("/sendCode")
	public Result sendCode(String phone){
		try {
			userService.createSmsCode(phone);
			return new Result(true,"请查看手机");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,"失败");
		}
	}
	
}
