package com.jwl.integration.role;

import com.jwl.business.permissions.Role;
import com.jwl.business.permissions.RoleId;
import com.jwl.integration.convertor.ArticleConvertor;
import com.jwl.integration.entity.Article;

/**
 *
 * @author Lukas Rychtecky
 */
public class RoleConvertor {

	public static Role toObject(RoleEntity entity) {
		Role role = new Role(entity.getCode());
		if (entity.getId() != null) {
			role.setId(new RoleId(entity.getId()));
		}
		for (Article article : entity.getArticleList()) {
			role.addArticle(ArticleConvertor.convertFromEntity(article));
		}
		return role;
	}

}
