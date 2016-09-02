package com.johnmarkese.se450.orderprocesssvc;

import com.johnmarkese.se450.utils.ParameterValidationException;

public class OrderRecordCommittedBackorder extends OrderRecordCommittedAbstr {

	private ORStatus status = ORStatus.BACKORDERED;
	private int backorderItems;

	public OrderRecordCommittedBackorder(OrderRecord orderRecordIn, int numItems)
			throws ParameterValidationException, OrderProcessorException {
		super(orderRecordIn);
		this.setBackorderItems(numItems);
	}

	private void setBackorderItems(int backorderItems) throws ParameterValidationException {
		if (backorderItems < 0) {
			throw new ParameterValidationException("Processing cost cannot be a negative number");
		}
		this.backorderItems = backorderItems;
	}

	@Override
	public int getNumItems() {
		return this.backorderItems;
	}

	@Override
	public ORStatus getOrderRecordStatus() throws OrderProcessorException {
		return status;
	}

	@Override
	public double getProcessCost() throws OrderProcessorException {
		return 0.0;
	}

	@Override
	public String toString() {
		return super.toString() + " status:" + status + " " + this.getNumItems();
	}

	@Override
	public double getItemsCost() throws OrderProcessorException {
		return 0.0;
	}
}