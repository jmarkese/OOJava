package com.johnmarkese.se450.itemsvc;

public class ItemDTO {
	public final String code;
	public final double price;

	public ItemDTO(String code, double price) {
		this.code = code;
		this.price = price;
	}

	public String toString() {
		return this.code;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		ItemDTO that = (ItemDTO) obj;
		return this.code.equals(that.code);
	}

	public int hashCode() {
		return this.code.hashCode();
	}
}
