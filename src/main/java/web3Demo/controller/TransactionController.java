package web3Demo.controller;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;

import web3Demo.helper.TransactionHelper;
import web3Demo.model.ContractQueryRequest;
import web3Demo.model.EtherTransactionRequest;

@RestController
public class TransactionController {
	
	@Autowired
	TransactionHelper transactionHelper;

	@PostMapping("/transaction/sendEther")
	public @ResponseBody ResponseEntity<TransactionReceipt> sendEther(
			@RequestBody EtherTransactionRequest transactionRequest) throws IOException, InterruptedException, TransactionException, ExecutionException {

		System.out.println("From address = " + transactionRequest.getFromAddress());
		System.out.println("To address = " + transactionRequest.getToAddress());
		System.out.println("amount = " + transactionRequest.getAmount());

		TransactionReceipt receipt = transactionHelper.sendEtherTransaction(transactionRequest);

		return new ResponseEntity<TransactionReceipt>(receipt, HttpStatus.OK);
	}
	
	@PostMapping("/transaction/queryContract")
	public @ResponseBody ResponseEntity<String> queryContract(
			@RequestBody ContractQueryRequest contractRequest) throws InterruptedException, ExecutionException {
		
		System.out.println("Contract address = " + contractRequest.getAddress());
		System.out.println("function = " + contractRequest.getFunctionName());
		
		String receipt = transactionHelper.queryContract(contractRequest);
		
		return new ResponseEntity<String>(receipt, HttpStatus.OK);
		
	}

}
