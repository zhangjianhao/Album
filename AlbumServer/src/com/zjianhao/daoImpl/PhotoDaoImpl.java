package com.zjianhao.daoImpl;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.sql.Insert;

import static org.hibernate.criterion.Example.create;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.zjianhao.dao.PhotoDao;
import com.zjianhao.model.Photo;

/**
 * A data access object (DAO) providing persistence and search support for Photo
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.zjianhao.model.Photo
 * @author MyEclipse Persistence Tools
 */
@Transactional
public class PhotoDaoImpl implements PhotoDao {
	private static final Logger log = LoggerFactory.getLogger(PhotoDaoImpl.class);
	// property constants
	public static final String PHOTO_NAME = "photoName";
	public static final String PHOTO_URL = "photoUrl";
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";

	private SessionFactory sessionFactory;

	/* (non-Javadoc)
	 * @see com.zjianhao.daoImpl.PhotoDao#setSessionFactory(org.hibernate.SessionFactory)
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
	 * @see com.zjianhao.daoImpl.PhotoDao#save(com.zjianhao.model.Photo)
	 */
	@Override
	@Transactional
	public void save(Photo transientInstance) {
		log.debug("saving Photo instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	@Transactional
	public List<Photo> getPhotoByAlbumId(int albumId){
		Session session = getCurrentSession();
		String sql = "from Photo photo where photo.album.id = ?";
		Query query = session.createQuery(sql);
		query.setInteger(0, albumId);
		List list = query.list();
		return list;
	}
	
	
	

	/* (non-Javadoc)
	 * @see com.zjianhao.daoImpl.PhotoDao#delete(com.zjianhao.model.Photo)
	 */
	@Transactional
	@Override
	public void delete(Photo persistentInstance) {
		log.debug("deleting Photo instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zjianhao.daoImpl.PhotoDao#findById(java.lang.Integer)
	 */
	@Override
	public Photo findById(java.lang.Integer id) {
		log.debug("getting Photo instance with id: " + id);
		try {
			Photo instance = (Photo) getCurrentSession().get(
					"com.zjianhao.daoImpl.Photo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zjianhao.daoImpl.PhotoDao#findByExample(com.zjianhao.model.Photo)
	 */
	@Override
	public List<Photo> findByExample(Photo instance) {
		log.debug("finding Photo instance by example");
		try {
			List<Photo> results = (List<Photo>) getCurrentSession()
					.createCriteria("com.zjianhao.daoImpl.Photo")
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
	 * @see com.zjianhao.daoImpl.PhotoDao#findByProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Photo instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Photo as model where model."
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
	 * @see com.zjianhao.daoImpl.PhotoDao#findByPhotoName(java.lang.Object)
	 */
	@Override
	public List<Photo> findByPhotoName(Object photoName) {
		return findByProperty(PHOTO_NAME, photoName);
	}

	/* (non-Javadoc)
	 * @see com.zjianhao.daoImpl.PhotoDao#findByPhotoUrl(java.lang.Object)
	 */
	@Override
	public List<Photo> findByPhotoUrl(Object photoUrl) {
		return findByProperty(PHOTO_URL, photoUrl);
	}

	/* (non-Javadoc)
	 * @see com.zjianhao.daoImpl.PhotoDao#findByLatitude(java.lang.Object)
	 */
	@Override
	public List<Photo> findByLatitude(Object latitude) {
		return findByProperty(LATITUDE, latitude);
	}

	/* (non-Javadoc)
	 * @see com.zjianhao.daoImpl.PhotoDao#findByLongitude(java.lang.Object)
	 */
	@Override
	public List<Photo> findByLongitude(Object longitude) {
		return findByProperty(LONGITUDE, longitude);
	}

	/* (non-Javadoc)
	 * @see com.zjianhao.daoImpl.PhotoDao#findAll()
	 */
	@Override
	public List findAll() {
		log.debug("finding all Photo instances");
		try {
			String queryString = "from Photo";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zjianhao.daoImpl.PhotoDao#merge(com.zjianhao.model.Photo)
	 */
	@Override
	public Photo merge(Photo detachedInstance) {
		log.debug("merging Photo instance");
		try {
			Photo result = (Photo) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zjianhao.daoImpl.PhotoDao#attachDirty(com.zjianhao.model.Photo)
	 */
	@Override
	public void attachDirty(Photo instance) {
		log.debug("attaching dirty Photo instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.zjianhao.daoImpl.PhotoDao#attachClean(com.zjianhao.model.Photo)
	 */
	@Override
	public void attachClean(Photo instance) {
		log.debug("attaching clean Photo instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static PhotoDao getFromApplicationContext(ApplicationContext ctx) {
		return (PhotoDao) ctx.getBean("photoDao");
	}
}