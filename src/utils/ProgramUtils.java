package utils;

import java.util.Arrays;
import java.util.List;

import model.expressions.*;
import model.statements.*;
import model.types.IntType;
import model.types.ReferenceType;
import model.types.StringType;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;

public class ProgramUtils
{
    private ProgramUtils()
    {
    }

    public static List<Statement> generatePrograms()
    {
        return List.of(program1(), program2(), program3(), program4(), program5(), program6());
    }

    private static Statement program6()
    {
        return concatenate(
                new VariableDeclaration(new ReferenceType(new IntType()), "v"),
                new AllocateHeapStatement("v", new ValueExpression(new IntValue(20))),
                new VariableDeclaration(new ReferenceType(new ReferenceType(new IntType())), "a"),
                new AllocateHeapStatement("a", new VariableExpression("v")),
                new AllocateHeapStatement("v", new ValueExpression(new IntValue(30))),
                new AllocateHeapStatement("v", new ValueExpression(new IntValue(40))),
                new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))))
        );
    }

    private static Statement program5()
    {
        return concatenate(
                new VariableDeclaration(new ReferenceType(new IntType()), "v"),
                new AllocateHeapStatement("v", new ValueExpression(new IntValue(20))),
                new VariableDeclaration(new ReferenceType(new ReferenceType(new IntType())), "a"),
                new AllocateHeapStatement("a", new VariableExpression("v")),
                new WriteHeapStatement("v", new ValueExpression(new IntValue(30))),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                new PrintStatement(new ArithmeticExpression(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))), new ValueExpression(new IntValue(5)), "+"))
        );
    }

    private static Statement program4()
    {
        return concatenate(
                new VariableDeclaration(new ReferenceType(new IntType()), "v"),
                new AllocateHeapStatement("v", new ValueExpression(new IntValue(20))),
                new VariableDeclaration(new ReferenceType(new ReferenceType(new IntType())), "a"),
                new AllocateHeapStatement("a", new VariableExpression("v")),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                new PrintStatement(new ArithmeticExpression(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))), new ValueExpression(new IntValue(5)), "+"))
        );
    }

    private static Statement program()
    {
        return concatenate(
                new VariableDeclaration(new IntType(), "a"),
                new AssignStatement("a", new ValueExpression(new IntValue(3))),
                new PrintStatement(new VariableExpression("a"))
        );
    }

    private static Statement concatenate(Statement... statements)
    {
        if (statements.length > 1)
        {
            return new CompoundStatement(statements[0], concatenate(Arrays.copyOfRange(statements, 1, statements.length)));
        }
        if (statements.length == 1)
        {
            return statements[0];
        }
        return null;
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
