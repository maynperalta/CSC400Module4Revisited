package main;

import java.util.Stack;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

public class PostfixCalculator {
	
	public int evaluatePostfix(String postfixExpression) {
		Stack<Integer> stack = new Stack<>();
// Check expression for space to differentiate between single-digit and multi-digit operands		
		String[] elements = postfixExpression.trim().split("\\s+");
// Loop through expression to push integers into stack or calculate if operator is found			
		for (String element : elements) {
			if(isNumeric(element)) {
				stack.push(Integer.parseInt(element));
			} else if (isOperator(element)) {
				if (stack.size() < 2) {
					throw new IllegalArguementException("Invalid postfix expression: insufficient operands");
				}
// Calculate expression when two operands are in stack and operator found		
// IMPROVEMENTS: Exceptions used instead of Integer.MIN_VALUE to avoid conflict with legitimate calculations
				int b = stack.pop();
				int a = stack.pop();
				int result = 0;
				
				switch (element) {
				case "+":
					result = a + b;
					break;
				case "-":
					result = a - b;
					break;
				case "*":
					result = a * b;
					break;
				case "/":
					if (b == 0) {
						throw new IllegalArgumentException("Division by zero is not allowed.");
					}
					result = a / b;
					break;
				case "%":
					if (b == 0) {
						throw new IllegalArgumentException("Division by zero is not allowed.");
					}
					result = a % b;
					break;
				default:
					throw new IllegalArgumentException("Invalid operator: " + element);					
				}
				stack.push(result);
			} else {
				throw new IllegalArgumentException("Invalid token: " + element);
			}
		}
// Print error if expression cannot be calculated		
		if (stack.size() != 1) {
			throw new IllegalArgumentException("Invalid postfix expression: excessive operands.");
		}
		
		return stack.pop();
	}
// Helper function to check for integer	
	private boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
// Helper function to check for operator	
	private boolean isOperator(String str) {
		return str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/") || str.equals("%");
	}
// Code to read file and calculate postfix expression line by line	
	public void fileEx(String filename) {
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			int lineNumber = 1;
			while ((line = br.readLine()) != null) {
				System.out.println("Expression " + lineNumber + ": " + line);
// IMPROVEMENT: added try/catch block to handle errors. 				
				try {
					int result = evaluatePostfix(line);
					System.out.println("Result: " + result);
				} catch(IllegalArgumentException e) {
					System.out.println("Error: " + e.getMessage());
				}
				System.out.println();
				lineNumber++;
			}
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
	}
	public static void main(String[] args) {
		PostfixCalculator calculator = new PostfixCalculator();
		System.out.println("Reading and calculating example expressions...");
		System.out.println("");
// Example expressions from assignment parameters		
		String[] testEx = {
				"4 2 * 3 +",   // read as (4 * 2) + 3; expected result = 11
				"5 3 + 7 *",   // read as (5 + 3) * 7; expected result = 56
				"42 * +",	   // read as (4 * 2) +; expected result = error	
				"12 3 / 5 *",  // read as (12 / 3) * 5; expected result = 20	
				"70 8 % 1 -"   // read as (70 % 8) - 1; expected result = 5 
		};
// Loop to calculate example expressions		
		for (String expression : testEx) {
			System.out.println("Executing: " + expression);
// IMPROVEMENT: added try/catch block to handle errors.
			try {
				int result = calculator.evaluatePostfix(expression);
				System.out.println("Result: " + result);
			} catch (IllegalArgumentException e) {
				System.out.println("Error: " + e.getMessage());
			}
			System.out.println("------------------------------");
		}
// Execute and display calculations from file		
		System.out.println("Reading and calculating expressions from file...");
		System.out.println("");
		calculator.fileEx("expressions.txt");
	}
}