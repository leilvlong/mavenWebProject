package com.pingyougou.controller;

import com.github.pagehelper.PageInfo;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pingyougou.pojos.TbBrand;
import com.pingyougou.service.BrandService;
import entity.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/brand")
public class BrandController {

    @Reference
    private BrandService brandService;

    @RequestMapping("/all")
    public List<TbBrand> all(){
        return brandService.selectAll();
    }

    @RequestMapping("/page")
    public PageInfo<TbBrand> findPage(
            @RequestParam(value = "pageNum", defaultValue = "1", required = true) Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10", required = true) Integer pageSize){

        PageInfo<TbBrand> pageInfo= brandService.findPage(pageNum,pageSize);
        return pageInfo;
    }

    @RequestMapping("/search")
    public PageInfo search(
            @RequestParam(value = "pageNum", defaultValue = "1", required = true) Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10", required = true) Integer pageSize,
            @RequestBody TbBrand tb){
        return brandService.findPage(pageNum,pageSize,tb);
    }

    @RequestMapping("/add")
    public Result add(@RequestBody TbBrand tb){
        Result result = new Result();
        try {
            brandService.add(tb);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }


    @RequestMapping("/update")
    public Result update(@RequestBody TbBrand tb){
        Result result = new Result();
        try {
            brandService.update(tb);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @RequestMapping("/findone/{id}")
    public TbBrand findOne(@PathVariable(value = "id")Long id){
        return brandService.findOne(id);
    }

    @RequestMapping("/delete")
    public Result delete(@RequestBody Long[] ids){
        Result result = new Result();
        try {
            brandService.delete(ids);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

}
