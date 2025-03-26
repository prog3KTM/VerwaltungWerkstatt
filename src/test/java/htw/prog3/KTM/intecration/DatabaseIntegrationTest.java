package htw.prog3.KTM.intecration;

import htw.prog3.KTM.database.DatabaseManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseIntegrationTest {

    private static final String TEST_DB_PATH = "test_integration.db";
    private DatabaseManager databaseManager;
    private Connection connection;

    @BeforeEach
    void setUp() {
        databaseManager = new DatabaseManager(TEST_DB_PATH);
        connection = databaseManager.getConnection();
        
        // Create a test table
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS test_table (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "value INTEGER)"
            );
            statement.executeUpdate();
        } catch (SQLException e) {
            fail("Failed to create test table: " + e.getMessage());
        }
    }
    
    @AfterEach
    void tearDown() {
        try {
            // Drop the test table
            PreparedStatement statement = connection.prepareStatement("DROP TABLE IF EXISTS test_table");
            statement.executeUpdate();
            
            // Close the connection
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error during cleanup: " + e.getMessage());
        }
    }

    @Test
    void getConnection_ReturnsValidConnection() {
        // Assert
        assertNotNull(connection);
        try {
            assertFalse(connection.isClosed());
        } catch (SQLException e) {
            fail("Connection check failed: " + e.getMessage());
        }
    }

    @Test
    void insertAndRetrieveData_WorksCorrectly() {
        try {
            // Insert test data
            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO test_table (name, value) VALUES (?, ?)"
            );
            insertStatement.setString(1, "Test Item");
            insertStatement.setInt(2, 42);
            int insertResult = insertStatement.executeUpdate();
            
            // Assert insert worked
            assertEquals(1, insertResult);
            
            // Retrieve the data
            PreparedStatement selectStatement = connection.prepareStatement(
                    "SELECT name, value FROM test_table WHERE name = ?"
            );
            selectStatement.setString(1, "Test Item");
            ResultSet resultSet = selectStatement.executeQuery();
            
            // Assert data retrieval works
            assertTrue(resultSet.next());
            assertEquals("Test Item", resultSet.getString("name"));
            assertEquals(42, resultSet.getInt("value"));
            assertFalse(resultSet.next());  // No more rows
            
            resultSet.close();
        } catch (SQLException e) {
            fail("Database operation failed: " + e.getMessage());
        }
    }
    
    @Test
    void updateData_WorksCorrectly() {
        try {
            // Insert test data
            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO test_table (name, value) VALUES (?, ?)"
            );
            insertStatement.setString(1, "Update Test");
            insertStatement.setInt(2, 100);
            insertStatement.executeUpdate();
            
            // Update the data
            PreparedStatement updateStatement = connection.prepareStatement(
                    "UPDATE test_table SET value = ? WHERE name = ?"
            );
            updateStatement.setInt(1, 200);
            updateStatement.setString(2, "Update Test");
            int updateResult = updateStatement.executeUpdate();
            
            // Assert update worked
            assertEquals(1, updateResult);
            
            // Verify the update
            PreparedStatement selectStatement = connection.prepareStatement(
                    "SELECT value FROM test_table WHERE name = ?"
            );
            selectStatement.setString(1, "Update Test");
            ResultSet resultSet = selectStatement.executeQuery();
            
            assertTrue(resultSet.next());
            assertEquals(200, resultSet.getInt("value"));
            
            resultSet.close();
        } catch (SQLException e) {
            fail("Database update operation failed: " + e.getMessage());
        }
    }
    
    @Test
    void deleteData_WorksCorrectly() {
        try {
            // Insert test data
            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO test_table (name, value) VALUES (?, ?)"
            );
            insertStatement.setString(1, "Delete Test");
            insertStatement.setInt(2, 999);
            insertStatement.executeUpdate();
            
            // Verify insertion
            PreparedStatement checkStatement = connection.prepareStatement(
                    "SELECT * FROM test_table WHERE name = ?"
            );
            checkStatement.setString(1, "Delete Test");
            ResultSet initialCheck = checkStatement.executeQuery();
            assertTrue(initialCheck.next());
            initialCheck.close();
            
            // Delete the data
            PreparedStatement deleteStatement = connection.prepareStatement(
                    "DELETE FROM test_table WHERE name = ?"
            );
            deleteStatement.setString(1, "Delete Test");
            int deleteResult = deleteStatement.executeUpdate();
            
            // Assert delete worked
            assertEquals(1, deleteResult);
            
            // Verify deletion
            ResultSet afterDelete = checkStatement.executeQuery();
            assertFalse(afterDelete.next());
            
            afterDelete.close();
        } catch (SQLException e) {
            fail("Database delete operation failed: " + e.getMessage());
        }
    }
    
    @Test
    void transactionHandling_CommitsSuccessfully() {
        try {
            // Start transaction
            connection.setAutoCommit(false);
            
            // Execute multiple statements in transaction
            PreparedStatement insert1 = connection.prepareStatement(
                    "INSERT INTO test_table (name, value) VALUES (?, ?)"
            );
            insert1.setString(1, "Transaction Item 1");
            insert1.setInt(2, 111);
            insert1.executeUpdate();
            
            PreparedStatement insert2 = connection.prepareStatement(
                    "INSERT INTO test_table (name, value) VALUES (?, ?)"
            );
            insert2.setString(1, "Transaction Item 2");
            insert2.setInt(2, 222);
            insert2.executeUpdate();
            
            // Commit transaction
            connection.commit();
            
            // Reset auto commit
            connection.setAutoCommit(true);
            
            // Verify both inserts are visible
            PreparedStatement countStatement = connection.prepareStatement(
                    "SELECT COUNT(*) as count FROM test_table WHERE name LIKE 'Transaction Item%'"
            );
            ResultSet countResult = countStatement.executeQuery();
            assertTrue(countResult.next());
            assertEquals(2, countResult.getInt("count"));
            
            countResult.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException rollbackEx) {
                System.err.println("Rollback failed: " + rollbackEx.getMessage());
            }
            fail("Transaction test failed: " + e.getMessage());
        }
    }
    
    @Test
    void transactionHandling_RollbacksCorrectly() {
        try {
            // Start transaction
            connection.setAutoCommit(false);
            
            // Insert first item
            PreparedStatement insert = connection.prepareStatement(
                    "INSERT INTO test_table (name, value) VALUES (?, ?)"
            );
            insert.setString(1, "Rollback Test");
            insert.setInt(2, 333);
            insert.executeUpdate();
            
            // Rollback the transaction
            connection.rollback();
            
            // Reset auto commit
            connection.setAutoCommit(true);
            
            // Verify the insert was rolled back
            PreparedStatement selectStatement = connection.prepareStatement(
                    "SELECT * FROM test_table WHERE name = 'Rollback Test'"
            );
            ResultSet resultSet = selectStatement.executeQuery();
            assertFalse(resultSet.next());
            
            resultSet.close();
        } catch (SQLException e) {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                System.err.println("Failed to reset auto commit: " + ex.getMessage());
            }
            fail("Rollback test failed: " + e.getMessage());
        }
    }
} 