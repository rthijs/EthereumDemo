package web3Demo.model;

import java.math.BigInteger;

public class Account {

	private String address;

	private BigInteger balance;

	public Account(String address, BigInteger balance) {
		this.address = address;
		this.balance = balance;
	}

	public String getAddress() {
		return address;
	}

	public BigInteger getBalance() {
		return balance;
	}

	public static class AccountBuilder {

		private String address;

		private BigInteger balance;

		public AccountBuilder address(final String address) {
			this.address = address;
			return this;
		}

		public AccountBuilder balance(final BigInteger balance) {
			this.balance = balance;
			return this;
		}

		public Account build() {
			return new Account(this.address, this.balance);
		}

	}

}
