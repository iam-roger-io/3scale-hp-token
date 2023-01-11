package com.redhat.consulting.api.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

/*
 * Esta classe representa o token recebido pelo serviço de geração.
 * 
 */
@JsonTypeName("access")
@JsonTypeInfo(include= JsonTypeInfo.As.WRAPPER_OBJECT,use= JsonTypeInfo.Id.NAME)
public class Access {
	
	@Override
	public String toString() {
		return "Access [token=" + token + "]";
	}

	private Token token = new Token();

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

}
