package com.pingyougou.service.impl;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired; 
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo; 									  
import org.apache.commons.lang3.StringUtils;
import com.pingyougou.core.service.CoreServiceImpl;

import tk.mybatis.mapper.entity.Example;

import com.pingyougou.mapper.TbSellerMapper;
import com.pingyougou.pojos.TbSeller;

import com.pingyougou.service.SellerService;



/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class SellerServiceImpl extends CoreServiceImpl<TbSeller>  implements SellerService {

	
	private TbSellerMapper sellerMapper;

	@Autowired
	public SellerServiceImpl(TbSellerMapper sellerMapper) {
		super(sellerMapper, TbSeller.class);
		this.sellerMapper=sellerMapper;
	}

	
	

	
	@Override
    public PageInfo<TbSeller> findPage(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<TbSeller> all = sellerMapper.selectAll();
        PageInfo<TbSeller> pageInfo = new PageInfo<TbSeller>(all);

        return pageInfo;
    }

	
	

	 @Override
    public PageInfo<TbSeller> findPage(Integer pageNo, Integer pageSize, TbSeller seller) {
        PageHelper.startPage(pageNo,pageSize);

        Example example = new Example(TbSeller.class);
        Example.Criteria criteria = example.createCriteria();

        if(seller!=null){			
						if(StringUtils.isNotBlank(seller.getSellerId())){
				criteria.andLike("sellerId","%"+seller.getSellerId()+"%");
				//criteria.andSellerIdLike("%"+seller.getSellerId()+"%");
			}
			if(StringUtils.isNotBlank(seller.getName())){
				criteria.andLike("name","%"+seller.getName()+"%");
				//criteria.andNameLike("%"+seller.getName()+"%");
			}
			if(StringUtils.isNotBlank(seller.getNickName())){
				criteria.andLike("nickName","%"+seller.getNickName()+"%");
				//criteria.andNickNameLike("%"+seller.getNickName()+"%");
			}
			if(StringUtils.isNotBlank(seller.getPassword())){
				criteria.andLike("password","%"+seller.getPassword()+"%");
				//criteria.andPasswordLike("%"+seller.getPassword()+"%");
			}
			if(StringUtils.isNotBlank(seller.getEmail())){
				criteria.andLike("email","%"+seller.getEmail()+"%");
				//criteria.andEmailLike("%"+seller.getEmail()+"%");
			}
			if(StringUtils.isNotBlank(seller.getMobile())){
				criteria.andLike("mobile","%"+seller.getMobile()+"%");
				//criteria.andMobileLike("%"+seller.getMobile()+"%");
			}
			if(StringUtils.isNotBlank(seller.getTelephone())){
				criteria.andLike("telephone","%"+seller.getTelephone()+"%");
				//criteria.andTelephoneLike("%"+seller.getTelephone()+"%");
			}
			if(StringUtils.isNotBlank(seller.getStatus())){
				criteria.andLike("status","%"+seller.getStatus()+"%");
				//criteria.andStatusLike("%"+seller.getStatus()+"%");
			}
			if(StringUtils.isNotBlank(seller.getAddressDetail())){
				criteria.andLike("addressDetail","%"+seller.getAddressDetail()+"%");
				//criteria.andAddressDetailLike("%"+seller.getAddressDetail()+"%");
			}
			if(StringUtils.isNotBlank(seller.getLinkmanName())){
				criteria.andLike("linkmanName","%"+seller.getLinkmanName()+"%");
				//criteria.andLinkmanNameLike("%"+seller.getLinkmanName()+"%");
			}
			if(StringUtils.isNotBlank(seller.getLinkmanQq())){
				criteria.andLike("linkmanQq","%"+seller.getLinkmanQq()+"%");
				//criteria.andLinkmanQqLike("%"+seller.getLinkmanQq()+"%");
			}
			if(StringUtils.isNotBlank(seller.getLinkmanMobile())){
				criteria.andLike("linkmanMobile","%"+seller.getLinkmanMobile()+"%");
				//criteria.andLinkmanMobileLike("%"+seller.getLinkmanMobile()+"%");
			}
			if(StringUtils.isNotBlank(seller.getLinkmanEmail())){
				criteria.andLike("linkmanEmail","%"+seller.getLinkmanEmail()+"%");
				//criteria.andLinkmanEmailLike("%"+seller.getLinkmanEmail()+"%");
			}
			if(StringUtils.isNotBlank(seller.getLicenseNumber())){
				criteria.andLike("licenseNumber","%"+seller.getLicenseNumber()+"%");
				//criteria.andLicenseNumberLike("%"+seller.getLicenseNumber()+"%");
			}
			if(StringUtils.isNotBlank(seller.getTaxNumber())){
				criteria.andLike("taxNumber","%"+seller.getTaxNumber()+"%");
				//criteria.andTaxNumberLike("%"+seller.getTaxNumber()+"%");
			}
			if(StringUtils.isNotBlank(seller.getOrgNumber())){
				criteria.andLike("orgNumber","%"+seller.getOrgNumber()+"%");
				//criteria.andOrgNumberLike("%"+seller.getOrgNumber()+"%");
			}
			if(StringUtils.isNotBlank(seller.getLogoPic())){
				criteria.andLike("logoPic","%"+seller.getLogoPic()+"%");
				//criteria.andLogoPicLike("%"+seller.getLogoPic()+"%");
			}
			if(StringUtils.isNotBlank(seller.getBrief())){
				criteria.andLike("brief","%"+seller.getBrief()+"%");
				//criteria.andBriefLike("%"+seller.getBrief()+"%");
			}
			if(StringUtils.isNotBlank(seller.getLegalPerson())){
				criteria.andLike("legalPerson","%"+seller.getLegalPerson()+"%");
				//criteria.andLegalPersonLike("%"+seller.getLegalPerson()+"%");
			}
			if(StringUtils.isNotBlank(seller.getLegalPersonCardId())){
				criteria.andLike("legalPersonCardId","%"+seller.getLegalPersonCardId()+"%");
				//criteria.andLegalPersonCardIdLike("%"+seller.getLegalPersonCardId()+"%");
			}
			if(StringUtils.isNotBlank(seller.getBankUser())){
				criteria.andLike("bankUser","%"+seller.getBankUser()+"%");
				//criteria.andBankUserLike("%"+seller.getBankUser()+"%");
			}
			if(StringUtils.isNotBlank(seller.getBankName())){
				criteria.andLike("bankName","%"+seller.getBankName()+"%");
				//criteria.andBankNameLike("%"+seller.getBankName()+"%");
			}
	
		}
        List<TbSeller> all = sellerMapper.selectByExample(example);
        PageInfo<TbSeller> pageInfo = new PageInfo<TbSeller>(all);


        return pageInfo;
    }

	@Override
	public void updateStatus(String id, String status) {
		TbSeller tb = new TbSeller();
		tb.setSellerId(id);
		tb.setStatus(status);
		sellerMapper.updateByPrimaryKey(tb);
	}

	@Override
    public void add(TbSeller ts){
		ts.setStatus("0");
		ts.setCreateTime(new Date());
		sellerMapper.insert(ts);

	}

}
