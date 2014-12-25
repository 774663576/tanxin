package com.sbb.tanxin.parser;

import org.json.JSONObject;

import com.sbb.tanxin.data.result.Result;

public interface IParser {
	public Result parse(JSONObject jsonObj) throws Exception;
}
