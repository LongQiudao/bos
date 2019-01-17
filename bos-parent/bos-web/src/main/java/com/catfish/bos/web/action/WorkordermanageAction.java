package com.catfish.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.catfish.bos.domain.Workordermanage;
import com.catfish.bos.service.IWorkordermanageService;
import com.catfish.bos.utils.FileUtils;
import com.catfish.bos.web.action.base.BaseAction;
@Controller
@Scope("prototype")
public class WorkordermanageAction extends BaseAction<Workordermanage>{
	@Autowired
	private IWorkordermanageService workordermanageService;
	/**
	 * 工作单开始录入
	 * */
	public String add() throws IOException {
		String f ="1";
		try {
			workordermanageService.save(model);
		} catch (Exception e) {
			e.printStackTrace();
			f="0";
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(f);
		return NONE;
	}
	/**
	 * 工作单分页查询
	 * */
	public String pageQuery() {
		workordermanageService.pageQuery(pageBean);
		this.javatojson(pageBean, new String[]{"currentPage","detachedCriteria","pageSize"});
		return NONE;
	}
	private File workordermanageFile;
	/**
	 * 批量导入工作单
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * */
	public String importXls() throws FileNotFoundException, IOException {
		List<Workordermanage> list = new ArrayList<Workordermanage>();
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(workordermanageFile));
		HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
		for (Row row : hssfSheet) {
			int number = row.getRowNum();
			if(number==0) {
				continue;
			}
			row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
			row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
			row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
			Workordermanage workordermanage = new Workordermanage();
			workordermanage.setId(row.getCell(0).getStringCellValue());
			workordermanage.setProduct(row.getCell(1).getStringCellValue());
			workordermanage.setProdtimelimit(row.getCell(2).getStringCellValue());
			workordermanage.setProdtype(row.getCell(3).getStringCellValue());
			workordermanage.setSendername(row.getCell(4).getStringCellValue());
			workordermanage.setSenderphone(row.getCell(5).getStringCellValue());
			workordermanage.setSenderaddr(row.getCell(6).getStringCellValue());
			workordermanage.setReceiveraddr(row.getCell(7).getStringCellValue());
			workordermanage.setReceiverphone(row.getCell(8).getStringCellValue());
			workordermanage.setReceivername(row.getCell(9).getStringCellValue());
			list.add(workordermanage);
		}
		workordermanageService.saceBatch(list);
		return NONE;
	}
	/**
	 * 批量导出工作单(模板下载)
	 * @throws IOException 
	 * */
	public String exportXls() throws IOException {
		List<Workordermanage> list = workordermanageService.findAll();
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("工作单");
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("编号");
		headRow.createCell(1).setCellValue("产品");
		headRow.createCell(2).setCellValue("产品时限");
		headRow.createCell(3).setCellValue("产品类型");
		headRow.createCell(4).setCellValue("发件人姓名");
		headRow.createCell(5).setCellValue("发件人电话");
		headRow.createCell(6).setCellValue("发件人地址");
		headRow.createCell(7).setCellValue("收件人地址");
		headRow.createCell(8).setCellValue("收件人电话");
		headRow.createCell(9).setCellValue("收件人姓名");
		for (Workordermanage workordermanage : list) {
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum()+1);
			dataRow.createCell(0).setCellValue(workordermanage.getId());
			dataRow.createCell(1).setCellValue(workordermanage.getProduct());
			dataRow.createCell(2).setCellValue(workordermanage.getProdtimelimit());
			dataRow.createCell(3).setCellValue(workordermanage.getProdtype());
			dataRow.createCell(4).setCellValue(workordermanage.getSendername());
			dataRow.createCell(5).setCellValue(workordermanage.getSenderphone());
			dataRow.createCell(6).setCellValue(workordermanage.getSenderaddr());
			dataRow.createCell(7).setCellValue(workordermanage.getReceiveraddr());
			dataRow.createCell(8).setCellValue(workordermanage.getReceiverphone());
			dataRow.createCell(4).setCellValue(workordermanage.getReceivername());
		}
		String filename = "工作单.xls";
		String contentType = ServletActionContext.getServletContext().getMimeType(filename);
		ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
		ServletActionContext.getResponse().setContentType(contentType);
		
		//获取客户端浏览器类型
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");
		filename = FileUtils.encodeDownloadFilename(filename, agent);
		ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename="+filename);
		workbook.write(out);
		return NONE;
	}
	public File getWorkordermanageFile() {
		return workordermanageFile;
	}
	public void setWorkordermanageFile(File workordermanageFile) {
		this.workordermanageFile = workordermanageFile;
	}
}
