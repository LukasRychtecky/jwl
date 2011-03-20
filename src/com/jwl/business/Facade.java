package com.jwl.business;
// <editor-fold defaultstate="collapsed">
import com.jwl.business.exceptions.InsufficientArticleDataException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwl.business.article.ArticleId;
import com.jwl.business.article.ArticleTO;
import com.jwl.business.article.AttachmentTO;
import com.jwl.business.article.HistoryId;
import com.jwl.business.article.HistoryTO;
import com.jwl.business.article.SearchTO;
import com.jwl.business.article.process.FileDownloadProcess;
import com.jwl.business.article.process.FileUploadProcess;
import com.jwl.business.article.usecases.CreateAttachmentUC;
import com.jwl.business.article.usecases.interfaces.ICreateAttachmentUC;
import com.jwl.business.exceptions.BusinessProcessException;
import com.jwl.business.exceptions.ModelException;
import com.jwl.business.permissions.IIdentity;
import com.jwl.business.usecases.CreateArticleUC;
import com.jwl.business.usecases.DeleteArticleUC;
import com.jwl.business.usecases.FindArticleByTitleUC;
import com.jwl.business.usecases.FindArticlesUC;
import com.jwl.business.usecases.GetArticleUC;
import com.jwl.business.usecases.GetHistoriesUC;
import com.jwl.business.usecases.GetHistoryUC;
import com.jwl.business.usecases.ImportACLUC;
import com.jwl.business.usecases.LockArticleUC;
import com.jwl.business.usecases.RateArticleUC;
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
import com.jwl.business.usecases.interfaces.IImportACLUC;
import com.jwl.business.usecases.interfaces.ILockArticleUC;
import com.jwl.business.usecases.interfaces.IRateArticleUC;
import com.jwl.business.usecases.interfaces.IRestoreArticleUC;
import com.jwl.business.usecases.interfaces.IUnlockArticleUC;
import com.jwl.business.usecases.interfaces.IUpdateArticleUC;
// </editor-fold>

/**
 * This interface provides communication between Model(business tier,
 * integration tier) and Controller, View. The class is designed as Facade.
 */
public class Facade implements IFacade {

	private IPaginator paginator = null;
	private SearchPaginator searchPaginator = null;

	@Override
	public void setJWLHome(String home) {
		Environment.setJWLHome(home);
	}

	@Override
	public String getJWLHome() {
		return Environment.getJWLHome();
	}

	
	@Override
	public List<ArticleTO> findArticles(SearchTO searchTO) throws ModelException {
		IFindArticlesUC uc = new FindArticlesUC(Environment.getDAOFactory());
		return uc.find(searchTO);
	}

	@Override
	public ArticleTO findArticleByTitle(String title) throws ModelException {
		IFindArticleByTitleUC uc = new FindArticleByTitleUC(Environment.getDAOFactory());
		return uc.find(title);
	}

	@Override
	public void updateArticle(ArticleTO article) throws ModelException {
		IUpdateArticleUC uc = new UpdateArticleUC(Environment.getDAOFactory());
		uc.update(article);
	}

	@Override
	public void createArticle(ArticleTO article) throws ModelException {
		ICreateArticleUC uc = new CreateArticleUC(Environment.getDAOFactory());
		uc.create(article);
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
	public void uploadFile(HttpServletRequest request) {
		FileUploadProcess process = new FileUploadProcess(request);
		try {
			process.doIt();
		} catch (BusinessProcessException e) {
			Logger.getLogger(FileUploadProcess.class.getName()).log(Level.SEVERE, null,
					e);
		}
	}

	@Override
	public void createAttachment(AttachmentTO attachment) throws InsufficientArticleDataException {
		ICreateAttachmentUC uc = new CreateAttachmentUC();
		uc.createAttachment(attachment);
	}

	@Override
	public void makeDownloadFileResponse(HttpServletRequest request,
			HttpServletResponse response) {
		FileDownloadProcess process = new FileDownloadProcess(request, response);
		try {
			process.doIt();
		} catch (BusinessProcessException e) {
			Logger.getLogger(FileDownloadProcess.class.getName()).log(Level.SEVERE, null, e);
		}
		
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

}
