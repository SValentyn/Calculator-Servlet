import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The servlet is used to process user GET requests, that is, to handle interactions with the calculator.
 * There is no need for mapping in web.xml.
 *
 * @author Syniuk Valentyn
 */
@WebServlet("/CalculatorServlet")
public class CalculatorServlet extends HttpServlet {

    private boolean ifExistsFirstNumber;
    private boolean ifExistsSecondNumber;
    private double firstNumber, secondNumber;
    private StringBuilder builder;
    private Operations operation;
    private String result;

    @Override
    public void init() {
        ifExistsFirstNumber = false;
        ifExistsSecondNumber = false;
        builder = new StringBuilder();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("0") != null) setZero();
        else if (request.getParameter("1") != null) setOne();
        else if (request.getParameter("2") != null) setTwo();
        else if (request.getParameter("3") != null) setThree();
        else if (request.getParameter("4") != null) setFour();
        else if (request.getParameter("5") != null) setFive();
        else if (request.getParameter("6") != null) setSix();
        else if (request.getParameter("7") != null) setSeven();
        else if (request.getParameter("8") != null) setEight();
        else if (request.getParameter("9") != null) setNine();
        else if (request.getParameter("+") != null) setAddition();
        else if (request.getParameter("-") != null) setSubtraction();
        else if (request.getParameter("*") != null) setMultiplication();
        else if (request.getParameter("/") != null) setDivision();
        else if (request.getParameter("=") != null) setEquals();
        else if (request.getParameter("C") != null) clear();

        request.setAttribute("result", result);
        request.getRequestDispatcher("/html/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void setZero() {
        addSymbol("0");
    }

    private void setOne() {
        addSymbol("1");
    }

    private void setTwo() {
        addSymbol("2");
    }

    private void setThree() {
        addSymbol("3");
    }

    private void setFour() {
        addSymbol("4");
    }

    private void setFive() {
        addSymbol("5");
    }

    private void setSix() {
        addSymbol("6");
    }

    private void setSeven() {
        addSymbol("7");
    }

    private void setEight() {
        addSymbol("8");
    }

    private void setNine() {
        addSymbol("9");
    }

    private void addSymbol(String symbol) {
        builder.append(symbol);
        updateResult();
    }

    private void updateResult() {
        result = builder.toString();
    }

    private void updateResult(String result) {
        this.result = result;
    }

    private void setAddition() {
        getNumber();
        operation = Operations.Addition;
    }

    private void setSubtraction() {
        getNumber();
        operation = Operations.Subtraction;
    }

    private void setMultiplication() {
        getNumber();
        operation = Operations.Multiplication;
    }

    private void setDivision() {
        getNumber();
        operation = Operations.Division;
    }

    private void setEquals() {
        getNumber();
        updateResult(calculate());
    }

    private void clear() {
        ifExistsFirstNumber = false;
        ifExistsSecondNumber = false;
        operation = Operations.None;
        builder = new StringBuilder();
        updateResult(String.valueOf(builder));
    }

    /**
     * Getting a number when calling the operation.
     */
    private void getNumber() {
        if (!ifExistsFirstNumber) {
            if (builder.length() > 0) {
                firstNumber = Double.parseDouble(builder.toString());
                ifExistsFirstNumber = true;
            }
        } else {
            if (builder.length() > 0) {
                secondNumber = Double.parseDouble(builder.toString());
                ifExistsSecondNumber = true;
            }
        }
        builder = new StringBuilder();
    }

    /**
     * The main logical element of calculating.
     */
    private String calculate() {
        if (operation == Operations.None || !ifExistsFirstNumber || !ifExistsSecondNumber) {
            return "undefined";
        } else {
            switch (operation) {
                case Addition: {
                    firstNumber += secondNumber;
                    break;
                }
                case Subtraction: {
                    firstNumber -= secondNumber;
                    break;
                }
                case Multiplication: {
                    firstNumber *= secondNumber;
                    break;
                }
                case Division: {
                    firstNumber /= secondNumber;
                    break;
                }
            }
        }
        ifExistsFirstNumber = true;
        ifExistsSecondNumber = false;
        operation = Operations.None;
        builder = new StringBuilder();
        return String.valueOf(firstNumber);
    }

}




