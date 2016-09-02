package com.johnmarkese.se450.orderprocesssvc;

public interface OrderRecordCommitted extends OrderRecord {
	public enum ORStatus{
		COMMITTED, BACKORDERED
	};
	
	public ORStatus getOrderRecordStatus() throws OrderProcessorException;
	
	public double getProcessCost() throws OrderProcessorException;
	
	public double getItemsCost() throws OrderProcessorException;
}
