package web3Demo.helper;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Ethereum;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

import web3Demo.model.ContractQueryRequest;
import web3Demo.model.EtherTransactionRequest;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;

@Component
public class TransactionHelperImpl implements TransactionHelper {

	@Autowired
	private Web3j web3j;
	
	@Autowired
	private AccountHelper accountHelper;

	@Override
	public TransactionReceipt sendEtherTransaction(EtherTransactionRequest transactionRequest)
			throws IOException, InterruptedException, TransactionException, ExecutionException {

		if (accountHelper.unlockAccount(transactionRequest.getFromAddress())) {
			ClientTransactionManager transactionManager = new ClientTransactionManager(web3j,transactionRequest.getFromAddress());
			Transfer transfer = new Transfer(web3j,transactionManager);
			return transfer.sendFunds(transactionRequest.getToAddress(), BigDecimal.valueOf(transactionRequest.getAmount().longValue()), Unit.WEI).sendAsync().get();
		}
		return null;
	}

	@Override
	public String queryContract(ContractQueryRequest contractRequest) throws InterruptedException, ExecutionException {
		
		Function function = new Function(
	             contractRequest.getFunctionName(),  // function we're calling
	             Arrays.<Type>asList(),  // Parameters to pass as Solidity Types
	             Collections.<TypeReference<?>>emptyList());
		
		String encodedFunction = FunctionEncoder.encode(function);

		EthCall response = web3j
				.ethCall(Transaction.createEthCallTransaction(null, contractRequest.getAddress(), encodedFunction),
						DefaultBlockParameterName.LATEST)
				.sendAsync().get();
		
		return FunctionReturnDecoder.decodeIndexedValue(response.getValue(), new TypeReference<Uint>(){}).getValue().toString();
	}

}
