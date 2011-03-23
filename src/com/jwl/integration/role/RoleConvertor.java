package com.jwl.integration.role;

import com.jwl.business.security.Role;
import com.jwl.business.security.RoleId;
import com.jwl.integration.convertor.ArticleConvertor;
import com.jwl.integration.entity.Article;
import java.util.ArrayList;

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

	public static RoleEntity toEntity(Role role) {
		RoleEntity entity = new RoleEntity();
		if (role.getId() != null) {
			entity.setId(role.getId().getId());
		}
		entity.setCode(role.getCode());
		entity.setPermissionList(new ArrayList<PermissionEntity>());

		return entity;
	}

}
