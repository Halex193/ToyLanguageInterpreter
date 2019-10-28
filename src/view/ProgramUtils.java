package view;

import java.util.List;

import model.expressions.ArithmeticExpression;
import model.expressions.Expression;
import model.expressions.ValueExpression;
import model.expressions.VariableExpression;
import model.statements.AssignStatement;
import model.statements.CompoundStatement;
import model.statements.PrintStatement;
import model.statements.Statement;
import model.statements.VariableDeclaration;
import model.types.IntType;
import model.values.IntValue;

public class ProgramUtils
{
    private ProgramUtils()
    {
    }

    public static List<Statement> generatePrograms()
    {
        return List.of(program1(), program2());
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
