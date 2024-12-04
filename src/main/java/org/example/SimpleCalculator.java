package org.example;

import java.util.Scanner;
import java.util.Stack;

public class SimpleCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String expression;
        System.out.println("欢迎使用简单计算器！");

        while (true) {
            System.out.print("请输入计算表达式 (输入 'exit' 退出): ");
            expression = scanner.nextLine();

            if ("exit".equalsIgnoreCase(expression)) {
                break;
            }

            try {
                double result = evaluateExpression(expression);
                System.out.println("结果: " + result);
            } catch (Exception e) {
                System.out.println("错误: " + e.getMessage());
            }
        }

        scanner.close();
    }

    // 解析并计算表达式
    public static double evaluateExpression(String expression) throws Exception {
        // 1. 处理表达式中的空格
        expression = expression.replaceAll("\\s+", "");

        // 2. 使用栈来处理表达式
        Stack<Double> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);

            // 3. 如果是数字
            if (Character.isDigit(currentChar)) {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    sb.append(expression.charAt(i));
                    i++;
                }
                values.push(Double.parseDouble(sb.toString()));
                i--; // 调整i的偏移
            }
            // 4. 如果是左括号
            else if (currentChar == '(') {
                operators.push(currentChar);
            }
            // 5. 如果是右括号
            else if (currentChar == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    values.push(performOperation(operators.pop(), values.pop(), values.pop()));
                }
                operators.pop(); // 弹出 '('
            }
            // 6. 如果是运算符
            else if (isOperator(currentChar)) {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(currentChar)) {
                    values.push(performOperation(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(currentChar);
            }
        }

        while (!operators.isEmpty()) {
            values.push(performOperation(operators.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    // 执行运算
    public static double performOperation(char operator, double b, double a) throws Exception {
        switch (operator) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/':
                if (b == 0) throw new ArithmeticException("除数不能为零");
                return a / b;
            default: throw new Exception("未知的运算符: " + operator);
        }
    }

    // 判断是否是运算符
    public static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    // 运算符的优先级
    public static int precedence(char operator) {
        if (operator == '+' || operator == '-') {
            return 1;
        }
        if (operator == '*' || operator == '/') {
            return 2;
        }
        return -1;
    }
}
