package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
//		app.test();
		app.launch();
	}

	private void test() {
		try {
			Film film = db.findFilmById(1);
			System.out.println(film);

		} catch (SQLException e) {
			System.err.println(e);
		}
	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {
		int menuChoice;
		boolean keepItRunnin = true;

		while (keepItRunnin) {
			printMenu();

			try {
				menuChoice = input.nextInt();

			} catch (InputMismatchException e) {
				System.out.println("*** Please enter a valid menu option ***");
				menuChoice = 0;
				continue;
			} finally {
				input.nextLine();
			}

			switch (menuChoice) {
			case 1:
				findFilmMenu(input);
				break;
			case 2:
				keywordSearchMenu(input);
				break;
			case 3:
				System.out.println("Thank you for using Film Data Searcher 3000\n");
				keepItRunnin = false;
				break;
			default:
				System.out.println("*** Please enter a valid menu option ***");
				break;
			}
		}

	}

	private void printMenu() {
		System.out.println("Film Data Searcher 3000");
		System.out.println("-----------------------");
		System.out.println("[1] Look up film by ID");
		System.out.println("[2] Look up film by keyword");
		System.out.println("[3] Quit");
		System.out.println();
		System.out.print("Enter choice > ");
	}

	private void findFilmMenu(Scanner input) {
		int filmIdToLookUp = 0;
		boolean keepItRunnin = true;
		Film filmDataToDisplay = null;

		while (keepItRunnin) {
			System.out.println();
			System.out.print("Enter film ID > ");
			try {
				filmIdToLookUp = input.nextInt();

			} catch (InputMismatchException e) {
				System.out.println("*** Please enter a number ***");
				filmIdToLookUp = 0;
				continue;
			} finally {
				input.nextLine();
			}

			try {
				filmDataToDisplay = db.findFilmById(filmIdToLookUp);
				if (filmDataToDisplay == null) {
					System.out.println("\n*** Could not find film with ID " + filmIdToLookUp + " ***");
					System.out.println("Returning to main menu...\n");
					return;
				}

			} catch (SQLException e) {
				System.err.println(e);
				System.out.println("Returning to main menu...");
				keepItRunnin = false;
			}

			showFilmData(filmDataToDisplay);
			keepItRunnin = false;
		}
	}

	private void showFilmData(Film film) {
		System.out.println();
		System.out.println("Title:\t\t" + film.getTitle());
		System.out.println("Release Year:\t" + film.getReleaseYear());
		System.out.println("Rating:\t\t" + film.getRating());
		System.out.println("Description:\t" + film.getDescription());
		System.out.println("Language:\t" + film.getLanguageName());
		System.out.print("Cast:\t\t");
		int castCounter = 0;
		for (Actor castMember : film.getCast()) {
			castCounter++;
			if (castCounter == film.getCast().size()) {
				System.out.print("and " + castMember);
				break;
			}
			System.out.print(castMember + ", ");
		}
		System.out.println("\n");
	}

	private void keywordSearchMenu(Scanner input) {
		List<Film> filmsFound = null;

		System.out.print("\nEnter keyword > ");
		String keyword = input.nextLine();

		try {
			filmsFound = db.findFilmByKeyword(keyword);
		} catch (SQLException e) {
			System.err.println(e);
			System.out.println("Returning to main menu...");
		}

		if (filmsFound == null) {
			return;
		}

		System.out.println(filmsFound.size() + (filmsFound.size() > 1 ? " results" : " result") + " found for \""
				+ keyword + "\"!");
		for (Film film : filmsFound) {
			showFilmData(film);
		}

		System.out.println();

	}

}
