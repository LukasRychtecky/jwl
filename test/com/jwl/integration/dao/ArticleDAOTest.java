package com.jwl.integration.dao;

import com.jwl.business.Environment;
import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.integration.dao.interfaces.IArticleDAO;
import com.jwl.integration.exceptions.DAOException;
import com.jwl.integration.exceptions.DuplicateEntryException;
import java.util.Date;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Lukas Rychtecky
 */
public class ArticleDAOTest extends TestCase {

	private IArticleDAO dao;
	private ArticleTO articleCrate;

    public ArticleDAOTest() {
    }

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

    @Before
	@Override
    public void setUp() {
		Environment.setPersistenceUnit("jwltest");

		this.dao = new ArticleDAO();

		this.articleCrate = new ArticleTO();
		this.articleCrate.setChangeNote("changed");
		this.articleCrate.setCreated(new Date());
		this.articleCrate.setEditCount(0);
		this.articleCrate.setEditor("nobody");
		this.articleCrate.setLocked(false);
		this.articleCrate.setText("text");
		this.articleCrate.setTitle("title");
    }

    @After
	@Override
    public void tearDown() {
    }

	/**
	 * Test of create method, of class ArticleDAO.
	 */
	@Test (expected=DuplicateEntryException.class)
	public void testCreateDuplicateTitle() {
		try {
			this.dao.create(this.articleCrate);
			this.dao.create(this.articleCrate);
		} catch (DuplicateEntryException e) {

		} catch (DAOException e) {
			Assert.fail("Unexpected exception: " + e.getMessage());
		}
	}

	/**
	 * Test of create method, of class ArticleDAO.
	 */
	@Test
	public void testCreate() {
		try {
			ArticleId idFirst = this.dao.create(this.articleCrate);

			this.articleCrate.setTitle("another title");
			ArticleId idSecond = this.dao.create(this.articleCrate);
			if (idSecond.getId() != (idFirst.getId() + 1)) {
				Assert.fail("Ids must be in series.");
			}
		} catch (DuplicateEntryException e) {
			Assert.fail("Unexpected exception: " + e.getMessage());
		} catch (DAOException e) {
			Assert.fail("Unexpected exception: " + e.getMessage());
		}
	}

	/**
	 * Test of get method, of class ArticleDAO.
	 */
	@Test
	public void testGet() {
	}

	/**
	 * Test of update method, of class ArticleDAO.
	 */
	@Test
	public void testUpdate() {
	}

	/**
	 * Test of delete method, of class ArticleDAO.
	 */
	@Test
	public void testDelete() {
	}

	/**
	 * Test of findAll method, of class ArticleDAO.
	 */
	@Test
	public void testFindAll() {
	}

	/**
	 * Test of findEverywhere method, of class ArticleDAO.
	 */
	@Test
	public void testFindEverywhere() {
	}

	/**
	 * Test of getByTitle method, of class ArticleDAO.
	 */
	@Test
	public void testGetByTitle() {
	}

}