package com.pingyougou.carcontroller;
import java.util.List;

import com.pingyougou.userservice.AddressService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pingyougou.pojos.TbAddress;



/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/address")
public class AddressController {

	@Reference
	private AddressService addressService;

    @RequestMapping("/findAddressListByUserId")
	public List<TbAddress> findAddressListByUserId(){
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		TbAddress address = new TbAddress();
		address.setUserId(userName);
		List<TbAddress> select = addressService.select(address);
		return select;
	}
	
}
