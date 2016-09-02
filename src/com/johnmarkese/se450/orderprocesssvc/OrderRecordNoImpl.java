package com.johnmarkese.se450.orderprocesssvc;

import java.time.LocalDate;

import com.johnmarkese.se450.facilitysvc.FacilityDTO;
import com.johnmarkese.se450.ordersvc.OrderDTO;
import com.johnmarkese.se450.utils.ParameterValidationException;

public class OrderRecordNoImpl implements OrderRecord {

	private OrderDTO order;
	private String itemCode;
	private LocalDate dayStart;

	
	public OrderRecordNoImpl(OrderDTO order, String itemCode, LocalDate dayStart) throws ParameterValidationException {
		this.setOrder(order);
		this.setItemCode(itemCode);
		this.setDayStart(dayStart);
	}
	
	private void setOrder(OrderDTO order) throws ParameterValidationException {
		if(order == null){
			throw new ParameterValidationException("The orderId must be not be null");
		}
		this.order = order;
	}
	
	private void setItemCode(String itemCode) throws ParameterValidationException {
		if(itemCode == null){
			throw new ParameterValidationException("The itemCode must be not be null");
		} else if(itemCode.isEmpty()){
			throw new ParameterValidationException("The itemCode cannot be empty");			
		}
		this.itemCode = itemCode;
	}

	
	private void setDayStart(LocalDate dayStart) throws ParameterValidationException {
		if (dayStart == null || dayStart.isBefore(LocalDate.now())) {
			throw new ParameterValidationException("The day specified must be initilized and cannot be in the past");
		}
		this.dayStart = dayStart;
	}

	@Override
	public FacilityDTO getSource() {
		return null;
	}

	@Override
	public OrderDTO getOrderDTO() {
		return order;
	}

	@Override
	public String getItemCode() {
		return null;
	}

	@Override
	public int getNumItems() {
		return 0;
	}

	@Override
	public int getNumDaysProcess() {
		return Integer.MAX_VALUE;
	}

	@Override
	public int getNumDaysTravel() {
		return Integer.MAX_VALUE;
	}

	@Override
	public LocalDate getDeliveryDay() {
		return LocalDate.MAX;
	}

	@Override
	public LocalDate getDayStart() {
		return this.dayStart;
	}
	
	@Override
	public String toString() {
		return String.format("\nsource:%-16s orderId:%6s itemCode:%7s numItems:%3d numDaysProcess:%3d numDaysTravel:%3d dayStart:%s", getSource(), order.orderId, itemCode, getNumItems(), getNumDaysProcess(), getNumDaysTravel(), dayStart.toString());
		//return "\nsource:" + source + " orderId:" + order.orderId  + " itemCode:" + itemCode + " numItems:" + numItems + " numDaysProcess:" + numDaysProcess + " numDaysTravel:" + numDaysTravel + " dayStart:" + dayStart.toString();
	}

}
