package com.johnmarkese.se450.itemsvc;

import com.johnmarkese.se450.utils.ParameterValidationException;

public class ItemImpl implements Item {
	private String code;
	private double price;
	
	public ItemImpl(String code, double price) throws ParameterValidationException {
		// TODO Auto-generated constructor stub
		this.setCode(code);
		this.setPrice(price);
	}
	
	public String getCode() {
		return this.code;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	@Override
	public ItemDTO getDTO() {
		return new ItemDTO(this.getCode(), this.getPrice());
	}
	
	@Override
	public String toString(){
		return this.code + ":" + this.price;
	}

	private void setCode(String code) throws ParameterValidationException {
		String check = code;
		if(check.isEmpty() || check == null){
			throw new ParameterValidationException("The Item code cannot be empty");
		}
		this.code = code;
	}
	
	private void setPrice(double price)  throws ParameterValidationException {
		double check = price;
		if( check < 0 || !Double.isFinite(check)){
			throw new ParameterValidationException("The Item code cannot be empty, negative, NaN, or infinite");
		}

		this.price = price;
	}

}
