package pl.edu.agh.to2.interpreter.expressions;

public class TerminalExpressionINTEGER extends TerminalExpressionNUMBER {
    public TerminalExpressionINTEGER(Integer number, String raw) {
        super(number, raw);
    }

    @Override
    public Integer getNumber() {
        return (Integer) super.getNumber();
    }
}
