package com.zjianhao.daoImpl;

import static org.hibernate.criterion.Example.create;


import java.util.List;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zjianhao.dao.AdminDao;
import com.zjianhao.model.Admin;
public class AdminDaoImpl implements AdminDao {
	private static final Logger log = LoggerFactory.getLogger(AdminDaoImpl.class);
	// property constants
	public static final String ADMIN_NAME = "adminName";
	public static final String EMAIL = "email";
	public static final String PASSWORD = "password";

	private SessionFactory sessionFactory;

	/* (non-Javadoc)
	 * @see com.zjianhao.daoImpl.AdminDao#setSessionFactory(org.hibernate.SessionFactory)
	 */
	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see com.zjianhao.daoImpl.AdminDao#save(com.zjianhao.model.Admin)
	 */
	@Override
	public void save(Admin transientInstance) {
		log.debug("saving Admin instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zjianhao.daoImpl.AdminDao#delete(com.zjianhao.model.Admin)
	 */
	@Override
	public void delete(Admin persistentInstance) {
		log.debug("deleting Admin instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zjianhao.daoImpl.AdminDao#findById(java.lang.Integer)
	 */
	@Override
	public Admin findById(java.lang.Integer id) {
		log.debug("getting Admin instance with id: " + id);
		try {
			Admin instance = (Admin) getCurrentSession().get(
					"com.bbs.model.Admin", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zjianhao.daoImpl.AdminDao#findByExample(com.zjianhao.model.Admin)
	 */
	@Override
	public List<Admin> findByExample(Admin instance) {
		log.debug("finding Admin instance by example");
		try {
			List<Admin> results = (List<Admin>) getCurrentSession()
					.createCriteria("com.zjianhao.model.Admin")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zjianhao.daoImpl.AdminDao#findByProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Admin instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Admin as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zjianhao.daoImpl.AdminDao#findByAdminName(java.lang.Object)
	 */
	@Override
	public List<Admin> findByAdminName(Object adminName) {
		return findByProperty(ADMIN_NAME, adminName);
	}

	/* (non-Javadoc)
	 * @see com.zjianhao.daoImpl.AdminDao#findByEmail(java.lang.Object)
	 */
	@Override
	public List<Admin> findByEmail(Object email) {
		return findByProperty(EMAIL, email);
	}

	/* (non-Javadoc)
	 * @see com.zjianhao.daoImpl.AdminDao#findByPassword(java.lang.Object)
	 */
	@Override
	public List<Admin> findByPassword(Object password) {
		return findByProperty(PASSWORD, password);
	}

	/* (non-Javadoc)
	 * @see com.zjianhao.daoImpl.AdminDao#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all Admin instances");
		try {
			String queryString = "from Admin";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zjianhao.daoImpl.AdminDao#merge(com.zjianhao.model.Admin)
	 */
	@Override
	public Admin merge(Admin detachedInstance) {
		log.debug("merging Admin instance");
		try {
			Admin result = (Admin) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zjianhao.daoImpl.AdminDao#attachDirty(com.zjianhao.model.Admin)
	 */
	@Override
	public void attachDirty(Admin instance) {
		log.debug("attaching dirty Admin instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zjianhao.daoImpl.AdminDao#attachClean(com.zjianhao.model.Admin)
	 */
	@Override
	public void attachClean(Admin instance) {
		log.debug("attaching clean Admin instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.zjianhao.daoImpl.AdminDao#login(java.lang.String)
	 */
	@Transactional
	@Override
	public List<Admin> login(String username){
		Session session = getCurrentSession();
		String sql = "from Admin admin where admin.adminName=? or email=?";
		Query query = session.createQuery(sql);
		query.setString(0, username);
		query.setString(1, username);
		List list = query.list();
		System.out.println("query size:"+list.size());
		return list;
	}

	public static AdminDao getFromApplicationContext(ApplicationContext ctx) {
		return (AdminDao) ctx.getBean("adminDao");
	}
}
