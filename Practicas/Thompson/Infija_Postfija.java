package Thompson;

import java.util.Stack;

public class Infija_Postfija {

    static int precedencia(char c) {
        switch (c) {
            case '+':
            case '-':
            case '|':
                return 1;
            case '.':
                return 2;
            case '*':
            case '/':

                return 3;
            case '^':
                return 4;
        }
        return -1;
    }

    static String infija_postfija(String expresion) {

        String resultado = "";
        Stack<Character> pila = new Stack<>();
        for (int i = 0; i < expresion.length(); i++) {
            char c = expresion.charAt(i);

            // checa si "c" es operador
            if (precedencia(c) > 0) {
                while (pila.isEmpty() == false && precedencia(pila.peek()) >= precedencia(c)) {
                    resultado += pila.pop();
                }
                pila.push(c);
            } else if (c == ')') {
                char x = pila.pop();
                while (x != '(') {
                    resultado += x;
                    x = pila.pop();
                }
            } else if (c == '(') {
                pila.push(c);
            } else {
                // el caracter no es operador ni (
                resultado += c;
            }
        }
        for (int i = 0; i <= pila.size(); i++) {
            if (!pila.empty())
                resultado += pila.pop();
        }
        return resultado;
    }
}