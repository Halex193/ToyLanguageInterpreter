package utils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import model.expressions.*;
import model.statements.*;
import model.types.IntType;
import model.types.ReferenceType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;

public class ProgramUtils
{
    private ProgramUtils()
    {
    }

    public static void cleanLogDirectory()
    {
        File[] logFiles = new File("logs").listFiles();
        if (logFiles == null) return;
        for (File logFile : logFiles)
        {
            boolean deleted = logFile.delete();
        }
    }

    public static List<Statement> generatePrograms()
    {
        return List.of(program1(), program2(), program3(), program4(), program5(), program6(), program7(), program8(), program9(), program10(), program11());
    }

    private static Statement program10()
    {
        return concatenate(
                new VariableDeclaration(new ReferenceType(new IntType()), "a"),
                new VariableDeclaration(new ReferenceType(new IntType()), "b"),
                new AllocateHeapStatement("a", new ValueExpression(new IntValue(0))),
                new AllocateHeapStatement("b", new ValueExpression(new IntValue(0))),
                new VariableDeclaration(new IntType(), "v"),
                new WriteHeapStatement("a", new ValueExpression(new IntValue(1))),
                new WriteHeapStatement("b", new ValueExpression(new IntValue(2))),
                new ConditionalAssignmentStatement("v",
                        new RelationalExpression(
                                new ReadHeapExpression(new VariableExpression("a")),
                                new ReadHeapExpression(new VariableExpression("b")),
                                "<"
                        ),
                        new ValueExpression(new IntValue(100)),
                        new ValueExpression(new IntValue(200))
                ),
                new PrintStatement(new VariableExpression("v")),
                new ConditionalAssignmentStatement("v",
                        new RelationalExpression(
                                new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("b")),new ValueExpression(new IntValue(2)), "-"),
                                new ReadHeapExpression(new VariableExpression("a")),
                                ">"
                        ),
                        new ValueExpression(new IntValue(100)),
                        new ValueExpression(new IntValue(200))
                ),
                new PrintStatement(new VariableExpression("v"))
        );
    }

    private static Statement program11()
    {
        return concatenate(
                new NOPStatement()
        );
    }

    private static Statement program9()
    {
        return concatenate(
                new VariableDeclaration(new IntType(), "a"),
                new AssignStatement("a", new LogicExpression(
                        new ValueExpression(new BoolValue(false)),
                        new ValueExpression(new BoolValue(true)),
                        "and"))
        );
    }

    private static Statement program8()
    {
        return concatenate(
                new VariableDeclaration(new IntType(), "v"),
                new VariableDeclaration(new ReferenceType(new IntType()), "a"),
                new AssignStatement("v", new ValueExpression(new IntValue(10))),
                new AllocateHeapStatement("a", new ValueExpression(new IntValue(22))),
                new ForkStatement(concatenate(
                        new WriteHeapStatement("a", new ValueExpression(new IntValue(30))),
                        new AssignStatement("v", new ValueExpression(new IntValue(32))),
                        new PrintStatement(new VariableExpression("v")),
                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                )),
                new PrintStatement(new VariableExpression("v")),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
        );
    }

    private static Statement program7()
    {
        return concatenate(
                new VariableDeclaration(new IntType(), "i"),
                new AssignStatement("i", new ValueExpression(new IntValue(0))),
                new WhileStatement(
                        new RelationalExpression(new VariableExpression("i"), new ValueExpression(new IntValue(5)), "<"),
                        new CompoundStatement(
                                new PrintStatement(new VariableExpression("i")),
                                new AssignStatement("i", new ArithmeticExpression(
                                        new VariableExpression("i"),
                                        new ValueExpression(new IntValue(1)),
                                        "+"
                                ))
                        )
                )
        );
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
