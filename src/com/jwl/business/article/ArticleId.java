package com.jwl.business.article;

import java.io.Serializable;

import com.jwl.business.AbstractId;

/**
 *
 * @author Lukas Rychtecky
 */
public class ArticleId extends AbstractId implements Serializable{
	private static final long serialVersionUID = 2250747352949234625L;

	public ArticleId(Integer id) {
		super(id);
	}

/*	@Override
	public boolean equals(Object object) {
		if(object instanceof ArticleId){
			ArticleId id2 = (ArticleId)object;
			if(super.getId().intValue()==id2.getId().intValue()){
				return true;
			}else{
				return false;
			}
		}
		return false;
	} */
	
	
}
