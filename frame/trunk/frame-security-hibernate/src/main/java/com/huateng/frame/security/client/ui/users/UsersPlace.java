package com.huateng.frame.security.client.ui.users;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class UsersPlace extends Place {
	public static class Tokenizer implements PlaceTokenizer<UsersPlace>
	{

		public UsersPlace getPlace(String token) {
			return new UsersPlace();
		}

		public String getToken(UsersPlace place) {
			return "";
		}
		
	}
}
