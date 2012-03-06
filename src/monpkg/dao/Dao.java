package monpkg.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;


import monpkg.entities.Person;

public class Dao {

	public static EntityManagerFactory factory = null;

   public void init() {
	   try {
		   Dao.factory = Persistence.createEntityManagerFactory("myConfig");
	   }catch(Exception e){ e.printStackTrace();}
   }

   public void close() {
      if (Dao.factory != null) {
         Dao.factory.close();
      }
   }
   
   public Person addPerson(Person p) {
	   EntityManager em = null;
	   try {
	      em = Dao.factory.createEntityManager();
	      em.getTransaction().begin();
	      // utilisation de l'EntityManager
	      em.persist(p);
	      em.getTransaction().commit();
	      System.err.println("addPerson witdh id=" + p.getId());
	      return (p);
	   } finally {
	      if (em != null) {
	         em.close();
	      }
	   }
	}
   
   public <T> T find(Class<T> clazz, Object id) {
		EntityManager em = null;
		try {
			em = newEntityManager();
			return em.find(clazz, id);
		}
		finally
		{
			closeEntityManager(em);
		}
	}

   public Person findPerson(long id) {
	       return  find(Person.class ,id);
	   	}

	private <T> T update(T entity) {
		EntityManager em = null;
		try {
			em = newEntityManager();
			entity = em.merge(entity);
			em.getTransaction().commit();
		} finally {
			closeEntityManager(em);
		}
		return entity;
	}

   
	public void updateItem(Person p)
	{
		update(p);
	}	   	  
	
	public void removePerson(long id) {
	          remove(Person.class, id);
	}
	
	
	 private <T> void remove(Class<T> clazz, Object pk)
		{
			EntityManager em = null;
			try {
				em = newEntityManager();
				T entity = em.find(clazz, pk);
				if (entity != null)
					em.remove(entity);

				em.getTransaction().commit();
			}
			finally
			{
				closeEntityManager(em);
			}
		}
   
   
   @SuppressWarnings("unchecked")
   public List<Person> findAllPersons() {
      EntityManager em = null;
      try {
         em = factory.createEntityManager();
         em.getTransaction().begin();
         // utilisation de l'EntityManager
         TypedQuery<Person> q = em.createQuery("FROM Person", Person.class);
         return q.getResultList();
      } finally {
         if (em != null) {
            em.close();
         }
      }
   }

	private EntityManager newEntityManager() {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		return (em);
	}
	
	private void closeEntityManager(EntityManager em)
	{
		if (em != null)
		{
			if (em.isOpen())
			{
				EntityTransaction t = em.getTransaction();
				if (t.isActive())
				{
					try {t.rollback();}
					catch (PersistenceException e) {}
				}
				em.close();
			}
		}
	}

}