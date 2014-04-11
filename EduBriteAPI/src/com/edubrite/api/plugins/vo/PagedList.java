package com.edubrite.api.plugins.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class PagedList {
	
	@XmlAttribute(name = "numItems")
	protected int numItems;
	
	@XmlAttribute(name = "currPage")
	protected int currPage = -1;
	
	@XmlAttribute(name = "numPages")
	protected int numPages;
	
	@XmlAttribute(name = "pageSize")
	protected int pageSize = 20;
	
	public int getNumItems() {
		return numItems;
	}
	public void setNumItems(int numItems) {
		this.numItems = numItems;
	}
	public int getCurrPage() {
		return currPage;
	}
	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}
	public int getNumPages() {
		return numPages;
	}
	public void setNumPages(int numPages) {
		this.numPages = numPages;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalPages() {
		if(pageSize > 0){
			return ((int) Math.floor(numItems / pageSize)) + (numItems % pageSize > 0?1 : 0);
		}
		return 0;
	}
	public boolean hasMorePages(){
		return currPage > -1 && currPage < (getTotalPages()-1); 
	}
}
