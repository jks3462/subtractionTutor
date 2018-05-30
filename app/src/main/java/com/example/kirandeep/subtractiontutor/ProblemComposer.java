package com.example.kirandeep.subtractiontutor;

import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.Random;

public abstract class ProblemComposer  {
	protected String operator;
	TextToSpeech t1;
	protected int operand1;
	protected int operand2;
	protected OperandRange operand1Range;
	protected OperandRange operand2Range;
	protected int correctAnswer;
	
	private OperandRange swapOperandRange = new OperandRange(0, 100);

	public int getOperand1()
	{
		return operand1;
	}
	public int getOperand2()
	{
		return operand2;
	}
	public int getCorrectAnswer()
	{
		return correctAnswer;
	}
	
	public ProblemComposer(OperandRange operand1Range, OperandRange operand2Range)
	{
		this.operand1Range = operand1Range;
		this.operand2Range = operand2Range;
	}
	
	public void GenerateProblem() {
		operand1 = RandomHelper.GetRandomNumberInRange(operand1Range);
		operand2 = RandomHelper.GetRandomNumberInRange(operand2Range);
		

		if (operator == "+"){
			correctAnswer = operand1 + operand2;
		}
		else if (operator == "*") {
			correctAnswer = operand1 * operand2;
		}
	}

	public abstract void showImageHint(TableLayout tableLayout, Button[] uiViewsToDisableDuringVisualization);

	public String GetProblemText(Boolean showCorrectAnswer){
		// Swap operands sometimes since you want them to learn that 4+5 is the same as 5+4 
		// In division & subtraction you need to override this method since order matters so you cannot swap
		if (RandomHelper.GetRandomNumberInRange(swapOperandRange) > 50) {
			swapOperands();
		}
		return String.format("%d %s %d = %s", operand1, operator, operand2, (showCorrectAnswer ? correctAnswer : ""));
	}
	public String GetProblemTextForAudio(Boolean showCorrectAnswer){
		// Swap operands sometimes since you want them to learn that 4+5 is the same as 5+4
		// In division & subtraction you need to override this method since order matters so you cannot swap
		//if (RandomHelper.GetRandomNumberInRange(swapOperandRange) > 50) {
		//	swapOperands();
		//}


		return String.format("%d %s %d = %s", operand1, "minus", operand2, (showCorrectAnswer ? correctAnswer : ""));
	}

	public void GenerateAnswerPossibilities(Button[] answerButtons)
	{
		ArrayList<Integer> alreadyGeneratedAnswers = new ArrayList<Integer>();
		alreadyGeneratedAnswers.add(correctAnswer);
		
		Random correctAnswerButtonIndexRandomizer = new Random();
		int correctAnswerButtonIndex = correctAnswerButtonIndexRandomizer.nextInt(answerButtons.length);
		
		for (int i=0; i<answerButtons.length; i++)
	    {
	      if (correctAnswerButtonIndex == i) {
	    	  answerButtons[i].setText(String.valueOf(correctAnswer));
	    	  answerButtons[i].setTag(correctAnswer);
	      }
	      else {
	    	  int dummyAnswerValue = generateDummyAnswerValue(alreadyGeneratedAnswers);
	    	  alreadyGeneratedAnswers.add(dummyAnswerValue);
	    	  answerButtons[i].setText(String.valueOf(dummyAnswerValue));
	    	  answerButtons[i].setTag(dummyAnswerValue);
	      }
	    }
	}
	
	protected int generateDummyAnswerValue(ArrayList<Integer> disallowedDummyValues){
		int dummyAnswerValue;
		do{
			dummyAnswerValue =  RandomHelper.GetRandomNumberInRange(new OperandRange(correctAnswer - 5, correctAnswer + 5)); // Put some spread on the possible answers
		} while (disallowedDummyValues.contains(dummyAnswerValue));
		
		return dummyAnswerValue;
	}
	
	public void enableViews(View[] views, Boolean enable){
		for (View view : views) {
		    view.setClickable(enable);
		}
	}

	protected void swapOperands() {
		int temp;
		temp = operand1;
		operand1 = operand2;
		operand2 = temp;
	}

	protected int occupiesVisualRows(int number) {
		return ((number - (number % 10)) / 10) + ((number % 10) > 0 ? 1 : 0);
	}

}
