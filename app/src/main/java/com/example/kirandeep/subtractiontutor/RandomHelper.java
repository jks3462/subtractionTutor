package com.example.kirandeep.subtractiontutor;

public class RandomHelper {
	public static int GetRandomNumberInRange(OperandRange operandRange) {
	    return operandRange.MinimumValue + (int)Math.round((Math.random() * (operandRange.MaximumValue - operandRange.MinimumValue)));
	}
}
