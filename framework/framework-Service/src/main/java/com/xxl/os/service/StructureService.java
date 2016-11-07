package com.xxl.os.service;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Service;

import com.xxl.baseService.bo.SystemProperties;
import com.xxl.baseService.bo.UserProperties;
import com.xxl.facade.CommonRemote;
import com.xxl.facade.HelperRemote;
import com.xxl.facade.StructureRemote;
import com.xxl.os.bo.EiaOnline;
import com.xxl.os.bo.SyDepartment;
import com.xxl.os.bo.SyDuty;
import com.xxl.os.bo.SyOrganise;
import com.xxl.os.bo.SyUsers;
import com.xxl.os.dao.DepartmentDAO;
import com.xxl.os.dao.DutyDAO;
import com.xxl.os.dao.OnlineDAO;
import com.xxl.os.dao.OrganiseDAO;
import com.xxl.os.dao.UserDutyDAO;
import com.xxl.os.dao.UserPropertiesDAO;
import com.xxl.os.dao.UsersDAO;

import common.bussiness.Message;
import common.exception.BaseBusinessException;
import common.exception.BaseException;
import common.os.vo.DepartmentVO;
import common.os.vo.DutyVO;
import common.os.vo.OrganiseVO;
import common.os.vo.UsersVO;
import common.os.vo.exception.OSBussinessException;
import common.os.vo.exception.OSException;
import common.service.BaseService;
import common.utils.SemAppUtils;
import common.value.PageList;
import common.value.UserPropertiesVO;
import common.web.bean.SessionUserBean;
import common.web.utils.SemWebAppUtils;

@Service("structureRemote")
public class StructureService extends BaseService implements StructureRemote {
	public Log logger = LogFactory.getLog(this.getClass());
	public Log dbLogger = SemAppUtils.getDBLog(StructureService.class);
	@Autowired
	DepartmentDAO departmentDAO;

	@Autowired
	DutyDAO dutyDAO;
	
	@Autowired
	OnlineDAO onlineDAO;
	
	@Autowired
	OrganiseDAO organiseDAO;
	
	@Autowired
	UserDutyDAO userDutyDAO;
	
	@Autowired
	UsersDAO usersDAO;
	
	@Autowired
	HelperRemote helperRemote;
	
	@Autowired
	CommonRemote commonRemote;
	
	@Autowired
	UserPropertiesDAO userPropertiesDAO;

	public UsersVO getUserInfo(final Integer empId) throws OSException,
			OSBussinessException {
		// SyUsers users = (SyUsers) usersDAO.loadById(empId, SyUsers.class);
		// if(users==null) {
		// throw new OSBussinessException("用户不存在");
		// }
		// return (UsersVO) users.toVO();
		// return (UsersVO)usersDAO.loadVoById(empId, SyUsers.class);
		try {
			// usersDAO.deleteById(id, SyUsers.class);

			SyUsers users = (SyUsers) usersDAO.loadById(empId, SyUsers.class);
			return (UsersVO) users.toVO();

		} catch (Exception e) {
			logger.error("获取用户资料失败：", e);
			throw new OSException("获取用户资料失败：", e);
		}
	}

	public void delUsers(final Integer id) throws OSException,
			OSBussinessException {
		try {
			SyUsers bo = (SyUsers) usersDAO.loadById(id, SyUsers.class);
			bo.setStatus("-1");

			usersDAO.save(bo);
		} catch (Exception e) {
			logger.error("删除用户资料失败：", e);
			throw new OSException("删除用户资料失败：", e);
		}
	}

	public Integer addUsers(UsersVO vo) throws OSException,
			OSBussinessException {
		try {
			logger.debug("add user vo,id=" + vo.getId());
			SyUsers bo = new SyUsers(vo.getId());
			SemAppUtils.beanCopy(vo, bo);
			if (SemAppUtils.isNotEmpty(vo.getPassword())) {
				String password = commonRemote.encrytor(vo.getPassword());
				bo.setPassword(password);
			}
			bo.setRegisterDate(Calendar.getInstance());
			bo.setStatus("0");
			bo.setMark("3");
			SyDepartment department = (SyDepartment) departmentDAO.loadBoById(
					vo.getDepartment(), SyDepartment.class);
			bo.setSyDepartment(department);
			bo.setSyOrganise(department.getSyOrganise());
			bo.setIsAd(0);
			logger.debug(bo);
			Integer userId = usersDAO.save(bo);
			// this.publishMessage(1001, "新增注册用户", "新增注册用户成功,users id="
			// + bo.getLoginId());
			return userId;
		} catch (org.springframework.dao.DataIntegrityViolationException ee) {
			logger.error("添加用户失败，该用户已存在,mobile[" + vo.getLoginId() + "]", ee);
			// this.publishMessage(1001, "新增注册用户失败", "该用户已存在,users id="
			// + vo.getLoginId());
			throw new OSBussinessException("添加用户失败，该用户已存在");
		} catch (Exception e) {
			logger.error("新增用户资料失败：", e);
			// this.publishMessage(1001, "新增注册用户失败", "该用户资料失败,users id="
			// + vo.getLoginId() + ",ERROR MESSAGE=" + e.getMessage());
			throw new OSException("新增用户资料失败：", e);
		}
	}

	public void updateUsers(final UsersVO vo) throws OSException,
			OSBussinessException {
		try {
			if (vo != null) {
				SyUsers bo = (SyUsers) usersDAO.loadById(vo.getId(),
						SyUsers.class);
				// SemAppUtils.beanCopy(vo, bo);
				if (vo.getLoginId() != null)
					bo.setLoginId(vo.getLoginId());
				if (vo.getName() != null)
					bo.setName(vo.getName());
				if (vo.getCode() != null)
					bo.setCode(vo.getCode());
				if (vo.getEmail() != null)
					bo.setEmail(vo.getEmail());
				if (vo.getRemark() != null)
					bo.setRemark(vo.getRemark());
				if (vo.getMobile() != null)
					bo.setMobile(vo.getMobile());
				if (vo.getTel() != null)
					bo.setTel(vo.getTel());
				if (vo.getQq() != null) {
					if (SemAppUtils.isNotEmpty(vo.getQq())) {
						bo.setQq(vo.getQq());
					} else {
						bo.setQq(null);
					}

				}
				if (vo.getWechat() != null) {
					if (SemAppUtils.isNotEmpty(vo.getWechat())) {
						bo.setWechat(vo.getWechat());
					} else {
						bo.setWechat(null);
					}
				}
				if (vo.getWeibo() != null) {
					if (SemAppUtils.isNotEmpty(vo.getWeibo())) {
						bo.setWeibo(vo.getWeibo());
					} else {
						bo.setWeibo(null);
					}
				}
				usersDAO.save(bo);
			}
		} catch (Exception e) {
			logger.error("保存用户资料失败错误" + e);
			throw new OSException("保存用户资料失败错误", e);
		}
	}

	public void updateUsers(final UsersVO vo, String captchas)
			throws BaseException, BaseBusinessException {
		if (this.checkCaptchas(vo.getMobile(), captchas)) {
			try {
				DetachedCriteria criteria = DetachedCriteria
						.forClass(SyUsers.class);
				if (vo.getId() != null)
					criteria.add(Expression.eq("id", vo.getId()));
				if (SemAppUtils.isNotEmpty(vo.getMobile()))
					criteria.add(Expression.eq("mobile", vo.getMobile()));
				criteria.add(Expression.not(Expression.eq("status", "-1")));
				List list = usersDAO.findByCriteria(criteria, new Integer(0),
						new Integer(0));
				if (list.size() > 0) {
					SyUsers user = (SyUsers) list.get(0);
					if (vo.getQq() != null) {
						user.setQq(vo.getQq());
					}
					if (vo.getWechat() != null) {
						user.setWechat(vo.getWechat());
					}
					if (vo.getWeibo() != null) {
						user.setWeibo(vo.getWeibo());
					}
					usersDAO.save(user);
				} else {
					throw new BaseBusinessException("更新用户资料失败");
				}
			} catch (Exception e) {
				logger.error("更新用户失败错误：", e);
				throw new BaseException("更新用户资料失败错误：", e);
			}// updateUsers(vo);
		} else {
			throw new BaseBusinessException("验证码错误或者失效");
		}
	}

	public PageList getLieyuUsers(UsersVO vo, Integer firstResult,
			Integer fetchSize) throws OSException, OSBussinessException {
		logger.debug("getdcp for " + vo);
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(SyUsers.class);
			if (vo.getSearchValue() != null) {
				criteria.add(Expression.or(
						Expression.like("loginId", "%" + vo.getSearchValue()
								+ "%"),
						Expression.or(
								Expression.like("name",
										"%" + vo.getSearchValue() + "%"),
								Expression.or(
										Expression.like("mobile",
												"%" + vo.getSearchValue() + "%"),
										Expression.like("email",
												"%" + vo.getSearchValue() + "%")))));
			}
			// 获取用户tag
			// try {
			// List tagList = (List) this.invokeEjbServices(new Integer(1000),
			// new Serializable[] {});
			//
			// } catch (Exception ee) {
			// logger.error("get user tag failer",ee);
			// }
			// 剔除手机用户
			criteria.add(Expression.eq("syDepartment", new SyDepartment(
					new Integer(888))));
			criteria.add(Expression.not(Expression.eq("status", "-1")));
			List userList = usersDAO.findByCriteria(criteria, firstResult,
					fetchSize);
			List resultList = new ArrayList();
			Iterator iter = userList.iterator();
			while (iter.hasNext()) {
				SyUsers ubo = (SyUsers) iter.next();
				UsersVO uvo = (UsersVO) ubo.toVO();
				// get users tag
				// 获取用户tag
				try {
					List tagList = (List) this.invokeEjbServices(new Integer(
							1000), new Serializable[] { "" + uvo.getId() });
					uvo.setUserTag(tagList);
				} catch (Exception ee) {
					logger.error("get user tag failer", ee);
				}
				resultList.add(uvo);
			}
			return new PageList(resultList);
		} catch (Exception e) {
			logger.error("获取用户失败错误：", e);
			throw new OSException("获取用户资料失败错误：", e);
		}
	}

	public PageList getUsers(UsersVO vo, Integer firstResult, Integer fetchSize)
			throws OSException, OSBussinessException {
		logger.debug("getdcp for " + vo);
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(SyUsers.class);
			if (vo.getId() != null)
				criteria.add(Expression.like("id", "%" + vo.getId() + "%"));
			if (SemAppUtils.isNotEmpty(vo.getName()))
				criteria.add(Expression.like("name", "%" + vo.getName() + "%"));
			if (SemAppUtils.isNotEmpty(vo.getRemark()))
				criteria.add(Expression.like("remark", "%" + vo.getRemark()
						+ "%"));
			if (SemAppUtils.isNotEmpty(vo.getEmail()))
				criteria.add(Expression.like("email", "%" + vo.getEmail() + "%"));
			if (vo.getDepartment() != null)
				criteria.add(Expression.eq("syDepartment",
						new SyDepartment(vo.getDepartment())));
			if (vo.getOrganise() != null)
				criteria.add(Expression.eq("syOrganise",
						new SyOrganise(vo.getOrganise())));
			logger.debug("vo.getSearchValue()=" + vo.getSearchValue());
			if (vo.getSearchValue() != null) {
				criteria.add(Expression.or(
						Expression.like("loginId", "%" + vo.getSearchValue()
								+ "%"),
						Expression.or(
								Expression.like("name",
										"%" + vo.getSearchValue() + "%"),
								Expression.or(
										Expression.like("code",
												"%" + vo.getSearchValue() + "%"),
										Expression.like("email",
												"%" + vo.getSearchValue() + "%")))));
			}
			// 获取用户tag
			// try {
			// List tagList = (List) this.invokeEjbServices(new Integer(1000),
			// new Serializable[] {});
			//
			// } catch (Exception ee) {
			// logger.error("get user tag failer",ee);
			// }
			// 剔除手机用户
			criteria.add(Expression.not(Expression.eq("syDepartment",
					new SyDepartment(new Integer(888)))));
			criteria.add(Expression.not(Expression.eq("status", "-1")));
			return usersDAO.findByCriteriaByPage(criteria, firstResult,
					fetchSize);

		} catch (Exception e) {
			logger.error("获取用户失败错误：", e);
			throw new OSException("获取用户资料失败错误：", e);
		}
	}

	public PageList getOrganises(final Integer orId) throws BaseException,
			BaseBusinessException {
		try {
			// DetachedCriteria criteria = DetachedCriteria
			// .forClass(SyOrganise.class);
			// if (orId != null && orId.intValue() != 0) {
			// criteria.add(Expression.eq("id", orId));
			// }
			// criteria.add(Expression.not(Expression.eq("status", "-1")));
			// return usersDAO.findByCriteriaByPage(criteria, new Integer(0),
			// new Integer(0));
			return organiseDAO.getSubOrganiseList(orId.intValue());

		} catch (Exception e) {
			logger.error("获取组织失败错误：", e);
			throw new OSException("获取组织资料失败错误：", e);
		}

	}

	public OrganiseVO getOrganise(final Integer orId) throws BaseException,
			BaseBusinessException {
		try {
			SyOrganise organise = (SyOrganise) organiseDAO.loadById(orId,
					SyOrganise.class);
			return (OrganiseVO) organise.toVO();
		} catch (Exception e) {
			this.handleException(e);
			return null;
		}
	}

	public void delOrganise(final Integer id) throws BaseException,
			BaseBusinessException {
		try {
			SyOrganise bo = (SyOrganise) organiseDAO.loadById(id,
					SyOrganise.class);
			bo.setStatus("-1");
			organiseDAO.save(bo);
		} catch (Exception e) {

		}

	}

	public Integer addOrganise(OrganiseVO vo) throws OSException,
			OSBussinessException {
		logger.debug("add organise vo,id=" + vo.getId());
		SyOrganise bo = new SyOrganise(vo.getId());
		SemAppUtils.beanCopy(vo, bo);
		return (Integer) organiseDAO.save(bo);

	}

	public void updateOrganise(final OrganiseVO vo) throws BaseException,
			BaseBusinessException {

		try {
			if (vo != null) {
				SyOrganise bo = (SyOrganise) organiseDAO.loadById(vo.getId(),
						SyOrganise.class);
				SemAppUtils.beanCopy(vo, bo);
				organiseDAO.save(bo);
			}
		} catch (Exception e) {
			this.handleException(e);
		}

	}

	public PageList getOrganise(OrganiseVO vo, Integer firstResult,
			Integer fetchSize) throws OSException, OSBussinessException {
		logger.debug("getdcp for " + vo);
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(SyOrganise.class);
			if (vo != null && vo.getId() != null)
				criteria.add(Expression.eq("id", vo.getId()));
			if (vo != null && SemAppUtils.isNotEmpty(vo.getName()))
				criteria.add(Expression.like("name", "%" + vo.getName() + "%"));
			if (vo != null && SemAppUtils.isNotEmpty(vo.getRemark()))
				criteria.add(Expression.like("remark", "%" + vo.getRemark()
						+ "%"));
			if (vo != null && vo.getParentId() != null)
				;
			criteria.add(Expression.eq("parentId", vo.getParentId()));
			return organiseDAO.findByCriteriaByPage(criteria, firstResult,
					fetchSize);

		} catch (Exception e) {
			logger.error("获取组织失败错误：", e);
			throw new OSException("获取组织资料失败错误：", e);
		}
	}

	public PageList getDuty(String organiseID, String parentId)
			throws OSException, OSBussinessException {
		DutyVO vo = new DutyVO();
		vo.setParentId(parentId);
		return getDuty(vo, new Integer(0), new Integer(0));
	}

	public PageList getDuty(DutyVO vo, Integer firstResult, Integer fetchSize)
			throws OSException, OSBussinessException {
		logger.debug("get for " + vo);
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(SyDuty.class);
			if (vo.getId() != null && Integer.parseInt("" + vo.getId()) != 0)
				criteria.add(Expression.eq("id", (Integer) vo.getId()));
			if (vo.getParentId() != null)
				criteria.add(Expression.eq("parentId", vo.getParentId()));
			if (SemAppUtils.isNotEmpty(vo.getName()))
				criteria.add(Expression.like("name", "%" + vo.getName() + "%"));
			if (SemAppUtils.isNotEmpty(vo.getNote()))
				criteria.add(Expression.like("note", "%" + vo.getNote() + "%"));
			if (vo.getMark() != null) {
				criteria.add(Expression.eq("mark", vo.getMark()));
			} else {
				criteria.add(Expression.eq("mark", "0"));
			}
			return dutyDAO.findByCriteriaByPage(criteria, firstResult,
					fetchSize);

		} catch (Exception e) {
			logger.error("获取职务层级失败：", e);
			throw new OSException("获取职务层级失败：", e);
		}
	}

	public void delDuty(final Integer id) throws OSException,
			OSBussinessException {

		try {
			SyDuty bo = (SyDuty) dutyDAO.loadById(id, SyDuty.class);
			bo.setMark("-1");
			departmentDAO.save(bo);

		} catch (Exception e) {
			logger.error("删除职务层级失败：", e);
			throw new OSException("删除职务层级失败：", e);
		}
	}

	public Integer addDuty(DutyVO vo) throws OSException, OSBussinessException {
		try {
			logger.debug("add user vo,id=" + vo.getId());
			SyDuty bo = new SyDuty();
			SemAppUtils.beanCopy(vo, bo);
			bo.setMark("0");// 表示有效
			return (Integer) dutyDAO.save(bo);
		} catch (Exception e) {
			logger.error("新增职务层级失败：", e);
			throw new OSException("新增职务层级失败：", e);
		}
	}

	public void updateDuty(final DutyVO vo) throws OSException,
			OSBussinessException {
		try {
			if (vo != null) {
				SyDuty bo = (SyDuty) dutyDAO.loadById(vo.getId(), SyDuty.class);
				SemAppUtils.beanCopy(vo, bo);
				bo.setId(null);
				bo.setMark("0");// 表示有效
				departmentDAO.save(bo);
			}
		} catch (Exception e) {
			logger.error("保存职务层级失败错误" + e);
			throw new OSException("保存职务层级失败错误", e);
		}
	}

	public DutyVO getDuty(Integer id) throws OSException, OSBussinessException {
		SyDuty department = (SyDuty) dutyDAO.findById(id, SyDuty.class);
		return (DutyVO) department.toVO();
	}

	public UsersVO getDeptTopDirector(String deptID) throws OSException,
			OSBussinessException {
		SyDepartment department = (SyDepartment) departmentDAO.findBoById(
				deptID, SyDepartment.class);
		return getUserInfo(department.getLeaderId());
	}

	public DepartmentVO getDeptOfLevel(String deptID, Integer level)
			throws OSException, OSBussinessException {
		return null;
	}

	public String getUserToken(Integer empId) throws OSException,
			OSBussinessException {
		String token = onlineDAO.getUserToken(empId);
		// deal toke,replace all "+" to "-"
		// token = token.replace("+", "-");
		return token;
	}

	public Integer verifyUser(String username, String password)
			throws BaseException, BaseBusinessException {
		DetachedCriteria criteria = DetachedCriteria.forClass(SyUsers.class);
		criteria.add(Expression.or(Expression.eq("loginId", username),
				Expression.eq("mobile", username)));
		// PageList list = usersDAO.findByCriteriaByPage(criteria, new
		// Integer(0),
		// new Integer(0));
		// if (list.getResults() < 0) {
		// logger.error("用户名不存在" + username);
		// throw new BaseException("用户名不存在");
		// }
		List list = usersDAO.findByCriteria(criteria, 0, 0);
		if (list == null || list.size() < 1) {
			logger.error("用户名不存在" + username);
			throw new BaseException("用户名不存在");
		}

		SyUsers vo = (SyUsers) list.get(0);
		if (vo.getIsAd() != null && vo.getIsAd().intValue() == 1) {
			throw new BaseException("您的账户被大量用户举报，涉嫌发布非法信息，已被封号。");
		}
		// password = ;
		if (commonRemote.encrytor(password).equals(vo.getPassword())
				|| password.equals(DigestUtils.md5Hex(commonRemote.decrytor(vo
						.getPassword())))) {
			return vo.getId();
		} else {
			logger.error("密码有误" + username);
			throw new BaseException("密码有误");
		}

	}

	public UsersVO getEofficeLoginUser(String openId) throws BaseException,
			BaseBusinessException {
		DetachedCriteria criteria = DetachedCriteria.forClass(SyUsers.class);
		// criteria.add(Expression.or(Expression.eq("wechat", openId),
		// Expression.or(Expression.eq("qq", openId), Expression.eq(
		// "weibo", openId))));
		criteria.add(Expression.or(
				Expression.eq("wechat", openId),
				Expression.or(Expression.eq("qq", openId),
						Expression.eq("weibo", openId))));
		PageList list = usersDAO.findByCriteriaByPage(criteria, new Integer(0),
				new Integer(0));
		if (list.getResults() <= 0) {
			logger.error("该用户未注册" + openId);
			throw new OSBussinessException("该用户未注册");
		}
		UsersVO vo = (UsersVO) list.getItems().get(0);
		if (vo.getIsAd() != null && vo.getIsAd().intValue() == 1) {
			throw new BaseException("您的账户被大量用户举报，涉嫌发布非法信息，已被封号。");
		}
		return vo;

	}

	public UsersVO getEofficeLoginUser(String key, String ip, String authKey,
			Calendar expiredTime, int type, boolean isTest, boolean checkActive)
			throws BaseException, BaseBusinessException {
		UsersVO vo = onlineDAO.getEofficeLoginUser(ip, authKey, expiredTime,
				type, isTest, checkActive);
		if (vo.getIsAd() != null && vo.getIsAd().intValue() == 1) {
			throw new BaseException("您的账户被大量用户举报，涉嫌发布非法信息，已被封号。");
		} else {
			return vo;
		}
	}

	public void logonOASystem(final Integer empId, final String ip)
			throws OSException, OSBussinessException {
		try {
			EiaOnline online = null;
			try {
				online = (EiaOnline) onlineDAO.findById(empId, EiaOnline.class);
			} catch (Exception ee) {
				logger.info("new user");
			}
			int activeNum = 0;
			if (online == null) {
				online = new EiaOnline(empId);
				online.setAuthkey(SemAppUtils.getRandomString(24));
			} else {
				activeNum = online.getActiveNum().intValue();
			}
			online.setActiveTime(Calendar.getInstance());
			online.setLoginTime(Calendar.getInstance());
			online.setLastloginTime(Calendar.getInstance());
			online.setIp(ip);
			online.setActiveNum(new Integer(activeNum++));
			online.setActiveNodeId(new Integer(1));
			online.setStatus("1");
			onlineDAO.save(online);
		} catch (Exception e) {
			logger.error("更新状态失败", e);
			throw new OSException("更新状态失败", e);
			// this.handleException(e);
		}
	}

	public void logoutOASystem(final Integer empId) throws OSException,
			OSBussinessException {
		try {
			EiaOnline online = null;
			try {
				online = (EiaOnline) onlineDAO.findById(empId, EiaOnline.class);
			} catch (Exception ee) {
				logger.info("new user");
			}
			online.setStatus("0");
			onlineDAO.save(online);
		} catch (Exception e) {
			logger.error("更新状态失败", e);
			throw new OSException("更新状态失败", e);
			// this.handleException(e);
		}

	}

	/**
	 * duty maintain
	 */
	public PageList getDepartment(Integer organiseID, Integer parentId)
			throws OSException, OSBussinessException {
		DepartmentVO vo = new DepartmentVO();
		vo.setParentId(parentId);
		vo.setOrganiseId(organiseID);
		return getDepartment(vo, new Integer(0), new Integer(0));
	}

	public PageList getDepartment(DepartmentVO vo, Integer firstResult,
			Integer fetchSize) throws OSException, OSBussinessException {
		logger.debug("getdcp for " + vo);

		try {
			Integer parentId = vo.getParentId();
			if (parentId != null && parentId == 0) {
				if (vo.getOrganiseId() != null) {
					OrganiseVO organise = this.getOrganise(vo.getOrganiseId());
					int idInt = organise.getId().intValue();
					organise.setId(new Integer(0 - idInt));
					List list = new ArrayList();
					list.add(organise);
					PageList pl = new PageList(list);
					return pl;
				} else {
					try {
						DetachedCriteria criteria = DetachedCriteria
								.forClass(SyOrganise.class);
						criteria.add(Expression.eq("status", "1"));
						criteria.add(Expression.gt("id", new Integer(0)));

						List list = organiseDAO.findByCriteria(criteria,
								firstResult, fetchSize);
						List resultList = new ArrayList();
						Iterator iter = list.iterator();
						while (iter.hasNext()) {
							SyOrganise bo = (SyOrganise) iter.next();
							int id = bo.getId().intValue();
							OrganiseVO theVO = (OrganiseVO) bo.toVO();
							theVO.setId(0 - id);
							resultList.add(theVO);
						}
						return new PageList(resultList);

					} catch (Exception e) {
						logger.error("获取部门失败错误：", e);
						throw new OSException("获取部门资料失败错误：", e);
					}

				}
			} else {
				DetachedCriteria criteria = DetachedCriteria
						.forClass(SyDepartment.class);
				if (vo.getId() != null
						&& Integer.parseInt("" + vo.getId()) != 0)
					criteria.add(Expression.eq("id", (Integer) vo.getId()));
				if (vo.getParentId() != null) {
					int paInt = vo.getParentId().intValue();
					if (paInt < 0) {
						paInt = 0 - paInt;
						SyOrganise organise = new SyOrganise(new Integer(paInt));
						criteria.add(Expression.eq("syOrganise", organise));
						criteria.add(Expression.eq("parentId", new Integer(0)));
					} else {
						criteria.add(Expression.eq("parentId", vo.getParentId()));
					}
				}
				if (SemAppUtils.isNotEmpty(vo.getName()))
					criteria.add(Expression.like("name", "%" + vo.getName()
							+ "%"));
				if (SemAppUtils.isNotEmpty(vo.getRemark()))
					criteria.add(Expression.like("remark", "%" + vo.getRemark()
							+ "%"));
				criteria.add(Expression.eq("status", new Integer(1)));
				return departmentDAO.findByCriteriaByPage(criteria,
						firstResult, fetchSize);
			}

		} catch (Exception e) {
			logger.error("获取组织架构错误：", e);
			throw new OSException("获取组织架构失败错误：", e);
		}
	}

	public void delDepartment(final Integer id) throws OSException,
			OSBussinessException {

		try {
			SyDepartment bo = (SyDepartment) departmentDAO.loadBoById(id,
					SyDepartment.class);
			bo.setStatus(new Integer(-1));
			departmentDAO.save(bo);
		} catch (Exception e) {
			logger.error("删除部门资料失败：", e);
			throw new OSException("删除部门资料失败：", e);
		}
	}

	public String addDepartment(DepartmentVO vo) throws OSException,
			OSBussinessException {
		try {
			logger.debug("add user vo,id=" + vo.getId());

			SyDepartment bo = new SyDepartment(vo.getId());
			Integer parentID = vo.getParentId();
			SemAppUtils.beanCopy(vo, bo);
			if (parentID == null) {
				parentID = new Integer(0);
			}
			if (parentID.intValue() < 0) {
				int organiseId = 0 - parentID.intValue();
				bo.setSyOrganise(new SyOrganise(new Integer(organiseId)));
				bo.setParentId(new Integer(0));
			} else {
				if (vo.getOrganiseId() == null) {
					bo.setSyOrganise(new SyOrganise(new Integer(0)));
				} else {
					bo.setSyOrganise(new SyOrganise(new Integer(vo
							.getOrganiseId())));
				}
			}
			bo.setId(null);
			bo.setRegisterDate(Calendar.getInstance());
			bo.setStatus(new Integer(1));
			return "" + departmentDAO.save(bo);
		} catch (Exception e) {
			logger.error("新增部门资料失败：", e);
			throw new OSException("新增部门资料失败：", e);
		}
	}

	public void updateDepartment(final DepartmentVO vo) throws OSException,
			OSBussinessException {
		try {
			if (vo != null) {
				SyDepartment bo = (SyDepartment) departmentDAO.loadBoById(
						vo.getId(), SyDepartment.class);
				// SemAppUtils.beanCopy(vo, bo);
				bo.setName(vo.getName());
				bo.setRemark(vo.getRemark());
				bo.setAbbr(vo.getAbbr());
				// bo.set
				departmentDAO.save(bo);
			}
		} catch (Exception e) {
			logger.error("保存部门资料失败错误" + e);
			throw new OSException("保存部门资料失败错误", e);
		}
	}

	public DepartmentVO getDepartment(String deptID) throws OSException,
			OSBussinessException {
		SyDepartment department = (SyDepartment) departmentDAO.findBoById(
				deptID, SyDepartment.class);
		return (DepartmentVO) department.toVO();
	}

	/**
	 * modify user properties
	 */

	public PageList getUserProperties(UserPropertiesVO vo, Integer firstResult,
			Integer fetchSize) throws OSException, OSBussinessException {
		logger.debug("get for " + vo);
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(UserProperties.class);
			if (vo.getId() != null && Integer.parseInt("" + vo.getId()) != 0)
				criteria.add(Expression.eq("id", (Integer) vo.getId()));
			if (vo.getProperty() != null)
				criteria.add(Expression.eq("name", vo.getProperty()));
			if (vo.getUsId() != null)
				criteria.add(Expression.eq("usId", vo.getUsId()));
			return userPropertiesDAO.findByCriteriaByPage(criteria,
					firstResult, fetchSize);

		} catch (Exception e) {
			logger.error("获取用户属性失败：", e);
			throw new OSException("获取用户属性失败：", e);
		}
	}

	public void delUserProperties(final Integer id) throws OSException,
			OSBussinessException {

		try {
			UserProperties bo = (UserProperties) dutyDAO.loadBoById(id,
					UserProperties.class);
			userPropertiesDAO.delete(bo);
		} catch (Exception e) {
			logger.error("删除用户属性资料失败：", e);
			throw new OSException("删除用户属性资料失败：", e);
		}
	}

	public String addUserProperties(UserPropertiesVO vo) throws OSException,
			OSBussinessException {
		try {
			// return (String) doInTransaction(new HibernateCallback() {
			// public Object doInHibernate(Session session) {
			logger.debug("add user vo,id=" + vo.getId());
			UserProperties bo = new UserProperties();
			SemAppUtils.beanCopy(vo, bo);
			bo.setId(vo.getProperty().getName() + vo.getUsId());
			SystemProperties spBo = new SystemProperties();
			// SystemProperties spBo = (SystemProperties) session.load(
			// SystemProperties.class, vo.getProperty().getName());
			spBo.setId(vo.getProperty().getName());
			bo.setName(spBo);
			userPropertiesDAO.saveOrUpdate(bo);
			return null;
			// }
			// });
		} catch (Exception e) {
			logger.error("新增用户属性失败：", e);
			throw new OSException("新增用户属性失败：", e);
		}
	}

	public void updateUserProperties(final UserPropertiesVO vo)
			throws OSException, OSBussinessException {
		try {
			if (vo != null) {
				UserProperties bo = (UserProperties) userPropertiesDAO
						.loadBoById(vo.getId(), UserProperties.class);
				SemAppUtils.beanCopy(vo, bo);
				bo.setId(null);
				userPropertiesDAO.save(bo);
			}
		} catch (Exception e) {
			logger.error("保存用户属性失败错误" + e);
			throw new OSException("保存用户属性失败错误", e);
		}
	}

	public UserPropertiesVO getUserProperties(String id) throws OSException,
			OSBussinessException {
		UserProperties userProperties = (UserProperties) userPropertiesDAO
				.findBoById(id, UserProperties.class);
		return (UserPropertiesVO) userProperties.toVO();
	}

	public Map getUserProperties(Integer empId) throws OSException,
			OSBussinessException, RemoteException {
		Map upMap = new HashMap();
		UserPropertiesVO vo = new UserPropertiesVO();
		vo.setUsId(empId);
		PageList list = this.getUserProperties(vo, new Integer(0), new Integer(
				0));
		Iterator iter = list.getItems().iterator();
		while (iter.hasNext()) {
			UserPropertiesVO resultVO = (UserPropertiesVO) iter.next();
			upMap.put(resultVO.getProperty().getName(), resultVO.getValue());
		}
		return upMap;

	}

	public void changePassword(String username, String password,
			String oldPassword) throws BaseException, BaseBusinessException {
		DetachedCriteria criteria = DetachedCriteria.forClass(SyUsers.class);
		criteria.add(Expression.or(Expression.or(
				Expression.eq("id", SemAppUtils.getInteger(username)),
				Expression.eq("loginId", username)), Expression.eq("mobile",
				username)));
		List list = usersDAO.findByCriteria(criteria, 0, 0);
		if (list.size() <= 0) {
			throw new OSBussinessException("用户名不存在");
		}
		SyUsers bo = (SyUsers) list.get(0);
		if (oldPassword != null && !password.equals(oldPassword)) {
			throw new OSBussinessException("密码有误");
		} else {
			password = commonRemote.encrytor(password);
			bo.setPassword(password);
			usersDAO.save(bo);
		}

	}

	public void changeName(String username, String nick, String icon,
			String sex, Calendar birthday) throws OSException,
			OSBussinessException {
		DetachedCriteria criteria = DetachedCriteria.forClass(SyUsers.class);
		criteria.add(Expression.or(Expression.or(
				Expression.eq("id", SemAppUtils.getInteger(username)),
				Expression.eq("loginId", username)), Expression.eq("mobile",
				username)));
		// criteria.add(Expression.eq("status",new Integer(1)));

		List list = usersDAO.findByCriteria(criteria, 0, 0);
		if (list.size() <= 0) {
			throw new OSBussinessException("用户名不存在");
		}
		SyUsers bo = (SyUsers) list.get(0);
		// if (!password.equals(oldPassword)){
		// throw new OSBussinessException("密码有误");
		// }else{
		bo.setName(nick);
		bo.setMark(icon);// mark转义为头像
		if (SemAppUtils.isNotEmpty(sex))
			bo.setSex(sex);
		if (birthday != null)
			bo.setBirthday(birthday);
		usersDAO.save(bo);
	}

	public void changeName(String username, String name, String icon)
			throws OSException, OSBussinessException {
		changeName(username, name, icon, null, null);
		// }
	}

	public String getToken(String userId) throws BaseBusinessException,
			BaseException, RemoteException {
		DetachedCriteria criteria = DetachedCriteria.forClass(SyUsers.class);
		criteria.add(Expression.or(Expression.or(
				Expression.eq("id", SemAppUtils.getInteger(userId)),
				Expression.eq("loginId", userId)), Expression.eq("mobile",
				userId)));
		List list = usersDAO.findByCriteria(criteria, 0, 0);
		if (list.size() <= 0) {
			throw new OSBussinessException("用户名不存在");
		}
		SyUsers bo = (SyUsers) list.get(0);
		return getToken(userId, bo.getName(),
				SemAppUtils.getFullPath(bo.getMark()));
	}

	public String getToken(String userId, String userName, String portraitUri)
			throws BaseBusinessException, BaseException {
		try {
			// encrypt the userid
			String token = helperRemote.getUserToken(SemAppUtils
					.getInteger(userId));
			if (token == null) {
				// String imUserId = SemAppUtils.encrytor(userId);
				// SdkHttpResult httpResult = this.imClient.getToken(imUsername,
				// imPassword, imUserId, userName, portraitUri,
				// FormatType.json);
				// String result = httpResult.getResult();
				// int code = httpResult.getHttpCode();
				// Map resultMap = SemWebAppUtils.getMap4Json(result);
				// logger.debug("get token result=" + result + "&code=" + code);
				// if (code != 200) {
				// throw new BaseException(httpResult.getResult());
				// } else {
				// token = (String) resultMap.get("token");
				// helperRemote.putUserToken(SemAppUtils.getInteger(userId),
				// token);
				// }
			}
			return token;
		} catch (Exception e) {
			this.handleException(e);
		}
		return null;
	}

	public String checkOnline(String userId) throws BaseBusinessException,
			BaseException {
		try {
			// encrypt the userid
			// String imUserId = SemAppUtils.encrytor(userId);
			// SdkHttpResult httpResult = this.imClient.checkOnline(imUsername,
			// imPassword, imUserId, FormatType.json);
			// return httpResult.getResult();
		} catch (Exception e) {
			this.handleException(e);
		}
		return null;
	}

	public String publishMessage(String fromUserId, List toUserIds, Message msg)
			throws BaseBusinessException, BaseException {
		try {
			// encrypt the userid
			String imUserId = commonRemote.encrytor(fromUserId);
			List toImUserId = new ArrayList();
			Iterator iter = toUserIds.iterator();
			while (iter.hasNext()) {
				String to = (String) iter.next();
				toImUserId.add(commonRemote.encrytor(to));
			}
			// SdkHttpResult httpResult = imClient.publishMessage(imUsername,
			// imPassword, imUserId, toUserIds, msg, FormatType.json);
			// if (httpResult.getHttpCode() != 200) {
			// throw new BaseException(httpResult.getResult());
			// } else {
			// return httpResult.getResult();
			// }

		} catch (Exception e) {
			this.handleException(e);
		}
		return null;
	}

	public String publishMessage(String fromUserId, List toUserIds,
			Message msg, String pushContent, String pushData)
			throws BaseBusinessException, BaseException {
		try {
			// encrypt the userid
			String imUserId = commonRemote.encrytor(fromUserId);
			List toImUserId = new ArrayList();
			Iterator iter = toUserIds.iterator();
			while (iter.hasNext()) {
				String to = (String) iter.next();
				toImUserId.add(commonRemote.encrytor(to));
			}
			// SdkHttpResult httpResult = imClient.publishMessage(imUsername,
			// imPassword, imUserId, toUserIds, msg, pushContent,
			// pushData, FormatType.json);
			// if (httpResult.getHttpCode() != 200) {
			// throw new BaseException(httpResult.getResult());
			// } else {
			// return httpResult.getResult();
			// }
		} catch (Exception e) {
			this.handleException(e);
		}
		return null;
	}

	public String publishSystemMessage(String fromUserId, List toUserIds,
			Message msg, String pushContent, String pushData)
			throws BaseBusinessException, BaseException {
		try {
			// encrypt the userid
			String imUserId = commonRemote.encrytor(fromUserId);
			List toImUserId = new ArrayList();
			Iterator iter = toUserIds.iterator();
			while (iter.hasNext()) {
				String to = (String) iter.next();
				toImUserId.add(commonRemote.encrytor(to));
			}
			// SdkHttpResult httpResult = imClient.publishSystemMessage(
			// imUsername, imPassword, imUserId, toUserIds, msg,
			// pushContent, pushData, FormatType.json);
			// if (httpResult.getHttpCode() != 200) {
			// throw new BaseException(httpResult.getResult());
			// } else {
			// return httpResult.getResult();
			// }
		} catch (Exception e) {
			this.handleException(e);
		}
		return null;
	}

	// 调用启动接口发送短信验证码然后把验证码跟手机绑定在一个地方
	public void sendMessageByMobile(String destMobile, String content)
			throws BaseException, BaseBusinessException {
		try {
			String[] contents = new String[1];
			contents[0] = content;
			commonRemote.sendMessageByMobile(destMobile, contents);

		} catch (Exception e) {
			logger.error("发送验证码失败", e);
			this.handleException(e);
			// throw new LieyuException("发送验证码失败", e);
		}
	}

	public String getCaptchas(String mobile, final SessionUserBean user)
			throws Exception {
		// 看是否异步发送短信
		final String modelmes = this.getProperty("SEND_MOBILE_MODE");
		try {
			// 随机获取验证码
			String vcode = "";
			for (int i = 0; i < 6; i++) {
				vcode = vcode + (int) (Math.random() * 9);
			}
			String content = "你的验证码是：" + vcode + "。请不要把验证码泄露给其他人，如非本人操作，可不用理会";
			logger.info("content:" + content + "modelmes:" + modelmes);
			String[] contents = new String[1];
			contents[0] = content;
			// 放队列中，发送短信验证码
			if (modelmes != null && modelmes.equals("0")) {
				commonRemote.sendMessageByMobile(mobile, contents);
				// return vcode;
			}

			// Lymobilecode bo = new Lymobilecode();
			// bo.setMobile(mobile);
			// bo.setVcode(vcode);
			// bo.setCreatedate(Calendar.getInstance());
			// Calendar cal = Calendar.getInstance();
			// cal.add(Calendar.MINUTE, 10);
			// bo.setInvaliddate(cal);
			// bo.setInvalid("0");
			// userPropertiesDAO.save(bo);
			return vcode;
		} catch (Exception e) {
			logger.error("获取验证码失败", e);
			this.handleException(e);
		}
		return null;
	}

	public boolean checkCaptchas(String mobile, String captchas)
			throws BaseBusinessException {
		boolean result = false;
		// DetachedCriteria criteriamobile = DetachedCriteria
		// .forClass(Lymobilecode.class);
		// criteriamobile.add(Expression.eq("vcode", captchas));
		// criteriamobile.add(Expression.eq("mobile", mobile));
		// criteriamobile.add(Expression.eq("invalid", "0"));
		// logger.info("registeruser:" + mobile + ":" + captchas);
		// List returnlistm = userPropertiesDAO.findByCriteria(criteriamobile,
		// new Integer(0), new Integer(0));
		// if (returnlistm != null && returnlistm.size() > 0) {
		// result = true;
		// } else {
		// throw new BaseBusinessException("验证码错误或者失效");
		// }

		return result;
	}

	// 请求注册用户
	public Integer registerUser(final UsersVO vo, final String captchas)
			throws Exception {

		try {
			// openHibernateSession();

			// return (Integer) doInTransaction(new HibernateCallback() {
			// public Object doInHibernate(Session session)
			// throws BaseException, BaseBusinessException {
			// DetachedCriteria criteriamobile = DetachedCriteria
			// .forClass(Lymobilecode.class);
			// criteriamobile.add(Expression.eq("vcode", captchas));
			// criteriamobile.add(Expression.eq("mobile", vo.getMobile()));
			// criteriamobile.add(Expression.eq("invalid", "0"));
			// logger.info("registeruser:" + vo.getMobile() + ":"
			// + captchas);
			// List returnlistm = userPropertiesDAO.findByCriteria(
			// criteriamobile, new Integer(0), new Integer(0));
			// if (returnlistm != null && returnlistm.size() > 0) {
			// DetachedCriteria criteriauser = DetachedCriteria
			// .forClass(Lyuser.class);
			// criteriauser.add(Expression
			// .eq("mobile", vo.getMobile()));
			// List returnlistuser = userPropertiesDAO.findByCriteria(
			// criteriauser, new Integer(0), new Integer(0));
			// if (returnlistuser == null
			// || returnlistuser.size() == 0) {
			// logger.debug("zhixing");
			// Integer id = SemAppUtils.registerUser(vo
			// .getMobile(), vo.getPassword());
			// Lyuser bo = new Lyuser(id);
			// bo.setUsername(vo.getMobile());
			// bo.setUsernick(vo.getUsernick());
			// // 普通用户角色
			// Lyrole role = (Lyrole) userPropertiesDAO.findById(
			// new Integer(1), Lyrole.class);
			// bo.setRole(role);
			// bo.setCreatedate(Calendar.getInstance());
			// bo.setModifydate(Calendar.getInstance());
			// bo.setMobile(vo.getMobile());
			// if (vo.getPassword() != null) {
			// bo.setPassword(SemAppUtils.encrytor(vo
			// .getPassword()));
			// }
			// bo.setAvatar_img(vo.getAvatar_img());
			// bo.setEnabled(new Integer(1));
			// bo.setLocked(new Integer(0));
			// bo.setCreatedate(Calendar.getInstance());
			// bo.setModifydate(Calendar.getInstance());
			// // Integer a = cabinetDAO.save(bo);
			// userPropertiesDAO.registerlyUser(bo, id, role
			// .getId());
			// logger.debug("id is what:" + id);
			// // bo.setId(id);
			//
			// Lymobilecode mobile = (Lymobilecode) returnlistm
			// .get(0);
			// mobile.setInvalid("1");
			// userPropertiesDAO.save(mobile);
			// return id;
			// } else {
			// logger.info("mobile is used 了");
			// // return null;
			// throw new OSBussinessException("该手机已经注册过了");
			//
			// }
			//
			// } else {
			// throw new OSBussinessException("验证码错误或者失效");
			// }
			// }
			// });

		} catch (Exception e) {
			logger.error("注册用户失败", e);

			this.handleException(e);
			return null;
		}

		return null;
	}

	// accountAction?action=update POST 请求忘记密码
	public Integer updateregisterUser(final UsersVO vo, final String captchas)
			throws Exception {

		try {
			// doInTransaction(new HibernateCallback() {
			// public Object doInHibernate(Session session)
			// throws OSBussinessException {
			// // 科长框架里面的用户名和密码也要跟着变化
			// DetachedCriteria criteriamobile = DetachedCriteria
			// .forClass(Lymobilecode.class);
			// criteriamobile.add(Expression.eq("vcode", captchas));
			// criteriamobile.add(Expression
			// .eq("mobile", vo.getUsername()));
			// criteriamobile.add(Expression.eq("invalid", "0"));
			// List returnlistm = userPropertiesDAO.findByCriteria(
			// criteriamobile, new Integer(0), new Integer(0));
			// if (returnlistm != null && returnlistm.size() > 0) {
			// DetachedCriteria criteria = DetachedCriteria
			// .forClass(Lyuser.class);
			// criteria.add(Expression.eq("mobile", vo.getMobile()));
			// // criteria.add(Expression.eq("password",
			// // vo.getPassword()));
			// List returnlist = userPropertiesDAO.findByCriteria(
			// criteria, new Integer(0), new Integer(0));
			// if (returnlist != null && returnlist.size() == 1) {
			// Lyuser bos = (Lyuser) returnlist.get(0);
			// bos.setModifydate(Calendar.getInstance());
			// if (vo.getNewpassword() != null)
			// bos.setPassword(vo.getNewpassword());
			// bos.setModifydate(Calendar.getInstance());
			// bos.setEnabled(new Integer(1));
			// bos.setLocked(new Integer(0));
			// return userPropertiesDAO.save(bos);
			//
			// } else {
			// throw new OSBussinessException("用户不存在");
			// }
			// } else {
			// throw new OSBussinessException("验证码错误或者失效");
			// }
			// }
			// });
		} catch (Exception e) {
			logger.error("更新用户失败", e);
			this.handleException(e);
			// throw new LieyuException("更新用户请求异常", e);
		}
		return 1;
	}

	// accountAction?action=save 请求修改用户头像,昵称
	public Integer updateLyUser(final UsersVO vo) throws Exception {

		try {
			// doInTransaction(new HibernateCallback() {
			// public Object doInHibernate(Session session) {
			//
			// Lyuser bos = (Lyuser) userPropertiesDAO.findById(vo
			// .getUserid(), Lyuser.class);
			//
			// if (bos != null) {
			// if (vo.getAvatar_img() != null)
			// bos.setAvatar_img(vo.getAvatar_img());
			// if (vo.getUsernick() != null)
			// bos.setUsernick(vo.getUsernick());
			// bos.setModifydate(Calendar.getInstance());
			// return userPropertiesDAO.save(bos);
			//
			// }
			// return 1;
			// }
			// });
		} catch (Exception e) {
			logger.error("更新用户头像失败", e);
			// throw new LieyuException("更新用户头像请求异常", e);
			// throw new LieyuException("用户登陆失败", e);
			this.handleException(e);
		}
		return 1;
	}

	public List getSubOrganises(int organise) throws OSException {
		return organiseDAO.getSubOrganises(organise);
	}

}
