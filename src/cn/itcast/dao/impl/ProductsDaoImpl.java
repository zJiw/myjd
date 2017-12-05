package cn.itcast.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.itcast.dao.ProductsDao;
import cn.itcast.pojo.Products;
import cn.itcast.pojo.SearchResult;

@Repository
public class ProductsDaoImpl implements ProductsDao {
	
	@Autowired
	private SolrServer solrServer;
	
	public SearchResult queryProductsWithCondition(SolrQuery solrQuery) {
		// 创建分页包装类对象
				SearchResult result = new SearchResult();
				
				//创建list集合,封装从索引库查询商品数据
				List<Products> pList = new ArrayList<Products>();

				try {
					// 根据封装参数对象查询索引库
					QueryResponse response = solrServer.query(solrQuery);
					// 获取文档集合
					SolrDocumentList results = response.getResults();
					// 获取命中总记录数
					Long numFound = results.getNumFound();
					// 把总记录数设置到分页包装类对象
					result.setTotalRecord(numFound.intValue());

					// 直接循环文档集合对象
					for (SolrDocument docs : results) {
						
						//创建商品对象products,封装每一个商品文档数据
						Products p = new Products();
						
						// 获取文档对象中值
						// docs.get(key)
						// id
						String id = (String) docs.get("id");
						p.setPid(id);
						// 标题
						String product_name = (String) docs.get("product_name");

						// 获取高亮
						Map<String, Map<String, List<String>>> highlighting = response
								.getHighlighting();
						// 第一个map的key就是id
						Map<String, List<String>> map = highlighting.get(id);
						// 第二个map的key是高亮字段名称
						List<String> list = map.get("product_name");
						// 判断是否有高亮
						if (list != null && list.size() > 0) {
							product_name = list.get(0);
						}

						p.setName(product_name);

						// 价格
						Float product_price = (Float) docs.get("product_price");
						
						p.setPrice(product_price);
						
						// 图片地址
						String product_picture = (String) docs.get("product_picture");
						p.setPicture(product_picture);
						
						//把商品对象放入集合
						pList.add(p);

					}
					
					//把查询所有商品结果放入分页包装类对象
					result.setpList(pList);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return result;
	}

}
