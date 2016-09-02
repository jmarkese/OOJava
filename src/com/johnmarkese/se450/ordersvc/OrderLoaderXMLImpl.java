package com.johnmarkese.se450.ordersvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.johnmarkese.se450.dataloader.DataLoaderException;
import com.johnmarkese.se450.dataloader.DataLoaderXML;
import com.johnmarkese.se450.utils.ParameterValidationException;

public class OrderLoaderXMLImpl implements OrderLoader{
	
	private DataLoaderXML doc;

	public OrderLoaderXMLImpl(String path) throws DataLoaderException {
		this.doc = new DataLoaderXML(path);
	}

	@Override
	public Collection<Order> load() throws DataLoaderException, ParameterValidationException, OrderException  {
		ArrayList<Order> orders =  new ArrayList<Order>();
		//HashMap<ItemDTO, Integer> orderItems = new HashMap<ItemDTO, Integer>();
		LinkedHashMap<String, Integer> orderItems = new LinkedHashMap<String, Integer>();
		NodeList ordersNode = this.doc.getDoc().getDocumentElement().getElementsByTagName("order");
		//OrderSvc os = OrderSvc.getInstance();
		for (int i = 0; i < ordersNode.getLength(); i++) {
			if (ordersNode.item(i).getNodeType() == Node.TEXT_NODE) {
				continue;
			}
			String entryName = ordersNode.item(i).getNodeName();
			if (!entryName.equals("order")) {
				continue;
			}
			NamedNodeMap aMap = ordersNode.item(i).getAttributes();
			String orderId = aMap.getNamedItem("id").getNodeValue();
			String orderDestination = aMap.getNamedItem("destination").getNodeValue();
			String orderDay = aMap.getNamedItem("day").getNodeValue();
			int orderDayOffset = -1 + Integer.parseInt(orderDay);
			LocalDate orderDate =  LocalDate.now().plusDays(orderDayOffset);
			Element elem = (Element) ordersNode.item(i);
			NodeList items = elem.getElementsByTagName("item");
			for (int j = 0; j < items.getLength(); j++){
				NamedNodeMap itemMap = items.item(j).getAttributes();
				String code = itemMap.getNamedItem("code").getNodeValue();
				String qty = itemMap.getNamedItem("qty").getNodeValue();
				//ItemDTO itemDto = os.getItemDTO(code);
				orderItems.put(code, Integer.parseInt(qty));
			}
			orders.add(OrderFactory.order(orderId, orderDate, orderDestination, orderItems));	
			orderItems.clear();
		}
		return orders;
	}
	
}
