package view;

import java.util.List;

import model.expressions.ArithmeticExpression;
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

        Statement program1 = new CompoundStatement(
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

        return List.of(program1);
    }
}
