package com.pingyougou.userservice;

import com.pingyougou.pojos.TbUser;


import com.pingyougou.core.service.CoreService;
/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface UserService extends CoreService<TbUser> {
	
	


	/**
	 * 发送验证码
	 *
	 * @param phone
	 */
	void createSmsCode(String phone);


	/**
	 * 验证验证码是否正确
	 * @param phone
	 * @param smscode
	 * @return
	 */
    boolean checkSmsCode(String phone, String smscode);
}
