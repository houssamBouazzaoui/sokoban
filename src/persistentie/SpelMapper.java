package persistentie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domein.Spel;
import domein.Spelbord;
import domein.Veld;

public class SpelMapper {

//OPHALEN
	public List<String> geefSpelNamen() {
		List<String> spelNamenLijst = new ArrayList<>();

		try (Connection connection = DriverManager.getConnection(Connectie.JDBC_URL)) {

			PreparedStatement query = connection.prepareStatement("SELECT naam FROM ID222177_g22.Spel");
			ResultSet rs = query.executeQuery();

			while (rs.next())
				spelNamenLijst.add(rs.getString("naam"));

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return spelNamenLijst;
	}

	public List<String> geefSpelNamenVanSpeler(String gebruikersnaam) {
		List<String> spelNamenLijst = new ArrayList<>();

		try (Connection connection = DriverManager.getConnection(Connectie.JDBC_URL)) {

			PreparedStatement query = connection.prepareStatement("SELECT Spel.naam FROM ID222177_g22.Spel "
					+ "INNER JOIN ID222177_g22.Speler ON Spel.makerID = Speler.spelerID WHERE gebruikersnaam =?");
			query.setObject(1, gebruikersnaam);
			ResultSet rs = query.executeQuery();
			while (rs.next())
				spelNamenLijst.add(rs.getString("naam"));

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return spelNamenLijst;
	}

	public List<Spelbord> geefSpelborden(String spelNaam) {
		List<Spelbord> spelborden = new ArrayList<>();

		try (Connection connection = DriverManager.getConnection(Connectie.JDBC_URL)) {
			PreparedStatement query = connection
					.prepareStatement("SELECT nummer FROM ID222177_g22.Spelbord WHERE spelNaam = ?");
			query.setObject(1, spelNaam);
			ResultSet rs = query.executeQuery();

			while (rs.next()) {
				spelborden.add(new Spelbord(rs.getInt("nummer")));
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return spelborden;
	}

	public Veld[][] geefVelden(String spelNaam, int spelbordNr) {
		Veld[][] velden = new Veld[10][10];
		for (int y = 0; y < velden.length; y++)
			for (int x = 0; x < velden[y].length; x++)
				velden[y][x] = new Veld();

		try (Connection connection = DriverManager.getConnection(Connectie.JDBC_URL)) {
			PreparedStatement query3 = connection.prepareStatement(
					"SELECT x, y, type,verplaatsbaarItem FROM ID222177_g22.Veld WHERE spelNaam = ? AND spelbordNr = ?");
			query3.setObject(1, spelNaam);
			query3.setObject(2, spelbordNr);
			ResultSet rs3 = query3.executeQuery();

			while (rs3.next()) {
				int x = rs3.getInt("x");
				int y = rs3.getInt("y");
				char type = rs3.getString("type").charAt(0);
				char verplaatsbaarItem = rs3.getString("verplaatsbaarItem").charAt(0);

				velden[y][x] = new Veld(type, verplaatsbaarItem);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return velden;
	}

//REGISTEREN
	public void registreerNieuwSpel(Spel spel, int spelerId) {
		try (Connection connection = DriverManager.getConnection(Connectie.JDBC_URL)) {
			PreparedStatement query = connection
					.prepareStatement("INSERT INTO ID222177_g22.Spel (naam,makerID) VALUES (?,?) ");
			query.setObject(1, spel.getNaam());
			query.setInt(2, spelerId);
			query.executeUpdate();

			registreerSpelborden(spel.getNaam(), spel.getSpelborden());

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public void registreerSpelborden(String spelNaam, List<Spelbord> spelborden) {
		try (Connection connection = DriverManager.getConnection(Connectie.JDBC_URL)) {
			PreparedStatement query = connection
					.prepareStatement("INSERT INTO ID222177_g22.Spelbord (spelNaam,nummer) VALUES (?,?) ");

			int spelbordNummer = 1;
			for (Spelbord spelbord : spelborden) {
				query.setObject(1, spelNaam);
				query.setObject(2, spelbordNummer);
				query.executeUpdate();

				registreerVelden(spelNaam, spelbordNummer, spelbord.getVelden());
				spelbordNummer++;
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	private void registreerVelden(String spelNaam, int spelbordNummer, Veld[][] velden) {
		int tellerX = 0;
		int tellerY = 0;

		try (Connection connection = DriverManager.getConnection(Connectie.JDBC_URL)) {
			PreparedStatement query = connection.prepareStatement(
					"INSERT INTO ID222177_g22.Veld (spelNaam, spelbordNr, x, y, type, verplaatsbaarItem) VALUES (?,?,?,?,?,?) ");
			for (Veld[] rij : velden) {
				for (Veld veld : rij) {
					if (veld.getType() != '.' || veld.getVerplaatsbaarItem() != '.') {
						query.setObject(1, spelNaam);
						query.setObject(2, spelbordNummer);
						query.setObject(3, tellerX);
						query.setObject(4, tellerY);
						query.setString(5, String.valueOf(veld.getType()));
						query.setString(6, String.valueOf(veld.getVerplaatsbaarItem()));
						query.executeUpdate();
					}

					tellerX++;
				}
				tellerY++;
				tellerX = 0;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void registreerGewijzigdSpel(Spel spel) {
		try (Connection connection = DriverManager.getConnection(Connectie.JDBC_URL)) {

			PreparedStatement query = connection
					.prepareStatement("DELETE FROM ID222177_g22.Veld WHERE spelNaam = ? AND spelbordNr = ?");
			query.setObject(1, spel.getNaam());

			List<Spelbord> spelborden = spel.getSpelborden();
			for (Spelbord spelbord : spelborden) {

				if (spelbord.getVelden() != null) {
					query.setObject(2, spelbord.getNummer());
					query.executeUpdate();

					registreerVelden(spel.getNaam(), spelbord.getNummer(), spelbord.getVelden());
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void verwijderSpelbord(Spel spel, Spelbord spelbord) {
		try (Connection connection = DriverManager.getConnection(Connectie.JDBC_URL)) {
			PreparedStatement query = connection
					.prepareStatement("DELETE FROM ID222177_g22.Veld WHERE spelNaam = ? AND spelbordNr = ?");
			query.setObject(1, spel.getNaam());
			query.setObject(2, spelbord.getNummer());
			query.executeUpdate();

			PreparedStatement query2 = connection
					.prepareStatement("DELETE FROM ID222177_g22.Spelbord WHERE spelNaam = ? AND nummer = ?");
			query2.setObject(1, spel.getNaam());
			query2.setObject(2, spelbord.getNummer());
			query2.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
