package web3Demo.model;

import java.math.BigInteger;

public class ContractTransactionRequest {

	private String contractAddress;
	
	private String fromAddress;
	
	private BigInteger gasPrice;
	
	private BigInteger gasLimit;
	
	private BigInteger funds;

	public String getContractAddress() {
		return contractAddress;
	}

	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public BigInteger getGasPrice() {
		return gasPrice;
	}

	public void setGasPrice(BigInteger gasPrice) {
		this.gasPrice = gasPrice;
	}

	public BigInteger getGasLimit() {
		return gasLimit;
	}

	public void setGasLimit(BigInteger gasLimit) {
		this.gasLimit = gasLimit;
	}

	public BigInteger getFunds() {
		return funds;
	}

	public void setFunds(BigInteger funds) {
		this.funds = funds;
	}

}
