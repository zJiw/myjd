package cn.itcast.controller;

import org.apache.solr.cloud.SyncStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.itcast.pojo.SearchResult;
import cn.itcast.service.ProductsService;

@Controller
public class ProductsController {
	
	@Autowired 
	private ProductsService productsService;
	
	/**
	 * 测试git使用
	 */
	@RequestMapping("git")
	public String showGit(){
		System.out.println("lqx是小狗");
		
		
		System.out.println("lqx是好人");
		System.out.println("在issu99分支上，开发新的业务代码");
		return "git";
	}
	
	/**
	 * 需求:跳转到商品搜索列表页面product_list
	 * 参数:String queryString,String catalog_name,String price,Integer page,Integer rows,String sort,Model model
	 */
	@RequestMapping("list")
	public String list(String queryString,
			String catalog_name,
			String price,
			@RequestParam(defaultValue="1") Integer page,
			@RequestParam(defaultValue="60") Integer rows,
			@RequestParam(defaultValue="1") String sort,
			Model model){
		//调用service服务方法
		SearchResult result = productsService.queryProductsWithCondition(queryString, catalog_name, price, page, rows, sort);
		//回显主查询条件
		model.addAttribute("queryString", queryString);
		//类别
		model.addAttribute("catalog_name", catalog_name);
		//价格
		model.addAttribute("price", price);
		//排序
		model.addAttribute("sort", sort);
		//分页数据
		model.addAttribute("result", result);
		return "product_list";
	}
}
