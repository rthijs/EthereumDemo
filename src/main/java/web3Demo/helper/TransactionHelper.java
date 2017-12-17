package web3Demo.helper;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;

import web3Demo.model.EtherTransactionRequest;

public interface TransactionHelper {

	public TransactionReceipt sendEtherTransaction(EtherTransactionRequest transactionRequest)
			throws IOException, InterruptedException, TransactionException, ExecutionException;

}
