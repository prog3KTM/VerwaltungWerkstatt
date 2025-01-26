package htw.prog3.KTM.repository;

import htw.prog3.KTM.model.Auto.Auto;
import htw.prog3.KTM.database.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutoRepository {

    // Retrieve all cars
    public List<Auto> findAll() {
        List<Auto> autos = new ArrayList<>();
        String sql = "SELECT * FROM Auto";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Auto auto = new Auto(
                        rs.getInt("id"),
                        rs.getString("model"),
                        rs.getString("brand"),
                        rs.getString("licensePlate")
                );
                autos.add(auto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return autos;
    }

    // Save a new car
    public void save(Auto auto) {
        String sql = "INSERT INTO Auto (id, model, brand, licensePlate) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, auto.getId());
            pstmt.setString(2, auto.getModel());
            pstmt.setString(3, auto.getBrand());
            pstmt.setString(4, auto.getLicensePlate());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Find a car by ID
    public Auto findById(int id) {
        String sql = "SELECT * FROM Auto WHERE id = ?";
        Auto auto = null;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    auto = new Auto(
                            rs.getInt("id"),
                            rs.getString("model"),
                            rs.getString("brand"),
                            rs.getString("licensePlate")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return auto;
    }

    // Delete a car by ID
    public void deleteById(int id) {
        String sql = "DELETE FROM Auto WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}