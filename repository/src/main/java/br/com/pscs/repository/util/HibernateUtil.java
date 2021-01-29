package br.com.pscs.repository.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	private static SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
				MetadataSources source = new MetadataSources(registry);
				Metadata metadata = source.getMetadataBuilder().build();

				sessionFactory = metadata.getSessionFactoryBuilder().build();

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			if (sessionFactory.isClosed()) {
				sessionFactory = null;
				sessionFactory = getSessionFactory();
			}
			
		}
		return sessionFactory;
	}

}
