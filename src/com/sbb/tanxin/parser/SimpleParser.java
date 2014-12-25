package com.sbb.tanxin.parser;

import org.json.JSONObject;

import com.sbb.tanxin.data.result.Result;
import com.sbb.tanxin.data.result.SimpleResult;

public class SimpleParser implements IParser {

	@Override
	public Result parse(JSONObject jsonObj) throws Exception {
		if (jsonObj == null) {
			return Result.defContentErrorResult();
		}
		return new SimpleResult();
	}

}
