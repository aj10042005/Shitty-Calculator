import Calculator.Calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        String exp = new BufferedReader(new InputStreamReader(System.in)).readLine();
        System.out.println(Calculator.toReversePolishNotation(exp.toCharArray()));
        System.out.println(Calculator.calc(exp));
    }
}