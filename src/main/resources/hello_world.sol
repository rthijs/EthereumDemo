pragma solidity ^0.4.16;

contract Hello_World {
    
    uint counter = 5;
    
    function add() public {
        counter++;
    }
    
    function subtract() public {
        counter--;
    }
    
    function getCounter() public constant returns (uint256) {
        return counter;
    }
    
}