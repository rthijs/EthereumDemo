package web3Demo.model;

import java.math.BigInteger;

public class EtherTransactionRequest {

	private String fromAddress;
	
	private String toAddress;
	
	private BigInteger amount;

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAdress) {
		this.toAddress = toAdress;
	}

	public BigInteger getAmount() {
		return amount;
	}

	public void setAmount(BigInteger amount) {
		this.amount = amount;
	}
	
}
