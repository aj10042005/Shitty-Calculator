import Calculator.Calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println(Calculator.calc(new BufferedReader(new InputStreamReader(System.in)).readLine()));
    }
}
