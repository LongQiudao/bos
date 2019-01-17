package com.catfish.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.support.incrementer.HsqlSequenceMaxValueIncrementer;
import org.springframework.stereotype.Controller;

import com.catfish.bos.domain.Region;
import com.catfish.bos.service.IRegionService;
import com.catfish.bos.utils.PageBean;
import com.catfish.bos.utils.PinYin4jUtils;
import com.catfish.bos.web.action.base.BaseAction;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
/**
 * 区域管理
 * */
@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<Region>{
	@Autowired
	private IRegionService regionService;
	//属性驱动接收上传文件
	private File regionFile;
	
	public void setRegionFile(File regionFile) {
		this.regionFile = regionFile;
	}
	/**
	 * 区域导入
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * */
	@RequiresPermissions("region-importXls")
	public String importXls() throws FileNotFoundException, IOException {
		List<Region> regionList = new ArrayList<Region>();
		//使用poi解析xls文件
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(regionFile));
		HSSFSheet hssfSheet =workbook.getSheetAt(0);
		for (Row row : hssfSheet) {
			int number = row.getRowNum();
			if(number==0) {
				continue;
			}
			String id = row.getCell(0).getStringCellValue();
			String province = row.getCell(1).getStringCellValue();
			String city = row.getCell(2).getStringCellValue();
			String district = row.getCell(3).getStringCellValue();
			String postcode = row.getCell(4).getStringCellValue();
			Region region = new Region(id, province, city, district, postcode, null, null, null);
			province = province.substring(0, province.length()-1);
			city = city.substring(0, city.length()-1);
			district = district.substring(0, district.length()-1);
			String info = province+city +district;
			String [] ss = PinYin4jUtils.getHeadByString(info);
			String shortCode = StringUtils.join(ss);
			String cityCode = PinYin4jUtils.hanziToPinyin(city, "");
			region.setCitycode(cityCode);
			region.setShortcode(shortCode);
			regionList.add(region);
		}
		regionService.saveBatch(regionList);
		return NONE;
	}
	
	
	/**
	 * 分页查询方法
	 * */
	public String pageQuery() throws IOException {
		regionService.pageQuery(pageBean);
		this.javatojson(pageBean, new String[]{"currentPage","detachedCriteria","pageSize","subareas"});
		return NONE;
	}
	/**
	 * 添加区域
	 * */
	@RequiresPermissions("region-add")
	public String add() {
		regionService.add(model);
		return LIST;
	}
	private String ids;
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	/**
	 * 批量删除区域
	 * */
	
	public String deleteBatch() {
		regionService.deleteBatch(ids);
		return LIST;
	}
	/**
	 * 修改区域信息
	 * */
	@RequiresPermissions("region-edit")
	public String edit() {
		
		regionService.update(model);
		return LIST;
	}
	private String q;
	public String getQ() {
		return q;
	}
	public void setQ(String q) {
		this.q = q;
	}
	/**
	 * 查询所有区域返回json数据
	 * */
	public String listajax() {
		List<Region> list = null ;
		if(StringUtils.isNotBlank(q)) {
			list = regionService.findListByq(q);
		}else {
			list =regionService.findAll();
		}
		this.javatojson(list, new String[]{"province","city","district","postcode","shortcode","citycode","subareas"} );
		return NONE;
	}
	
}
