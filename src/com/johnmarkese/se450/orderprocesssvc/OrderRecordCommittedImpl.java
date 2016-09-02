package com.johnmarkese.se450.orderprocesssvc;

import java.time.LocalDate;

import com.johnmarkese.se450.dataloader.DataLoaderException;
import com.johnmarkese.se450.utils.ParameterValidationException;

public class OrderRecordCommittedImpl extends OrderRecordCommittedAbstr {
	private ORStatus status = ORStatus.COMMITTED;
	private double cost; // convenience data member for toString()

	public OrderRecordCommittedImpl(OrderRecord orderRecordIn, int numItems) throws ParameterValidationException, OrderProcessorException {
		super(orderRecordIn);
		if(orderRecordIn.getNumItems() < numItems){
			throw new OrderProcessorException("You cannot commit an Order Record that stocks fewer items than needed. ("+this.getSource().name+") This order should be partially backordered");
		}
		OrderProcessorSvc.getInstance().bookFacilty(getSource().name, numItems, getItemCode(), getDayStart());
		this.cost = getProcessCost();
	}

	@Override
	public ORStatus getOrderRecordStatus() throws OrderProcessorException {
		return status;
	}

	@Override
	public double getProcessCost() throws OrderProcessorException {
		//System.out.println(String.format("%-20s %s", this.getSource().name, " Cost:" +this.getSource().cost + " Items:" + this.getNumItems() + " Rate:" + this.getSource().rate)); //System.out.println(this.getSource().cost * this.getNumItems() / this.getSource().rate);
		
		//This is the calculation according to the forum discussion
		return this.getSource().cost * this.getNumItems() / this.getSource().rate;
		
		//This is the calculation according to the hand-out.
		//return this.getSource().cost * this.getNumDaysProcess(); 
	}
	
	public String toString() {
		return super.toString() + " status:" + status + " prcCost:" + cost;
	}

	@Override
	public double getItemsCost() throws OrderProcessorException {
		try {
			return OrderProcessorSvc.getInstance().getItem(super.getItemCode()).price * super.getNumItems();
		} catch (DataLoaderException e) {
			e.printStackTrace();
			throw new OrderProcessorException(e.getMessage());
		}
	}

}
