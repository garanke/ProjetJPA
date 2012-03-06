package monpkg.dao;

import static org.junit.Assert.*;

import java.util.Date;

import monpkg.entities.Person;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestDao {

  public static Dao dao;

   @BeforeClass
   public static void beforeAll() {
      dao = new Dao();
      dao.init();
   }

   @AfterClass
   public static void afterAll() {
      dao.close();
   }

   @Before
   public void setUp() {
      // pour plus tard
   }

   @After
   public void tearDown() {
      // pour plus tard
   }

   
   @Test
   public void testDao() {
	    assertNotNull(" dao est non null!",dao);
   }
   
   
   @Test
   public void testAjoutPerson(){
	   
	   Person p= new Person("barry" , new Date());
	   System.out.println(Dao.factory);
	  // assertNotNull(Dao.factory);
	   assertNotNull(dao.addPerson(p));
   }
  
   
}