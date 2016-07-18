package com.zjianhao.daoImpl;

import java.util.List;
import java.util.Set;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import static org.hibernate.criterion.Example.create;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.zjianhao.dao.AlbumDao;
import com.zjianhao.model.Album;

/**
 * A data access object (DAO) providing persistence and search support for Album
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.zjianhao.model.Album
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class AlbumDaoImpl implements AlbumDao {
	private static final Logger log = LoggerFactory.getLogger(AlbumDaoImpl.class);
	// property constants
	public static final String ALBUM_NAME = "albumName";

	private SessionFactory sessionFactory;

	/* (non-Javadoc)
	 * @see com.zjianhao.daoImpl.AlbumDao#setSessionFactory(org.hibernate.SessionFactory)
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
	 * @see com.zjianhao.daoImpl.AlbumDao#save(com.zjianhao.model.Album)
	 */
	@Override
	@Transactional
	public void save(Album transientInstance) {
		log.debug("saving Album instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zjianhao.daoImpl.AlbumDao#delete(com.zjianhao.model.Album)
	 */
	@Override
	public void delete(Album persistentInstance) {
		log.debug("deleting Album instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zjianhao.daoImpl.AlbumDao#findById(java.lang.Integer)
	 */
	@Override
	public Album findById(java.lang.Integer id) {
		log.debug("getting Album instance with id: " + id);
		try {
			Album instance = (Album) getCurrentSession().get(
					"com.zjianhao.daoImpl.Album", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zjianhao.daoImpl.AlbumDao#findByExample(com.zjianhao.model.Album)
	 */
	@Override
	public List<Album> findByExample(Album instance) {
		log.debug("finding Album instance by example");
		try {
			List<Album> results = (List<Album>) getCurrentSession()
					.createCriteria("com.zjianhao.daoImpl.Album")
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
	 * @see com.zjianhao.daoImpl.AlbumDao#findByProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Album instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Album as model where model."
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
	 * @see com.zjianhao.daoImpl.AlbumDao#findByAlbumName(java.lang.Object)
	 */
	@Override
	public List<Album> findByAlbumName(Object albumName) {
		return findByProperty(ALBUM_NAME, albumName);
	}

	/* (non-Javadoc)
	 * @see com.zjianhao.daoImpl.AlbumDao#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all Album instances");
		try {
			String queryString = "from Album";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zjianhao.daoImpl.AlbumDao#merge(com.zjianhao.model.Album)
	 */
	@Override
	public Album merge(Album detachedInstance) {
		log.debug("merging Album instance");
		try {
			Album result = (Album) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zjianhao.daoImpl.AlbumDao#attachDirty(com.zjianhao.model.Album)
	 */
	@Override
	public void attachDirty(Album instance) {
		log.debug("attaching dirty Album instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zjianhao.daoImpl.AlbumDao#attachClean(com.zjianhao.model.Album)
	 */
	@Override
	public void attachClean(Album instance) {
		log.debug("attaching clean Album instance");
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
	 * @see com.zjianhao.daoImpl.AlbumDao#getAlbumByUserId(int)
	 */
	@Override
	@Transactional
	public List<Album> getAlbumByUserId(int userId){
		Session session = getCurrentSession();
		String sql = "from Album album where album.user.id = ?";
		Query query = session.createQuery(sql);
		query.setInteger(0, userId);
		List list = query.list();
		return list;
	}
	
	
	@Transactional
	public List<Album> getAlbums(int pageIndex,int pageSize){
		Session session = getCurrentSession();
		String sql = "from Album album";
		Query query = session.createQuery(sql);
		int startIndex = (pageIndex -1) * pageSize;
		query.setFirstResult(startIndex);
		query.setMaxResults(pageSize);
		List list = query.list();
		return list;
	}
	
	

	public static AlbumDao getFromApplicationContext(ApplicationContext ctx) {
		return (AlbumDao) ctx.getBean("albumDao");
	}
	@Transactional
	@Override
	public void update(Album album) {
		Session session = getCurrentSession();
		session.update(album);
	}
}