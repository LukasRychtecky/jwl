package com.jwl.business;

import com.jwl.business.article.HistoryId;
import com.jwl.business.article.HistoryTO;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.permissions.Identity;
import com.jwl.business.permissions.IIdentity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.SearchTO;
import com.jwl.business.article.process.FileDownloadProcess;
import com.jwl.business.article.process.FileUploadProcess;
import com.jwl.business.exceptions.BusinessProcessException;
import com.jwl.business.usecases.CreateArticleUC;
import com.jwl.business.usecases.DeleteArticleUC;
import com.jwl.business.usecases.FindArticleByTitleUC;
import com.jwl.business.usecases.FindArticlesUC;
import com.jwl.business.usecases.GetArticleUC;
import com.jwl.business.usecases.GetHistoriesUC;
import com.jwl.business.usecases.GetHistoryUC;
import com.jwl.business.usecases.LockArticleUC;
import com.jwl.business.usecases.RestoreArticleUC;
import com.jwl.business.usecases.UnlockArticleUC;
import com.jwl.business.usecases.UpdateArticleUC;
import com.jwl.business.usecases.interfaces.ICreateArticleUC;
import com.jwl.business.usecases.interfaces.IDeleteArticleUC;
import com.jwl.business.usecases.interfaces.IFindArticleByTitleUC;
import com.jwl.business.usecases.interfaces.IFindArticlesUC;
import com.jwl.business.usecases.interfaces.IGetArticleUC;
import com.jwl.business.usecases.interfaces.IGetHistoriesUC;
import com.jwl.business.usecases.interfaces.IGetHistoryUC;
import com.jwl.business.usecases.interfaces.ILockArticleUC;
import com.jwl.business.usecases.interfaces.IRestoreArticleUC;
import com.jwl.business.usecases.interfaces.IUnlockArticleUC;
import com.jwl.business.usecases.interfaces.IUpdateArticleUC;
import com.jwl.integration.dao.ArticleDAO;
import com.jwl.integration.dao.HistoryDAO;
import com.jwl.integration.dao.RoleDAO;
import com.jwl.integration.dao.TagDAO;
import com.jwl.integration.dao.interfaces.IArticleDAO;
import com.jwl.integration.dao.interfaces.IHistoryDAO;
import com.jwl.integration.dao.interfaces.ITagDAO;
import com.jwl.integration.entity.Role;

/**
 * 
 * @review Petr Dytrych
 */
public class Facade implements IFacade {

	private IIdentity identity = null;
	private IPaginator paginator = null;
	private IArticleDAO articleDAO = null;
	private ITagDAO tagDAO = null;
	private IHistoryDAO historyDAO = null;
	private SearchPaginator searchPaginator = null;

	protected IArticleDAO getArticleDAO() {
		if (this.articleDAO == null) {
			this.articleDAO = new ArticleDAO();
		}
		return this.articleDAO;
	}

	protected ITagDAO getTagDAO() {
		if (this.tagDAO == null) {
			this.tagDAO = new TagDAO();
		}
		return this.tagDAO;
	}

	protected IHistoryDAO getHistoryDAO() {
		if (this.historyDAO == null) {
			this.historyDAO = new HistoryDAO();
		}
		return this.historyDAO;
	}
	
	@Override
	public List<ArticleTO> findArticles(SearchTO searchTO) throws ModelException {
		IFindArticlesUC uc = new FindArticlesUC(this.getArticleDAO());
		return uc.find(searchTO);
	}

	@Override
	public ArticleTO findArticleByTitle(String title) throws ModelException {
		IFindArticleByTitleUC uc = new FindArticleByTitleUC(this.getArticleDAO());
		return uc.find(title);
	}

	@Override
	public void updateArticle(ArticleTO article) throws ModelException {
		IUpdateArticleUC uc = new UpdateArticleUC(
					this.getArticleDAO(),
					this.getHistoryDAO(),
					this.getTagDAO());
		uc.update(article);
	}

	@Override
	public void createArticle(ArticleTO article) throws ModelException {
		ICreateArticleUC uc = new CreateArticleUC(this.getArticleDAO(), this.getTagDAO());
		uc.create(article);
	}

	@Override
	public IIdentity getIdentity() {
		if (this.identity == null) {
			this.identity = new Identity();
			this.identity.setPermissionsSources(Role.class, new RoleDAO());
		}
		return this.identity;
	}

	@Override
	public void uploadFile(HttpServletRequest request) {
		FileUploadProcess process = new FileUploadProcess(request);
		try {
			process.doIt();
		} catch (BusinessProcessException e) {
			Logger.getLogger(Identity.class.getName()).log(Level.SEVERE, null,
					e);
		}
	}

	@Override
	public void makeDownloadFileResponse(HttpServletRequest request,
			HttpServletResponse response) {
		FileDownloadProcess process = new FileDownloadProcess(request, response);
		try {
			process.doIt();
		} catch (BusinessProcessException e) {
			Logger.getLogger(Identity.class.getName()).log(Level.SEVERE, null, e);
		}
		
	}

	@Override
	public void deleteArticle(ArticleId id) throws ModelException {
		IDeleteArticleUC uc = new DeleteArticleUC(this.getArticleDAO(), this.getHistoryDAO());
		uc.delete(id);
	}

	@Override
	public void lockArticle(ArticleId id) throws ModelException {
		ILockArticleUC uc = new LockArticleUC(this.getArticleDAO());
		uc.lock(id);
	}

	@Override
	public void unlockArticle(ArticleId id) throws ModelException {
		IUnlockArticleUC uc = new UnlockArticleUC(this.getArticleDAO());
		uc.unlock(id);
	}

	@Override
	public ArticleTO getArticle(ArticleId id) throws ModelException {
		IGetArticleUC uc = new GetArticleUC(this.getArticleDAO());
		return uc.get(id);
	}

	@Override
	public IPaginator getPaginator() {
		if(this.paginator == null){
			this.paginator = new Paginator(3);
		}
		paginator.setUpPaginator();
		return paginator;
	}
	
	@Override
	public IPaginator getSearchPaginator() {
		if (searchPaginator != null) {
			searchPaginator.setUpPaginator();
		}
		return searchPaginator;
	}

	@Override
	public void setSearchParametres(SearchTO searchTO) {
		if (this.searchPaginator == null) {
			this.searchPaginator = new SearchPaginator(3);
		}
		searchPaginator.setSearchCategories(searchTO);
	}

	@Override
	public List<HistoryTO> getHistories(ArticleId id) throws ModelException {
		IGetHistoriesUC uc = new GetHistoriesUC(this.getHistoryDAO());
		return uc.get(id);
	}

	@Override
	public HistoryTO getHistory(HistoryId id) throws ModelException {
		IGetHistoryUC uc = new GetHistoryUC(this.getHistoryDAO());
		return uc.get(id);
	}

	@Override
	public void restoreArticle(HistoryId id) throws ModelException {
		IRestoreArticleUC uc = new RestoreArticleUC(this.getArticleDAO(), this.getHistoryDAO());
		uc.restore(id);
	}

}
