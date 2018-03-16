package pl.edu.agh.to2.interpreter;

import com.google.common.collect.ImmutableMap;
import pl.edu.agh.to2.commands.Command;
import pl.edu.agh.to2.commands.MultiCommand;
import pl.edu.agh.to2.commands.NopCommand;
import pl.edu.agh.to2.commands.UnknownCommand;
import pl.edu.agh.to2.controllers.OverviewController;
import pl.edu.agh.to2.interpreter.expressions.*;

import java.util.*;
import java.util.stream.Collectors;

public class ParserPL implements Parser {
    private ArrayDeque<Expression> s;
    private ImmutableMap<String, Expression> logoLangExpressions;

    public ParserPL(OverviewController overviewController) {
        this.s = new ArrayDeque<>();
        this.logoLangExpressions = new ImmutableMap.Builder<String, Expression>()
                .put("np", new TerminalExpressionMOVE(overviewController, MoveType.DISTANCE, Direction.FORWARD))
                .put("ws", new TerminalExpressionMOVE(overviewController, MoveType.DISTANCE, Direction.BACKWARD))
                .put("pw", new TerminalExpressionMOVE(overviewController, MoveType.ANGLE, Direction.RIGHT))
                .put("lw", new TerminalExpressionMOVE(overviewController, MoveType.ANGLE, Direction.LEFT))
                .put("wróć", new TerminalExpressionRETURN(overviewController))
                .put("sż", new TerminalExpressionHIDE(overviewController))
                .put("pż", new TerminalExpressionSHOW(overviewController))
                .put("pod", new TerminalExpressionRAISE(overviewController))
                .put("opu", new TerminalExpressionLOWER(overviewController))
                .put("okrąg", new TerminalExpressionCIRCLE(overviewController))
                .put("czyść", new TerminalExpressionCLEAR(overviewController))
                .put("oto", new TerminalExpressionPROCEDURESTART(overviewController))
                .put("już", new TerminalExpressionPROCEDURESTOP(overviewController))
                .build();
    }

    public void parse(String command) {
        if (command.trim().indexOf("#") == 0) return;
        List<String> list = Arrays.stream(command
                .split("\\s"))
                .map(String::toLowerCase)
                .filter(exp -> exp.length() != 0)
                .collect(Collectors.toList());

        loopLabel:
        for (String expression : list) {
            for (String logoExpression : logoLangExpressions.keySet()) {
                if (expression.matches(logoExpression)) {
                    s.add(logoLangExpressions.get(logoExpression));
                    continue loopLabel;
                }
            }


            if (expression.matches("\\:.*")) {
                s.add(new TerminalExpressionPROCEDUREVARIABLE(expression));
            } else if (expression.matches("\\d+"))
                s.add(new TerminalExpressionINTEGER(Integer.parseInt(expression), expression));
            else if (expression.matches("\\d+[.,]\\d+"))
                s.add(new TerminalExpressionNUMBER(
                        Double.parseDouble(expression.replace(",", ".")), expression));
            else if (expression.matches("powtórz"))
                s.add(new TerminalExpressionREPEAT());
            else if (expression.matches("\\[.*")) {
                s.add(new TerminalExpressionLIST());
                if (expression.substring(1).length() > 0)
                    parse(expression.substring(1));
            } else if (expression.matches(".*?]")) {
                if (expression.substring(0, expression.length() - 1).length() > 0)
                    parse(expression.substring(0, expression.length() - 1));
                s.add(new TerminalExpressionLISTEND());
            } else if (expression.matches(".*?\\\\")) {
                if (expression.substring(0, expression.length() - 1).length() > 0)
                    parse(expression.substring(0, expression.length() - 1));
                s.add(new TerminalExpressionLINEBREAKER());
            } else {
                s.add(new TerminalExpressionDEFAULT(expression));
            }
        }
    }


    public Command evaluate(boolean force) {
        if (s.peekLast() instanceof TerminalExpressionLINEBREAKER && !force)
            return new NopCommand();
        ArrayDeque<Command> commands = new ArrayDeque<>();
        while (!s.isEmpty()) {
            commands.add(s.pollFirst().interpret(s));
        }

        if (!commands
                .stream()
                .filter(command -> command instanceof UnknownCommand)
                .collect(Collectors.toList()).isEmpty()) {
            return new UnknownCommand(commands);
        }

        return commands.size() > 0 ? new MultiCommand(commands) : new NopCommand();
    }

    public ArrayDeque<Expression> getExpressions() {
        return s;
    }

    public void removeExpressions() {
        s = new ArrayDeque<>();
    }
}
