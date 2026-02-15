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
					System.out.println("Error: Invalid postfix expression");
					return Integer.MIN_VALUE;
				}
// Calculate expression when two operands are in stack and operator found				
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
						System.out.println("Error: Cannot divide by zero.");
						return Integer.MIN_VALUE;
					}
					result = a / b;
					break;
				case "%":
					if (b == 0) {
						System.out.println("Error: Cannot divide by zero.");
						return Integer.MIN_VALUE;
					}
					result = a % b;
					break;
				default:
					System.out.println("Error: Invalid operator " + element);
					return Integer.MIN_VALUE;						
				}
				stack.push(result);
			} else {
				System.out.println("Error: Invalid element: " + element);
				return Integer.MIN_VALUE;
			}
		}
// Print error if expression cannot be calculated		
		if (stack.size() != 1) {
			System.out.println("Error: Invalid postfix expression.");
			return Integer.MIN_VALUE;
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
				int result = evaluatePostfix(line);
				if (result != Integer.MIN_VALUE) {
					System.out.println("Result: " + result);
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
			int result = calculator.evaluatePostfix(expression);
			if (result != Integer.MIN_VALUE) {
				System.out.println("Result: " + result);
			}
			System.out.println("------------------------------");
		}
// Execute and display calculations from file		
		System.out.println("Reading and calculating expressions from file...");
		System.out.println("");
		calculator.fileEx("expressions.txt");
	}
}