package com.johnmarkese.se450.orderprocesssvc;

import java.time.LocalDate;

import com.johnmarkese.se450.facilitysvc.FacilityDTO;
import com.johnmarkese.se450.ordersvc.OrderDTO;
import com.johnmarkese.se450.utils.ParameterValidationException;

public class OrderRecordImpl implements OrderRecord {
	//private FacilityDTO source;
	private String source;
	private String order;
	private String itemCode;
	private int numItems;
	private int numDaysProcess;
	private int numDaysTravel;
	private LocalDate dayStart;

	// a convenience data member to avoid exceptions in toString()
	private OrderDTO orderDTO;
	
	public OrderRecordImpl(String source, String order, String itemCode, int numItems, int numDaysProcess, int numDaysTravel,
			LocalDate dayStart) throws ParameterValidationException, OrderProcessorException {
		this.setSource(source);
		this.setOrderId(order);
		this.setItemCode(itemCode);
		this.setNumItems(numItems);
		this.setNumDaysProcess(numDaysProcess);
		this.setNumDaysTravel(numDaysTravel);
		this.setDayStart(dayStart);
		this.setOrderDTO(this.getOrderDTO());
	}
	
	private void setOrderDTO(OrderDTO orderDTOin) {
		this.orderDTO = orderDTOin;
	}

	private void setSource(String sourceIn) throws ParameterValidationException {
		if(sourceIn == null || sourceIn.isEmpty()){
			throw new ParameterValidationException("The source cannot be null or empty");
		}
		this.source = sourceIn;
	}
	
	private void setOrderId(String orderIn) throws ParameterValidationException {
		if(orderIn == null || orderIn.isEmpty()){
			throw new ParameterValidationException("The orderId must be not be null or empty");
		}
		this.order = orderIn;
	}
	
	private void setItemCode(String itemCodeIn) throws ParameterValidationException {
		if(itemCodeIn == null){
			throw new ParameterValidationException("The itemCode must be not be null");
		} else if(itemCodeIn.isEmpty()){
			throw new ParameterValidationException("The itemCode cannot be empty");			
		}
		this.itemCode = itemCodeIn;
	}

	
	private void setNumItems(int numItemsIn) throws ParameterValidationException {
		if(numItemsIn < 1){
			throw new ParameterValidationException("The number of items must be a positive number");
		}
		this.numItems = numItemsIn;
	}
	
	private void setNumDaysProcess(int numDaysProcessIn) throws ParameterValidationException {
		if(numDaysProcessIn < 1){
			throw new ParameterValidationException("The number of days to process must be a positive number");
		}
		this.numDaysProcess = numDaysProcessIn;
	}
	
	private void setNumDaysTravel(int numDaysTravelIn) throws ParameterValidationException {
		if(numDaysTravelIn < 0){
			throw new ParameterValidationException("The number of days to travel cannot be less than zero");
		}
		this.numDaysTravel = numDaysTravelIn;
	}
	
	private void setDayStart(LocalDate dayStartIn) throws ParameterValidationException {
		if (dayStartIn == null || dayStartIn.isBefore(LocalDate.now())) {
			throw new ParameterValidationException("The day specified must be initilized and cannot be in the past");
		}
		this.dayStart = dayStartIn;
	}
	
	@Override
	public FacilityDTO getSource() throws OrderProcessorException {
		return OrderProcessorSvc.getInstance().getFacility(this.source);
	}
	
	@Override
	public OrderDTO getOrderDTO() throws OrderProcessorException {
		return OrderProcessorSvc.getInstance().getOrder(this.order);
		//return this.order;
	}
	
	@Override
	public String getItemCode() {
		return this.itemCode;
	}
	
	@Override
	public int getNumItems() {
		return this.numItems;
	}
	
	@Override
	public int getNumDaysProcess() {
		return this.numDaysProcess;
	}
	
	@Override
	public int getNumDaysTravel() {
		return this.numDaysTravel;
	}
	
	@Override
	public LocalDate getDeliveryDay() {
		return this.dayStart.plusDays(numDaysProcess + numDaysTravel);
	}

	@Override
	public String toString() {
		return String.format("\nsource:%-16s orderId:%6s itemCode:%7s numItems:%3d numDaysProcess:%3d numDaysTravel:%3d Start:%s Delivered:%s", source, orderDTO.orderId, itemCode, numItems, numDaysProcess, numDaysTravel, dayStart.toString(), getDeliveryDay());
	}
	
	@Override
	public LocalDate getDayStart() {
		// TODO Auto-generated method stub
		return this.dayStart;
	}
}
