package com.fable.industry.bussiness.service.idgenerate;

import com.fable.industry.api.exception.DaoException;

public interface IdentifierGenerator {
	Object generate() throws DaoException;
}
