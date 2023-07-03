package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";
	private static final String user = "student";
	private static final String pass = "student";

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println(e);
		}
	}

	public List<Film> findFilmByKeyword(String keyword) throws SQLException {
		Film filmMatch = null;
		List<Film> filmsMatchingKeyword = new ArrayList<>();

		String query = "SELECT * FROM film WHERE film.title LIKE ? OR film.description LIKE ?";
		Connection conn = DriverManager.getConnection(URL, user, pass);
		PreparedStatement prepStmt = conn.prepareStatement(query);
		prepStmt.setString(1, "%" + keyword + "%");
		prepStmt.setString(2, "%" + keyword + "%");
		ResultSet searchResult = prepStmt.executeQuery();
		
		if (searchResult.next() == false) {
			System.out.println("\n*** Could not find \"" + keyword + "\" in any movie title or description ***\n");
			return null;
		} else {
			do {
				filmMatch = new Film();

				filmMatch.setId(searchResult.getInt("id"));
				filmMatch.setTitle(searchResult.getString("title"));
				filmMatch.setDescription(searchResult.getString("description"));
				filmMatch.setReleaseYear(searchResult.getShort("release_year"));
				filmMatch.setLanguageId(searchResult.getInt("language_id"));
				filmMatch.setRentalDuration(searchResult.getInt("rental_duration"));
				filmMatch.setRentalRate(searchResult.getDouble("rental_rate"));
				filmMatch.setLength(searchResult.getInt("length"));
				filmMatch.setReplacementCost(searchResult.getDouble("replacement_cost"));
				filmMatch.setRating(searchResult.getString("rating"));
				filmMatch.setSpecialFeatures(searchResult.getString("special_features"));
				filmMatch.setLanguageName(findLanguageByFilmId(searchResult.getInt("id")));
				filmMatch.setCast(findActorsByFilmId(filmMatch.getId()));

				filmsMatchingKeyword.add(filmMatch);
			} while (searchResult.next());

			searchResult.close();
			prepStmt.close();
			conn.close();
			return filmsMatchingKeyword;
		}

	}

	@Override
	public Film findFilmById(int filmId) throws SQLException {
		Film film = null;
		String sql = "SELECT * FROM film WHERE id = ?";
		Connection conn = DriverManager.getConnection(URL, user, pass);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, filmId);
		ResultSet filmResult = prepStmt.executeQuery();

		if (filmResult.next()) {
			film = new Film();

			film.setId(filmResult.getInt("id"));
			film.setTitle(filmResult.getString("title"));
			film.setDescription(filmResult.getString("description"));
			film.setReleaseYear(filmResult.getShort("release_year"));
			film.setLanguageId(filmResult.getInt("language_id"));
			film.setRentalDuration(filmResult.getInt("rental_duration"));
			film.setRentalRate(filmResult.getDouble("rental_rate"));
			film.setLength(filmResult.getInt("length"));
			film.setReplacementCost(filmResult.getDouble("replacement_cost"));
			film.setRating(filmResult.getString("rating"));
			film.setSpecialFeatures(filmResult.getString("special_features"));
			film.setLanguageName(findLanguageByFilmId(filmResult.getInt("id")));
			film.setCast(findActorsByFilmId(filmId));
		}

		filmResult.close();
		prepStmt.close();
		conn.close();
		return film;
	}

	public Actor findActorById(int actorId) throws SQLException {
		Actor actor = null;
		String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
		Connection conn = DriverManager.getConnection(URL, user, pass);
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, actorId);
		ResultSet actorResult = stmt.executeQuery();
		if (actorResult.next()) {
			actor = new Actor(); 
			actor.setId(actorResult.getInt("id"));
			actor.setFirstName(actorResult.getString("first_name"));
			actor.setLastName(actorResult.getString("last_name"));
		}

		actorResult.close();
		stmt.close();
		conn.close();
		return actor;
	}

	public List<Film> findFilmsByActorId(int actorId) {
		List<Film> films = new ArrayList<>();

		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT id, title, description, release_year, language_id, rental_duration, ";
			sql += " rental_rate, length, replacement_cost, rating, special_features "
					+ " FROM film JOIN film_actor ON film.id = film_actor.film_id " + " WHERE actor_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("film.id");
				String title = rs.getString("film.title");
				String description = rs.getString("film.description");
				int releaseYear = rs.getShort("film.release_year");
				int languageId = rs.getInt("film.language_id");
				int rentalDuration = rs.getInt("film.rental_duration");
				double rentalRate = rs.getDouble("film.rental_rate");
				int length = rs.getInt("film.length");
				double replacementCost = rs.getDouble("film.replacement_cost");
				String rating = rs.getString("film.rating");
				String specialFeatures = rs.getString("film.special_features");
				Language language = findLanguageByFilmId(id);
				Film film = new Film(id, title, description, releaseYear, languageId, rentalDuration, rentalRate,
						length, replacementCost, rating, specialFeatures, language);
				films.add(film);
			}
			
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList<>();

		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT * FROM actor JOIN film_actor "
					+ "ON film_actor.actor_id = actor.id JOIN film ON film_actor.film_id = film.id WHERE film.id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String firstName = rs.getString("actor.first_name");
				String lastName = rs.getString("actor.last_name");
				int id = rs.getInt("actor.id");
				Actor actor = new Actor(id, firstName, lastName);
				actors.add(actor);
			}

			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return actors;
	}

	public Language findLanguageByFilmId(int filmId) throws SQLException {
		int id;
		String name;

		String langQuery = "SELECT * FROM language JOIN film ON language.id = film.language_id WHERE film.id = ?";
		Connection conn = DriverManager.getConnection(URL, user, pass);

		PreparedStatement langStmt = conn.prepareStatement(langQuery);
		langStmt.setInt(1, filmId);
		ResultSet langResult = langStmt.executeQuery();

		if (langResult.next() == false) {
			System.out.println("\n*** Could not find language for film ID " + filmId);
			return null;
		} else {
			do {
				id = langResult.getInt("language.id");
				name = langResult.getString("language.name");
			} while (langResult.next());

			langResult.close();
			langStmt.close();
			conn.close();
			return new Language(id, name);
		}

	}

}
