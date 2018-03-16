package pl.edu.agh.to2.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.edu.agh.to2.commands.Command;
import pl.edu.agh.to2.commands.CommandStack;
import pl.edu.agh.to2.commands.ProceduresStack;
import pl.edu.agh.to2.commands.UnknownCommand;
import pl.edu.agh.to2.interpreter.Parser;
import pl.edu.agh.to2.interpreter.ParserPL;
import pl.edu.agh.to2.interpreter.expressions.Expression;
import pl.edu.agh.to2.interpreter.expressions.TerminalExpressionLIST;
import pl.edu.agh.to2.interpreter.expressions.TerminalExpressionNUMBER;
import pl.edu.agh.to2.interpreter.expressions.TerminalExpressionPROCEDUREVARIABLE;
import pl.edu.agh.to2.models.Call;
import pl.edu.agh.to2.models.CallStack;
import pl.edu.agh.to2.models.Procedure;
import pl.edu.agh.to2.models.Turtle;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class OverviewController {
    private CommandStack commands;
    private ProceduresStack procedures;
    private CallStack calls;
    private Parser parser;
    private AppController appController;
    private Turtle turtle;
    private File file = null;

    private static final Logger logger = LogManager.getLogger(OverviewController.class);

    @FXML
    private ImageView turtleImage;

    @FXML
    private AnchorPane pane;

    @FXML
    private TextField textField;

    @FXML
    private ComboBox<Call> comboBox_history;

    @FXML
    private TextArea console;

    @FXML
    private MenuItem closeItem;

    @FXML
    private MenuItem saveItem;

    @FXML
    private MenuItem saveAsItem;

    @FXML
    private MenuItem loadItem;

    @FXML
    private MenuItem undoItem;

    @FXML
    private MenuItem redoItem;

    @FXML
    private Button button_undo;

    @FXML
    private Button button_redo;

    public OverviewController() {
        parser = new ParserPL(this);
        commands = new CommandStack();
        procedures = new ProceduresStack();
    }

    @FXML
    private void undo() {
        commands.undo();
    }

    @FXML
    private void redo() {
        commands.redo();
    }

    @FXML
    private void reset() {
        setDefaultState();
    }

    @FXML
    private void initialize() {
        setDefaultState();
        redoItem.setAccelerator(KeyCombination.keyCombination("CTRL+SHIFT+Z"));
        redoItem.setOnAction(event -> commands.redo());

        undoItem.setAccelerator(KeyCombination.keyCombination("CTRL+SHIFT+X"));
        undoItem.setOnAction(event -> commands.undo());

        saveItem.setAccelerator(KeyCombination.keyCombination("CTRL+S"));
        saveItem.setOnAction(event -> {
            logger.debug("Starting saving the file");
            File newFile = appController.handleFileSaving(file, commands.toString(), true);
            this.file = newFile != null ? newFile : this.file;
        });

        saveAsItem.setOnAction(event -> {
            logger.debug("Starting saving the file as");
            File newFile = appController.handleFileSaving(file, commands.toString(), false);
            this.file = newFile != null ? newFile : this.file;
        });

        loadItem.setOnAction(event -> {
            logger.debug("Starting loading data");
            File newFile = appController.handleFileChoosing(file);
            if (newFile == null) {
                logger.debug("User resigned from opening file");
            } else {
                this.file = newFile;
                setDefaultState();
                ArrayDeque<String> commands = appController.handleFileReading(newFile);
                commands.forEach(command -> {
                    logger.debug(command);
                    if (command.length() > 0) calls.addCall(new Call(command));
                    console.appendText("\n" + command);
                    console.setScrollTop(Double.MAX_VALUE);
                    executeCommand(command);
                });
                executeCommand(parser.evaluate(true));
            }
            logger.debug("Finished loading data");
        });
        closeItem.setAccelerator(KeyCombination.keyCombination("CTRL+Q"));
        closeItem.setOnAction(event -> System.exit(0));
    }


    private void setDefaultState() {
        this.pane.getChildren().clear();
        this.pane.getChildren().add(turtleImage);
        this.commands = new CommandStack();
        this.procedures = new ProceduresStack();
        this.calls = new CallStack();
        this.comboBox_history.itemsProperty().bind(calls.totalProperty());
        initializeTurtle(pane.getPrefWidth() / 2 - this.turtleImage.getFitWidth() / 2,
                pane.getPrefHeight() / 2 - this.turtleImage.getFitHeight() / 2, true, 90);

        comboBox_history.valueProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null) {
                textField.setText(newItem.getCallRaw());
            }
        });


        undoItem.disableProperty().bind(commands.historyEmptyProperty());
        redoItem.disableProperty().bind(commands.futureEmptyProperty());
        button_undo.disableProperty().bind(commands.historyEmptyProperty());
        button_redo.disableProperty().bind(commands.futureEmptyProperty());

        this.textField.setText("");
        this.console.setText("Witaj w Logomocji by Wiesław Stanek & Wojciech Chmielarz");
        this.console.textProperty().addListener(event -> this.console.setScrollTop(Double.MAX_VALUE));
    }

    @FXML
    public void handleKeyPressed(KeyEvent keyEvent) {
        String s;
        Call call;
        switch (keyEvent.getCode()) {
            case ENTER:
                s = textField.getText().trim();
                if (s.length() > 0 && s.split("\\s").length > 0) {
                    textField.setText("");
                    calls.addCall(new Call(s));
                    console.appendText("\n" + s);
                    if (procedures.getProcedures().get(s.split(" ")[0]) != null) {
                        executeProcedure(s);
                    } else {
                        executeCommand(s);
                    }
                } else {
                    console.appendText("\n");
                    executeCommand(parser.evaluate(true));
                }
                break;
            case UP:
                call = calls.getPrev();
                if (call != null) textField.setText(call.getCallRaw());
                break;
            case DOWN:
                call = calls.getNext();
                textField.setText(call == null ? "" : call.getCallRaw());
                break;
        }
    }

    private void executeProcedure(String command) {
        String procedureName = command.split(" ")[0];

        Procedure procedure = procedures.getProcedures().get(procedureName);

        ArrayList<Expression> args =
                new ArrayList<>(Arrays
                        .stream(command.split(" "))
                        .skip(1)
                        .map(x -> new TerminalExpressionNUMBER(Integer.parseInt(x), x))
                        .collect(Collectors.toList()));

        ArrayList<Expression> copy = new ArrayList<>(procedure.getExpressions()); //shallow copy
        ArrayList<Expression> exp = new ArrayList<>(procedure.getExpressions()); //shallow copy

        for (Expression expression : procedure.getExpressions()) {
            if (expression instanceof TerminalExpressionPROCEDUREVARIABLE) {

                for (Expression arg : procedure.getArguments()) {
                    if (expression.getRaw().equals(arg.getRaw())) {
                        int index = exp.indexOf(expression);
                        exp.remove(index);

                        int index2 = new ArrayList<>(procedure.getArguments()).indexOf(arg);
                        exp.add(index, args.get(index2));
                    }
                }
            }

            if (expression instanceof TerminalExpressionLIST) {
                ((TerminalExpressionLIST) expression).emptify();
            }
        }

        procedure.setExpressions(new ArrayDeque<>(exp));

        ((ParserPL) parser).removeExpressions();
        ((ParserPL) parser).getExpressions().addAll(exp);

        executeCommand(parser.evaluate(false));

        procedure.setExpressions(new ArrayDeque<>(copy));
    }


    public void executeCommand(String command) {

        if (!command.equals("już") && procedures.getActiveProcedure() != null) {
            Procedure procedure = procedures.getActiveProcedure();
            parser.parse(command);

            procedure.addExpressions(((ParserPL) parser).getExpressions());
            ((ParserPL) parser).removeExpressions();
        } else {
            ((ParserPL) parser).removeExpressions();

            parser.parse(command);
            executeCommand(parser.evaluate(false));
        }
    }

    private void executeCommand(Command command) {
        if (command instanceof UnknownCommand)
            console.setText(console.getText() + "\n" + command.toString());
        commands.execute(command);
    }

    public void initializeTurtle(double x, double y, boolean visible, double angle) {
        turtle = new Turtle(x, y, visible, angle, turtleImage);
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public ArrayDeque<Node> clearCanvas() {
        this.pane.getChildren().remove(this.turtleImage);
        ArrayDeque<Node> children = new ArrayDeque<>(this.pane.getChildren());
        this.pane.getChildren().clear();
        this.pane.getChildren().add(this.turtleImage);
        return children;
    }

    public void setDefaultPosition() {
        turtle.setPosition(pane.getPrefWidth() / 2 - this.turtleImage.getFitWidth() / 2,
                pane.getPrefHeight() / 2 - this.turtleImage.getFitHeight() / 2, 90);
    }

    public Turtle getTurtle() {
        return turtle;
    }

    public AnchorPane getPane() {
        return pane;
    }

    public TextArea getConsole() {
        return console;
    }

    public ProceduresStack getProceduresStack() {
        return procedures;
    }
}
