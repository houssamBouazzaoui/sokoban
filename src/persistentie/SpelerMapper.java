package persistentie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domein.Speler;

public class SpelerMapper {
//ATTRIBUTEN
	private static final String INSERT_SPELER = "INSERT INTO ID222177_g22.Speler (gebruikersnaam, wachtwoord, naam, voornaam, admin)"
			+ "VALUES (?, ?, ?, ?, ?)";

//METHODES
	public void voegToe(Speler speler) {

		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn.prepareStatement(INSERT_SPELER)) {
			query.setString(1, speler.getGebruikersnaam());
			query.setString(2, speler.getWachtwoord());
			query.setString(3, speler.getNaam());
			query.setString(4, speler.getVoornaam());
			query.setBoolean(5, false);
			query.executeUpdate();

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public Speler geefSpeler(String gebruikersnaam) {
		Speler speler = null;

		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
				PreparedStatement query = conn
						.prepareStatement("SELECT * FROM ID222177_g22.Speler WHERE gebruikersnaam = ?")) {
			query.setString(1, gebruikersnaam);
			try (ResultSet rs = query.executeQuery()) {
				if (rs.next()) {
					int spelerId = rs.getInt("spelerID");
					String naam = rs.getString("naam");
					String voornaam = rs.getString("voornaam");
					String wachtwoord = rs.getString("wachtwoord");
					boolean admin = rs.getBoolean("admin");
					speler = new Speler(gebruikersnaam, wachtwoord, naam, voornaam, admin,spelerId);
				}
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

		return speler;
	}

}
