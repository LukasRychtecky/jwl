package com.jwl.business;

// <editor-fold defaultstate="collapsed">
import java.io.File;
import java.util.List;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.AttachmentTO;
import com.jwl.business.article.HistoryId;
import com.jwl.business.article.HistoryTO;
import com.jwl.business.article.PostTO;
import com.jwl.business.article.SearchTO;
import com.jwl.business.article.TopicTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.knowledge.util.ArticleIdPair;
import com.jwl.business.security.IIdentity;
import com.jwl.business.usecases.AddForumPostUC;
import com.jwl.business.usecases.AddToMergeSuggestionsIgnoreUC;
import com.jwl.business.usecases.CloseForumTopicsUC;
import com.jwl.business.usecases.CreateArticleUC;
import com.jwl.business.usecases.CreateForumTopicUC;
import com.jwl.business.usecases.DeleteArticleUC;
import com.jwl.business.usecases.DeleteForumPostUC;
import com.jwl.business.usecases.DeleteForumTopicsUC;
import com.jwl.business.usecases.FindArticleByTitleUC;
import com.jwl.business.usecases.FindArticlesUC;
import com.jwl.business.usecases.GetArticleTopicsUC;
import com.jwl.business.usecases.GetArticleUC;
import com.jwl.business.usecases.GetDeadArticlesUC;
import com.jwl.business.usecases.GetFileUC;
import com.jwl.business.usecases.GetHistoriesUC;
import com.jwl.business.usecases.GetHistoryUC;
import com.jwl.business.usecases.GetMergeSuggestionsUC;
import com.jwl.business.usecases.GetSimilarArticlesInViewUC;
import com.jwl.business.usecases.GetTopicUC;
import com.jwl.business.usecases.ImportACLUC;
import com.jwl.business.usecases.IncreaseLivabilityUC;
import com.jwl.business.usecases.LockArticleUC;
import com.jwl.business.usecases.OpenForumTopicsUC;
import com.jwl.business.usecases.RateArticleUC;
import com.jwl.business.usecases.RestoreArticleUC;
import com.jwl.business.usecases.UnlockArticleUC;
import com.jwl.business.usecases.UpdateArticleUC;
import com.jwl.business.usecases.UploadAttachmentUC;
import com.jwl.business.usecases.interfaces.IAddForumPostUC;
import com.jwl.business.usecases.interfaces.IAddToMergeSuggestionsIgnoreUC;
import com.jwl.business.usecases.interfaces.ICloseForumTopicsUC;
import com.jwl.business.usecases.interfaces.ICreateArticleUC;
import com.jwl.business.usecases.interfaces.ICreateForumTopicUC;
import com.jwl.business.usecases.interfaces.IDeleteArticleUC;
import com.jwl.business.usecases.interfaces.IDeleteForumPostUC;
import com.jwl.business.usecases.interfaces.IDeleteForumTopicsUC;
import com.jwl.business.usecases.interfaces.IFindArticleByTitleUC;
import com.jwl.business.usecases.interfaces.IFindArticlesUC;
import com.jwl.business.usecases.interfaces.IGetArticleTopicsUC;
import com.jwl.business.usecases.interfaces.IGetArticleUC;
import com.jwl.business.usecases.interfaces.IGetDeadArticlesUC;
import com.jwl.business.usecases.interfaces.IGetFileUC;
import com.jwl.business.usecases.interfaces.IGetHistoriesUC;
import com.jwl.business.usecases.interfaces.IGetHistoryUC;
import com.jwl.business.usecases.interfaces.IGetMergeSuggestionsUC;
import com.jwl.business.usecases.interfaces.IGetSimilarArticlesInViewUC;
import com.jwl.business.usecases.interfaces.IGetTopicUC;
import com.jwl.business.usecases.interfaces.IImportACLUC;
import com.jwl.business.usecases.interfaces.IIncreaseLivablityUC;
import com.jwl.business.usecases.interfaces.ILockArticleUC;
import com.jwl.business.usecases.interfaces.IOpenForumTopicsUC;
import com.jwl.business.usecases.interfaces.IRateArticleUC;
import com.jwl.business.usecases.interfaces.IRestoreArticleUC;
import com.jwl.business.usecases.interfaces.IUnlockArticleUC;
import com.jwl.business.usecases.interfaces.IUpdateArticleUC;
import com.jwl.business.usecases.interfaces.IUploadAttachmentUC;

/**
 * This interface provides communication between Model(business tier,
 * integration tier) and Controller, View. The class is designed as Facade.
 */
public class Facade implements IFacade {

	private IPaginator<ArticleTO> paginator = null;
	private KeyWordPaginator searchPaginator = null;
	private TopicPaginator topicPaginator = null;
	@Override
	public void setJWLHome(String home) {
		Environment.setJWLHome(home);
	}

	@Override
	public String getJWLHome() {
		return Environment.getJWLHome();
	}

	@Override
	public List<ArticleTO> findArticles(SearchTO searchTO)
			throws ModelException {
		IFindArticlesUC uc = new FindArticlesUC(Environment.getDAOFactory());
		return uc.find(searchTO);
	}

	@Override
	public ArticleTO findArticleByTitle(String title) throws ModelException {
		IFindArticleByTitleUC uc = new FindArticleByTitleUC(
				Environment.getDAOFactory());
		return uc.find(title);
	}

	@Override
	public void updateArticle(ArticleTO article) throws ModelException {
		IUpdateArticleUC uc = new UpdateArticleUC(Environment.getDAOFactory());
		uc.update(article);
	}

	@Override
	public ArticleId createArticle(ArticleTO article) throws ModelException {
		ICreateArticleUC uc = new CreateArticleUC(Environment.getDAOFactory());
		return uc.create(article);
	}

	@Override
	public IIdentity getIdentity() {
		return Environment.getIdentity();
	}

	@Override
	public void importACL(String fileName) throws ModelException {
		IImportACLUC uc = new ImportACLUC(Environment.getDAOFactory());
		uc.importACL(fileName);
	}

	@Override
	public void uploadAttachment(AttachmentTO attachment, String source) throws ModelException {
		IUploadAttachmentUC uc = new UploadAttachmentUC(Environment.getDAOFactory());
		uc.upload(attachment, source, Environment.getAttachmentStorage());


	}

	@Override
	public File getFile(String name) throws ModelException {
		IGetFileUC uc = new GetFileUC(Environment.getDAOFactory());
		return uc.get(name);
	}

	@Override
	public void deleteArticle(ArticleId id) throws ModelException {
		IDeleteArticleUC uc = new DeleteArticleUC(Environment.getDAOFactory());
		uc.delete(id);
	}

	@Override
	public void lockArticle(ArticleId id) throws ModelException {
		ILockArticleUC uc = new LockArticleUC(Environment.getDAOFactory());
		uc.lock(id);
	}

	@Override
	public void unlockArticle(ArticleId id) throws ModelException {
		IUnlockArticleUC uc = new UnlockArticleUC(Environment.getDAOFactory());
		uc.unlock(id);
	}

	@Override
	public ArticleTO getArticle(ArticleId id) throws ModelException {
		IGetArticleUC uc = new GetArticleUC(Environment.getDAOFactory());
		return uc.get(id);
	}

	@Override
	public IPaginator<ArticleTO> getPaginator() {
		if (this.paginator == null) {
			this.paginator = new Paginator(3);
		}
		paginator.setUpPaginator();
		return paginator;
	}

	@Override
	public IPaginator<ArticleTO> getSearchPaginator() {
		if (searchPaginator != null) {
			searchPaginator.setUpPaginator();
		}
		return searchPaginator;
	}

	@Override
	public void setSearchParametres(SearchTO searchTO) {
		if (this.searchPaginator == null) {
			this.searchPaginator = new KeyWordPaginator(Environment.getKnowledgeFacade());
		}
		searchPaginator.setSearch(searchTO);
	}

	@Override
	public List<HistoryTO> getHistories(ArticleId id) throws ModelException {
		IGetHistoriesUC uc = new GetHistoriesUC(Environment.getDAOFactory());
		return uc.get(id);
	}

	@Override
	public HistoryTO getHistory(HistoryId id) throws ModelException {
		IGetHistoryUC uc = new GetHistoryUC(Environment.getDAOFactory());
		return uc.get(id);
	}

	@Override
	public void restoreArticle(HistoryId id) throws ModelException {
		IRestoreArticleUC uc = new RestoreArticleUC(Environment.getDAOFactory());
		uc.restore(id);
	}

	@Override
	public void rateArticle(ArticleId id, float rating) throws ModelException {
		IRateArticleUC uc = new RateArticleUC(Environment.getDAOFactory());
		uc.rateArticle(id, rating);
	}

	@Override
	public List<ArticlePair> getMergeSuggestions() throws ModelException {
		IGetMergeSuggestionsUC uc = new GetMergeSuggestionsUC(
				Environment.getDAOFactory());
		return uc.getMergeSuggestions();
	}

	@Override
	public List<ArticleTO> getDeadArticles() throws ModelException {
		IGetDeadArticlesUC uc = new GetDeadArticlesUC(Environment.getDAOFactory());
		return uc.getDeadArticles();
	}

	@Override
	public void addToMergeSuggestionsIgnored(List<ArticleIdPair> articleIdPairs)
			throws ModelException {
		IAddToMergeSuggestionsIgnoreUC uc = new AddToMergeSuggestionsIgnoreUC(Environment.getDAOFactory());
		uc.addToIgnored(articleIdPairs);		
	}

	@Override
	public void increaseLivability(List<ArticleId> ids, double increase)
			throws ModelException {
		IIncreaseLivablityUC uc = new IncreaseLivabilityUC(Environment.getDAOFactory());
		uc.addLivability(ids, increase);
		
	}

	@Override
	public List<ArticleTO> getSimilarArticlesInView(ArticleTO article)
			throws ModelException {
		IGetSimilarArticlesInViewUC uc = new GetSimilarArticlesInViewUC(Environment.getDAOFactory());
		return uc.getSimilarArticles(article);
	}

	@Override
	public TopicPaginator getArticleForumTopics(ArticleId articleId)
			throws ModelException {
		if(topicPaginator==null||!topicPaginator.getArticleId().equals(articleId)){
			topicPaginator = new TopicPaginator();
		}
		IGetArticleTopicsUC uc  = new GetArticleTopicsUC(Environment.getDAOFactory());	
		topicPaginator.setSearchResults(uc.getArticleTopics(articleId));
		topicPaginator.setArticleId(articleId);		
		return topicPaginator;
	}

	@Override
	public Integer createForumTopic(TopicTO topic, ArticleId article)
			throws ModelException {
		ICreateForumTopicUC uc = new CreateForumTopicUC(Environment.getDAOFactory());
		return uc.createTopic(topic, article);	
	}

	@Override
	public void deleteForumTopics(List<Integer> topicIds) throws ModelException {
		IDeleteForumTopicsUC uc = new DeleteForumTopicsUC(Environment.getDAOFactory());
		uc.deleteTopics(topicIds);
		
	}

	@Override
	public void closeForumTopics(List<Integer> topicIds) throws ModelException {
		ICloseForumTopicsUC uc = new CloseForumTopicsUC(Environment.getDAOFactory());
		uc.closeTopics(topicIds);
	}

	@Override
	public void openForumTopics(List<Integer> topicIds) throws ModelException {
		IOpenForumTopicsUC uc = new OpenForumTopicsUC(Environment.getDAOFactory());
		uc.openTopics(topicIds);
		
	}

	@Override
	public TopicTO getTopic(Integer topicId) throws ModelException {
		IGetTopicUC uc = new GetTopicUC(Environment.getDAOFactory());
		return uc.getTopic(topicId);
	}

	@Override
	public void deleteForumPost(Integer postId) throws ModelException {
		IDeleteForumPostUC uc = new DeleteForumPostUC(Environment.getDAOFactory());
		uc.deletePost(postId);
	}

	@Override
	public void addForumPost(PostTO post, Integer topicId)
			throws ModelException {
		IAddForumPostUC uc = new AddForumPostUC(Environment.getDAOFactory());
		uc.add(post, topicId);
	}
 
}
