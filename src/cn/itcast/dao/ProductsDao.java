package cn.itcast.dao;

import org.apache.solr.client.solrj.SolrQuery;

import cn.itcast.pojo.SearchResult;

public interface ProductsDao {
	
	/**
	 * 访问索引库
	 */
	public SearchResult queryProductsWithCondition(SolrQuery solrQuery);
}
