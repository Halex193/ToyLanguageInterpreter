package controller;

import model.statements.Statement;
import model.statements.VariableDeclaration;
import model.types.IntType;
import org.junit.jupiter.api.*;
import repository.IRepository;
import repository.Repository;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest
{

    private Controller controller;
    private IRepository repository;

    @BeforeEach
    void setUp()
    {
        repository = new Repository(null, null);
        controller = new Controller(repository);
    }

    @AfterEach
    void tearDown()
    {
        repository = null;
        controller = null;
    }

    @Test
    void testCompoundStatement()
    {
        Statement program = new VariableDeclaration(new IntType(), "a");
    }

    @Test
    void oneStep()
    {
    }

    @Test
    void allStep()
    {
    }
}