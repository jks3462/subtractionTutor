package com.example.kirandeep.subtractiontutor;

public class ProblemComposerFactory {
	public static ProblemComposer Create(Options options){
    /*	if (options.Operator.equals("+"))
    	{
    		return new AdditionProblemComposer(options.Operand1,options.Operand2);
    	}
    	else if (options.Operator.equals("-"))
    	{*/
    		return new SubtractionProblemComposer(options.Operand1,options.Operand2);
    /*	}
    	else 
    	{
    		return new MultiplicationProblemComposer(options.Operand1,options.Operand2);
    	}*/
	}
}
