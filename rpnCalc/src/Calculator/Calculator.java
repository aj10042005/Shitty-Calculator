package Calculator;

import java.util.EmptyStackException;
import java.util.Stack;

public abstract class Calculator {

    static class NumBuffer {
        private StringBuffer num;
        private boolean isEmpty;
        public static NumBuffer instance;

        public NumBuffer() {
            num = new StringBuffer();
            isEmpty=true;
        }

        public Double pop() {
            if(this.isEmpty)return null;
            else {
                isEmpty=true;
                return Double.valueOf(num.toString());
            }
        }

        public boolean isEmpty() {
            return isEmpty;
        }

        public void push(char in) {
            if(isEmpty) {isEmpty=false;
                num.delete(0,num.length()).append(in);
            } else {
                num.append(in);
            }
        }
    }

    private static int checkForMinus(char[] in, int i, int len, StringBuffer exp) {
        boolean minusNeeded=true;
        for(i+=1;i < len;i+=1) {
            if(Character.isDigit(in[i])) {
                if(minusNeeded) exp.append("-");
                return i-1;
            }

            else if(in[i] == '\u0020');

            else if(in[i] == '-') minusNeeded=!minusNeeded;

            else {
                System.err.println("Line " + i + ": operator after minus");
                return i-1;
            }
        }
        return len;
    }

    private static int onBracketMet(char[] in, int start, StringBuffer exp) {
        int iter = in.length;
        int bracks = 0, j = start;
        for(; j < iter; j++) {
            if(in[j]=='(')bracks+=1;
            else if(in[j]==')') {
                bracks-=1;
                if(bracks==0)break;
            }

        }
        if(bracks>0)System.err.println("Expression has missing closing brackets: " + bracks);
        exp.append(toReversePolishNotation(String.valueOf(in).substring(start+1, j).toCharArray()));
        return j;
    }

    public static String toReversePolishNotation(char[] in) {
        StringBuffer exp = new StringBuffer(), aftermath = new StringBuffer();
        int iter = in.length;
        char mulDiv = '='; boolean isMul=false;



        for (int i = 0; i < iter; i++) {
            if(Character.isDigit(in[i])||in[i]=='.') exp.append(in[i]);
            else if(in[i]=='\u0020');
            else {
                exp.append('\u0020');

                if(isMul && in[i]!='(') {
                    exp.append(mulDiv).append('\u0020');
                    isMul = false;
                }

                switch(in[i]) {//test lines

                    case '-':
                        i = checkForMinus(in, i, iter, exp);
                        break;
                    case '+':
                        aftermath.append('+');
                        break;
                    case '*':
                    case '/':
                        isMul = true;
                        mulDiv = in[i];
                        break;
                    case '(':
                        i = onBracketMet(in, i, exp);

                        break;
                    default:
                        System.err.println("Can't read " + in[i]);
                        break;
                }

            }
        }
        if(isMul) exp.append(mulDiv);

        return exp.append(aftermath).toString();
    }

    private static double calcRPNExp(String in) { //TODO: Make it less shitty and buggy mess than it is now
        Stack<Double> stek = new Stack<>();
        int len = in.length();
        char[] exp = in.toCharArray();

        NumBuffer buffer = new NumBuffer();

        try {
            for (int i = 0; i < len; i++) {
                if (Character.isDigit(exp[i]) || exp[i] == '.' || exp[i] == '-') {
                    buffer.push(exp[i]);
                } else {
                    if (!buffer.isEmpty()) stek.push(buffer.pop());

                    switch (exp[i]) {
                        case '+':
                            stek.push(stek.pop() + stek.pop());
                            break;
                        case '*':
                            stek.push(stek.pop() * stek.pop());
                            break;
                        case '/':
                            stek.push(1 / (stek.pop() / stek.pop()));
                            break;
                    }

                }
            }
            return stek.pop();

        } catch(EmptyStackException e) {
            System.err.println("Not enough operands given, returning zero");
            return 0;
        }
    }


    public static String calc(String in) {
        char[] exp = in.toCharArray();
        return Double.toString(calcRPNExp(toReversePolishNotation(exp)));
    }
}