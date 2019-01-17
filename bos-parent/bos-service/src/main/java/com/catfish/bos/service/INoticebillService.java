package com.catfish.bos.service;

import java.util.List;

import com.catfish.bos.domain.Noticebill;
import com.catfish.bos.utils.PageBean;

public interface INoticebillService {

	public void save(Noticebill model);

	public void pageQuery(PageBean pageBean);

	public List<Noticebill> findNoticebillByOrdertype();

	public void diaodu(Noticebill model);

}
