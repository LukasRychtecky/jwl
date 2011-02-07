package com.jwl.business.article;

import junit.framework.Assert;
import java.util.Date;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Lukas Rychtecky
 */
public class ArticleTOTest {

	private ArticleTO article;
	private ArticleTO articleImplicit;
	private Date now;

    @Before
    public void setUp() {
		this.now = new Date();

		this.article = new ArticleTO();
		this.article.setChangeNote("changeNote");
		this.article.setCreated(this.now);
		this.article.setEditCount(0);
		this.article.setEditor("editor");
		this.article.setId(new ArticleId(0));
		this.article.setLocked(Boolean.TRUE);
		this.article.setText("text");
		this.article.setTitle("title");

		this.article.addTag("tag1");
		this.article.addTag("tag2");
		this.article.addTag("tag3");

		this.articleImplicit = new ArticleTO();
    }

    @After
    public void tearDown() {
    }

	/**
	 * Test of getId method, of class ArticleTO.
	 */
	@Test
	public void testGetId() {
		Assert.assertEquals(new ArticleId(0), this.article.getId());
		Assert.assertNull(this.articleImplicit.getId());
	}

	/**
	 * Test of setId method, of class ArticleTO.
	 */
	@Test
	public void testSetId() {
		this.article.setId(new ArticleId(1));
		Assert.assertEquals(new ArticleId(1), this.article.getId());
	}

	/**
	 * Test of getTitle method, of class ArticleTO.
	 */
	@Test
	public void testGetTitle() {
		Assert.assertEquals("title", this.article.getTitle());
		Assert.assertEquals("", this.articleImplicit.getTitle());
	}

	/**
	 * Test of getTitle method, of class ArticleTO.
	 */
	@Test (expected=IllegalArgumentException.class)
	public void testGetTitleTooLong() {
		StringBuilder title = new StringBuilder();
		for (int i = 0; i <= ArticleTO.TITLE_MAX_SIZE; i++) {
			title.append("#");
		}
		this.article.setTitle(title.toString());
	}

	/**
	 * Test of setTitle method, of class ArticleTO.
	 */
	@Test
	public void testSetTitle() {
		this.article.setTitle("title1");
		Assert.assertEquals("title1", this.article.getTitle());
	}

	@Test
	public void testSetTitleNull() {
		this.articleImplicit.setTitle(null);
		Assert.assertEquals("", this.articleImplicit.getTitle());
	}

	/**
	 * Test of getText method, of class ArticleTO.
	 */
	@Test
	public void testGetText() {
		Assert.assertEquals("text", this.article.getText());
		Assert.assertEquals("", this.articleImplicit.getText());
	}

	/**
	 * Test of setText method, of class ArticleTO.
	 */
	@Test
	public void testSetText() {
		this.article.setText("text1");
		Assert.assertEquals("text1", this.article.getText());
	}

	@Test
	public void testSetTextNull() {
		this.articleImplicit.setText(null);
		Assert.assertEquals("", this.articleImplicit.getText());
	}

	/**
	 * Test of getCreated method, of class ArticleTO.
	 */
	@Test
	public void testGetCreated() {
		Assert.assertEquals(this.now, this.article.getCreated());
		Assert.assertEquals(this.now, this.articleImplicit.getCreated());
	}

	@Test
	public void testSetCreatedNull() {
		this.articleImplicit.setCreated(null);
		Assert.assertEquals(new Date(), this.articleImplicit.getCreated());
	}

	/**
	 * Test of getEditor method, of class ArticleTO.
	 */
	@Test
	public void testGetEditor() {
		Assert.assertEquals("editor", this.article.getEditor());
		Assert.assertEquals("", this.articleImplicit.getEditor());
	}

	/**
	 * Test of setEditor method, of class ArticleTO.
	 */
	@Test
	public void testSetEditor() {
		this.article.setEditor("editor1");
		Assert.assertEquals("editor1", this.article.getEditor());
	}

	/**
	 * Test of getTitle method, of class ArticleTO.
	 */
	@Test (expected=IllegalArgumentException.class)
	public void testGetEditorTooLong() {
		StringBuilder editor = new StringBuilder();
		for (int i = 0; i <= ArticleTO.EDITOR_MAX_SIZE; i++) {
			editor.append("#");
		}
		this.article.setEditor(editor.toString());
	}

	@Test
	public void testSetEditorNull() {
		this.articleImplicit.setEditor(null);
		Assert.assertEquals("", this.articleImplicit.getEditor());
	}

	/**
	 * Test of getEditCount method, of class ArticleTO.
	 */
	@Test
	public void testGetEditCount() {
		Assert.assertEquals(new Integer(0), this.article.getEditCount());
		Assert.assertEquals(new Integer(0), this.articleImplicit.getEditCount());
	}

	/**
	 * Test of setEditCount method, of class ArticleTO.
	 */
	@Test
	public void testSetEditCount() {
		this.article.setEditCount(new Integer(1));
		Assert.assertEquals(new Integer(1), this.article.getEditCount());
	}

	@Test
	public void testSetEditCountNull() {
		this.articleImplicit.setEditCount(null);
		Assert.assertEquals(new Integer(0), this.articleImplicit.getEditCount());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testSetEditCountInvalidValue() {
		this.article.setEditCount(Integer.MIN_VALUE);
	}

	/**
	 * Test of isLocked method, of class ArticleTO.
	 */
	@Test
	public void testIsLocked() {
		Assert.assertEquals(Boolean.TRUE, this.article.isLocked());
	}

	/**
	 * Test of setLocked method, of class ArticleTO.
	 */
	@Test
	public void testSetLocked() {
		this.article.setLocked(Boolean.FALSE);
		Assert.assertEquals(Boolean.FALSE, this.article.isLocked());
	}

	@Test
	public void testSetLockedNull() {
		this.articleImplicit.setLocked(null);
		Assert.assertEquals(Boolean.FALSE, this.articleImplicit.isLocked());
	}


	/**
	 * Test of getModified method, of class ArticleTO.
	 */
	@Test
	public void testGetModified() {
		Assert.assertNull(this.articleImplicit.getModified());
	}

	/**
	 * Test of getChangeNote method, of class ArticleTO.
	 */
	@Test
	public void testGetChangeNote() {
		Assert.assertEquals("changeNote", this.article.getChangeNote());
		Assert.assertEquals("", this.articleImplicit.getChangeNote());
	}

	/**
	 * Test of setChangeNote method, of class ArticleTO.
	 */
	@Test
	public void testSetChangeNote() {
		this.article.setChangeNote("changeNote1");
		Assert.assertEquals("changeNote1", this.article.getChangeNote());
	}

	@Test
	public void testSetChangeNoteNull() {
		this.articleImplicit.setChangeNote(null);
		Assert.assertEquals("", this.articleImplicit.getChangeNote());
	}

	/**
	 * Test of getTitle method, of class ArticleTO.
	 */
	@Test (expected=IllegalArgumentException.class)
	public void testGetChangeNoteTooLong() {
		StringBuilder changeNote = new StringBuilder();
		for (int i = 0; i <= ArticleTO.CHANGE_NOTE_MAX_SIZE; i++) {
			changeNote.append("#");
		}
		this.article.setChangeNote(changeNote.toString());
	}

	/**
	 * Test of createHistory method, of class ArticleTO.
	 */
	@Test
	public void testCreateHistory() {
		HistoryTO history = this.article.createHistory();
		Assert.assertEquals(this.article.getChangeNote(), history.getChangeNote());
		Assert.assertEquals(this.article.getEditor(), history.getEditor());
		Assert.assertEquals(this.article.getModified(), history.getModified());
		Assert.assertEquals(this.article.getText(), history.getText());
		Assert.assertEquals(this.article.getTitle(), history.getTitle());
	}

	/**
	 * Test of addTag method, of class ArticleTO.
	 */
	@Test
	public void testAddTag() {
		Assert.assertEquals(Boolean.TRUE, this.article.addTag("tag"));
		Assert.assertEquals(Boolean.FALSE, this.article.addTag("tag"));
		Assert.assertTrue(this.article.getTags().contains("tag"));
	}

	@Test
	public void testAddTagNull() {
		Assert.assertEquals(Boolean.FALSE, this.article.addTag(null));
	}

	@Test
	public void testAddTagEmpty() {
		Assert.assertEquals(Boolean.FALSE, this.article.addTag(""));
	}

	/**
	 * Test of removeTag method, of class ArticleTO.
	 */
	@Test
	public void testRemoveTag() {
		this.article.addTag("tag");
		Assert.assertEquals(Boolean.TRUE, this.article.removeTag("tag"));
		Assert.assertEquals(Boolean.FALSE, this.article.removeTag("tag"));
		Assert.assertFalse(this.article.getTags().contains("tag"));
	}

	/**
	 * Test of removeTag method, of class ArticleTO.
	 */
	@Test
	public void testRemoveTagNull() {
		Assert.assertEquals(Boolean.FALSE, this.article.removeTag(null));
		Assert.assertFalse(this.article.getTags().contains(null));
	}

	/**
	 * Test of removeTag method, of class ArticleTO.
	 */
	@Test
	public void testRemoveTagEmpty() {
		Assert.assertEquals(Boolean.FALSE, this.article.removeTag(""));
		Assert.assertFalse(this.article.getTags().contains(""));
	}

	@Test
	public void testGetTags() {
		Set<String> tags1 = this.article.getTags();
		Set<String> tags2 = this.article.getTags();
		for (String tag : tags1) {
			Assert.assertTrue(tags2.contains(tag));
		}
	}

	@Test
	public void testGetTagsUnmodifiable() {
		Set<String> tags1 = this.article.getTags();
		Set<String> tags2 = this.article.getTags();
		Set<String> tags3 = this.article.getTags();
		tags1.clear();

		for (String tag : tags2) {
			Assert.assertTrue(tags3.contains(tag));
		}
	}

	@Test
	public void testRemoveAllTags() {
		this.article.removeAllTags();
		Assert.assertTrue(this.article.getTags().isEmpty());
	}

}