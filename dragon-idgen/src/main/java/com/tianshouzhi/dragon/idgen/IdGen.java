package com.tianshouzhi.dragon.idgen;

/**
 * Created by TIANSHOUZHI336 on 2017/3/9.
 */
public interface IdGen {
	public Long getAutoIncrementId() throws Exception;

	public Long getAutoIncrementId(IdDecorator idDecorator) throws Exception;
}
