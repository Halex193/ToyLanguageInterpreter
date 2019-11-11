package utils;

import java.util.List;

import model.expressions.ArithmeticExpression;
import model.expressions.Expression;
import model.expressions.ValueExpression;
import model.expressions.VariableExpression;
import model.statements.*;
import model.types.IntType;
import model.types.StringType;
import model.values.IntValue;
import model.values.StringValue;

public class ProgramUtils
{
    private ProgramUtils()
    {
    }

    public static List<Statement> generatePrograms()
    {
        return List.of(program1(), program2(), program3(), program4());
    }

    private static Statement program4()
    {
        return null;//TODO
    }

    private static Statement program3()
    {
        return new CompoundStatement(
                new VariableDeclaration(new StringType(), "file"),
                new CompoundStatement(
                        new AssignStatement("file", new ValueExpression(new StringValue("input/input.in"))),
                        new CompoundStatement(
                                new OpenRFileStatement(new VariableExpression("file")),
                                new CompoundStatement(
                                        new VariableDeclaration(new IntType(), "var"),
                                        new CompoundStatement(
                                                new ReadFileStatement(new VariableExpression("file"), "var"),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("var")),
                                                        new CompoundStatement(
                                                                new ReadFileStatement(new VariableExpression("file"), "var"),
                                                                new CompoundStatement(
                                                                        new PrintStatement(new VariableExpression("var")),
                                                                        new CloseRFileStatement(new VariableExpression("file"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    private static Statement program1()
    {
        return new CompoundStatement(
                new VariableDeclaration(new IntType(), "v"),
                new CompoundStatement(
                        new AssignStatement(
                                "v",
                                new ArithmeticExpression(
                                        new ValueExpression(new IntValue(1)),
                                        new ValueExpression(new IntValue(5)),
                                        1
                                )
                        ),
                        new PrintStatement(new VariableExpression("v"))
                )
        );
    }

    private static Statement program2()
    {
        return new CompoundStatement(
                new VariableDeclaration(new IntType(), "a"),
                new CompoundStatement(
                        new VariableDeclaration(new IntType(), "b"),
                        new CompoundStatement(
                                new AssignStatement(
                                        "a",
                                        new ArithmeticExpression(
                                                new ValueExpression(new IntValue(2)),
                                                new ArithmeticExpression(
                                                        new ValueExpression(new IntValue(3)),
                                                        new ValueExpression(new IntValue(5)),
                                                        "*"
                                                ),
                                                "+"
                                        )
                                ),
                                new CompoundStatement(
                                        new AssignStatement(
                                                "b",
                                                new ArithmeticExpression(
                                                        new VariableExpression("a"),
                                                        new ValueExpression(new IntValue(1)),
                                                        "+"
                                                )
                                        ),
                                        new PrintStatement(new VariableExpression("b"))
                                )
                        )
                )
        );
    }
}
