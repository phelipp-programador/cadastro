package br.com.pscs.repository.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import br.com.pscs.domain.model.ModelImpl;
import br.com.pscs.repository.util.HibernateUtil;

public interface Repository<T extends ModelImpl> {
	public static final HibernateUtil HIBERNATE_UTIL = new HibernateUtil();

	default Optional<T> save(T entity) {
		return save(entity, HIBERNATE_UTIL.getSessionFactory());
	}

	default Optional<T> save(T entity, SessionFactory sessionFactory) {
		// isCloseSession(sessionFactory);

		try (Session session = sessionFactory.openSession();) {
			Transaction transaction = session.getTransaction();
			transaction.begin();
			entity = (T) session.merge(entity);
			transaction.commit();
			return Optional.of(entity);
		} catch (Exception e) {
			return Optional.empty();
		}

	}

	default Optional<T> findById(Integer id, Class<? extends ModelImpl> classe) {
		return findById(HIBERNATE_UTIL.getSessionFactory(), id, classe);
	}

	default Optional<T> findById(SessionFactory sessionFactory, Integer id, Class<? extends ModelImpl> classe) {

		try (Session session = sessionFactory.openSession();) {

			Transaction transaction = session.getTransaction();
			transaction.begin();
			T objetoEncontrado = (T) session.find(classe, id);
			transaction.commit();
			if (objetoEncontrado != null) {
				return Optional.of(objetoEncontrado);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return Optional.empty();
	}

	public default Optional<List> findAll(Class<T> classe) {
		return findAll(HIBERNATE_UTIL.getSessionFactory(), classe);
	}

	public default Optional<List> findAll(SessionFactory sessionFactory, Class<T> classe) {
		try (Session session = sessionFactory.openSession();) {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<T> cq = cb.createQuery(classe);
			Root<T> rootEntry = cq.from(classe);
			CriteriaQuery<T> all = cq.select(rootEntry);

			TypedQuery<T> allQuery = session.createQuery(all);
			List<T> resultList = allQuery.getResultList();

			if (resultList.size() > 0) {
				return Optional.of(resultList);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return Optional.empty();

	}

	public default Optional<T> update(T entity, Integer id) {
		return update(entity, id, HIBERNATE_UTIL.getSessionFactory());
	}

	public default Optional<List> findAllBy(String campo, String text, Class<? extends ModelImpl> classe){
		return findAllBy(campo, text, classe,HIBERNATE_UTIL.getSessionFactory());
	}

	public default Optional<List> findAllBy(String campo, String text, Class<? extends ModelImpl> classe,
			SessionFactory sessionFactory) {
		try (Session session = sessionFactory.openSession();) {
			String classNome = classe.getSimpleName();
			
			Query query = session.createQuery("from " + classNome+ " where " + campo + " =:nome ");
			query.setParameter("nome", text);
			List<T> resultList = query.list();

			if (resultList.size() > 0) {
				return Optional.of(resultList);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return Optional.empty();
	}

	public default Optional<T> update(T oldEntity, T newEntity) {
		return update(newEntity, oldEntity.getId(), HIBERNATE_UTIL.getSessionFactory());
	}

	public default Optional<T> update(T entity, Integer id, SessionFactory sessionFactory) {
		// TODO Auto-generated method stub
		Optional<T> objetoEncontrado = findById(id, entity.getClass());
		objetoEncontrado.map(objeto -> {
			try (Session session = sessionFactory.openSession();) {
				Transaction transaction = session.getTransaction();
				transaction.begin();
				entity.setId(objeto.getId());
				session.merge(entity);
				transaction.commit();
				return Optional.of(entity);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return Optional.empty();
		});

		return objetoEncontrado;

	}

	public default Optional<T> delete(T entity) {
		return delete(entity.getClass(), entity.getId(), HIBERNATE_UTIL.getSessionFactory());
	}

	public default Optional<T> delete(Class<? extends ModelImpl> classe, Integer id) {

		return delete(classe, id, HIBERNATE_UTIL.getSessionFactory());
	}

	public default Optional<T> delete(Class<? extends ModelImpl> classe, Integer id, SessionFactory sessionFactory) {
		Optional<T> objetoEncontrado = findById(id, classe);
		objetoEncontrado.map(objeto -> {
			try (Session session = sessionFactory.openSession();) {
				Transaction transaction = session.getTransaction();
				transaction.begin();
				// entity.setId(objeto.getId());
				session.delete(objeto);
				transaction.commit();
				return Optional.of(objeto);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return Optional.empty();
		});

		return objetoEncontrado;

	}
}
