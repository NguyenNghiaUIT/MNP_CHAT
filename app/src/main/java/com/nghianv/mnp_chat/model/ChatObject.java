package com.nghianv.mnp_chat.model;

import org.json.JSONException;
import org.json.JSONObject;

public class ChatObject {
	private String mCommand;
	private String mContent;
	private String mName;
	
	public ChatObject(){
		
	}
	
	public ChatObject(String command, String name, String content){
		this.mCommand = command;
		this.mName = name;
		this.mContent = content;
	}
	

	public void setCommand(String command){
		this.mCommand = command;
	}
	
	public void setName(String name){
		this.mName = name;
	}
	
	public void setContent(String content){
		this.mContent = content;
	}
	
	public String getChatString(){
		JSONObject obj = new JSONObject();
		try {
			obj.put(Command.COMMAND_KEY, mCommand);
			obj.put(Command.NAME_KEY, mName);
			obj.put(Command.CONTENT_KEY, mContent);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return obj.toString();
		
		
		
		
	}

}
