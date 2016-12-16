package com.xxl.temp.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xxl.baseService.bo.Favorite;
import com.xxl.baseService.dao.IFrameworkDao;
import com.xxl.facade.JMSTaskRemote;
import com.xxl.facade.TempRemote;
import com.xxl.temp.bo.TempOrder;

import common.businessObject.ItModule;
import common.businessObject.MessageEvent;
import common.businessObject.MessageSubscibe;
import common.exception.BaseBusinessException;
import common.exception.BaseException;
import common.service.BaseService;
import common.temp.vo.TempOrderVo;
import common.utils.SemAppUtils;
import common.value.ObserverVO;
import common.value.PageList;

@Service("tempRemote")
public class TempService extends BaseService implements TempRemote{

	public Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	IFrameworkDao frameworkDAO;
	
	@Autowired
	private JMSTaskRemote jmsTaskRemote;
	
	public TempOrderVo getTempOrder(Integer id){
		TempOrder rootModule = (TempOrder)frameworkDAO.loadBoById(
				TempOrder.class, id);
		return (TempOrderVo) rootModule.toVO();
	}
	
	public Integer addTempOrder(TempOrderVo vo) throws BaseException, BaseBusinessException {
		Integer result = null;
		logger.debug(" addTempOrder" + vo);
		
		try {	
			if(vo.getAmount()!=null && vo.getSize()!=null && vo.getName()!=null){
				DetachedCriteria criteria = DetachedCriteria.forClass(TempOrder.class);
				criteria.add(Expression.eq("amount", vo.getAmount()));
				criteria.add(Expression.eq("name", vo.getName()));
				criteria.add(Expression.eq("size", vo.getSize()));
				List list=frameworkDAO.findByCriteria(criteria, 0, 0);
				if(list.size()>0){
					throw new BaseBusinessException("");
				}
				
			}
			TempOrder bo = new TempOrder();
			SemAppUtils.beanCopy(vo, bo);
			frameworkDAO.saveOrUpdate(bo);
			result = (Integer) bo.getId();
			vo.setId(result);
			String text=vo.getName()+"于"+SemAppUtils.getFullTime(vo.getCreatedate())+"下了一个订单"+
					 			"【尺码："+vo.getSize()+", 颜色："+vo.getColor()+", 共"+vo.getNum()+"双。地址："+vo.getAddress()+"，联系电话:"+vo.getTel()+"】";
					 			try {
					 				jmsTaskRemote.sendMessageByMail("275609395@qq.com", null, "订单提醒", text, null, null);
					 			} catch (Exception e) {
					 				logger.error("邮件提醒失败："+e);
					 			}
		} catch (HibernateException ee) {
			logger.error(ee);
			throw new BaseException("新增订单失败" + ee.getMessage());

		}
		return result;
	}
	
	public PageList getTempOrderList(
			TempOrderVo searchVO, Integer firstResult, Integer fetchSize)
			throws BaseException {
		try {

			DetachedCriteria criteria = DetachedCriteria.forClass(TempOrder.class);
			if(searchVO.getId()!=null){
				criteria.add(Expression.eq("id", searchVO.getId()));
			}
			criteria.addOrder(Order.desc("id"));
			PageList pageList=frameworkDAO.findByCriteriaByPage(criteria, firstResult, fetchSize);

			return pageList;
		} catch (HibernateException ee) {
			throw new BaseException("获取订单列表失败", ee);
		}
	}
	
	
}
