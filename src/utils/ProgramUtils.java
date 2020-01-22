package utils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import model.expressions.*;
import model.statements.*;
import model.types.IntType;
import model.types.ReferenceType;
import model.types.StringType;
import model.values.*;

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
        return List.<Statement>of(program1(), program2(), program3(), program4(), program5(), program6(), program7(), program8(), program9(), program10(), program11(), program12());
    }

    private static Statement program12()
    {
        return concatenate(
                new DefineProcedure("sum", List.of("a", "b"), concatenate(
                        new VariableDeclaration(new IntType(), "v"),
                        new AssignStatement("v", new ArithmeticExpression(new VariableExpression("a"), new VariableExpression("b"), "+")),
                        new PrintStatement(new VariableExpression("v"))
                )),
                new DefineProcedure("product", List.of("a", "b"), concatenate(
                        new VariableDeclaration(new IntType(), "v"),
                        new AssignStatement("v", new ArithmeticExpression(new VariableExpression("a"), new VariableExpression("b"), "*")),
                        new PrintStatement(new VariableExpression("v"))
                )),
                new VariableDeclaration(new IntType(), "v"),
                new VariableDeclaration(new IntType(), "w"),
                new AssignStatement("v", new ValueExpression(new IntValue(2))),
                new AssignStatement("w", new ValueExpression(new IntValue(5))),
                new CallProcedure("sum", List.of(new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(10)), "*"), new VariableExpression("w"))),
                new PrintStatement(new VariableExpression("v")),
                new ForkStatement(concatenate(
                        new CallProcedure("product", List.of(new VariableExpression("v"), new VariableExpression("w"))),
                        new ForkStatement(concatenate(
                                new CallProcedure("sum", List.of(new VariableExpression("v"), new VariableExpression("w")))
                        ))
                ))
        );
    }

    private static Statement program11()
    {
        return concatenate(
                new VariableDeclaration(new ReferenceType(new IntType()), "v1"),
                new VariableDeclaration(new ReferenceType(new IntType()), "v2"),
                new VariableDeclaration(new ReferenceType(new IntType()), "v3"),
                new VariableDeclaration(new IntType(), "cnt"),
                new AllocateHeapStatement("v1", new ValueExpression(new IntValue(2))),
                new AllocateHeapStatement("v2", new ValueExpression(new IntValue(3))),
                new AllocateHeapStatement("v3", new ValueExpression(new IntValue(4))),
                new NewBarrierStatement("cnt", new ReadHeapExpression(new VariableExpression("v2"))),
                new ForkStatement(concatenate(
                        new AwaitStatement("cnt"),
                        new WriteHeapStatement("v1", new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(10)), "*")),
                        new PrintStatement(new ReadHeapExpression(new VariableExpression("v1")))
                )),
                new ForkStatement(concatenate(
                        new AwaitStatement("cnt"),
                        new WriteHeapStatement("v2", new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v2")), new ValueExpression(new IntValue(10)), "*")),
                        new WriteHeapStatement("v2", new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v2")), new ValueExpression(new IntValue(10)), "*")),
                        new PrintStatement(new ReadHeapExpression(new VariableExpression("v2")))
                )),
                new AwaitStatement("cnt"),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("v3")))
        );
    }

    private static Statement program10()
    {
        return concatenate(
                new VariableDeclaration(new IntType(), "v"),
                new VariableDeclaration(new IntType(), "x"),
                new VariableDeclaration(new IntType(), "y"),
                new AssignStatement("v", new ValueExpression(new IntValue(0))),
                new RepeatStatement(concatenate(
                        new ForkStatement(concatenate(
                                new PrintStatement(new VariableExpression("v")),
                                new AssignStatement("v", new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(1)), "-"))
                        )),
                        new AssignStatement("v", new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(1)), "+"))
                ), new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(3)), "==")),
                new AssignStatement("x", new ValueExpression(new IntValue(1))),
                new NOPStatement(),
                new AssignStatement("y", new ValueExpression(new IntValue(3))),
                new NOPStatement(),
                new PrintStatement(new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(10)), "*"))
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
