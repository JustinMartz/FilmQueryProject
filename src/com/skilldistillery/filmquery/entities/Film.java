package com.skilldistillery.filmquery.entities;

import java.util.List;
import java.util.Objects;

public class Film {
	private int filmId;
	private String title;
	private String desc;
	private short releaseYear;
	private int langId;
	private int rentDur;
	private double rate;
	private int length;
	private double repCost;
	private String rating;
	private String features;
	private List<Actor> cast;
	
	public Film() {
		
	}
	
	public Film(int filmId2, String title2, String desc2, short releaseYear2, int langId2, int rentDur2, double rate2,
			int length2, double repCost2, String rating2, String features2) {
		// TODO Auto-generated constructor stub
		filmId = filmId2;
		title = title2;
		desc = desc2;
		releaseYear = releaseYear2;
		langId = langId2;
		rentDur = rentDur2;
		rate = rate2;
		length = length2;
		repCost = repCost2;
		rating = rating2;
		features = features2;
	}
	
	public int getFilmId() {
		return filmId;
	}
	public void setFilmId(int filmId) {
		this.filmId = filmId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public short getReleaseYear() {
		return releaseYear;
	}
	public void setReleaseYear(short year) {
		this.releaseYear = year;
	}
	public int getLangId() {
		return langId;
	}
	public void setLangId(int langId) {
		this.langId = langId;
	}
	public int getRentDur() {
		return rentDur;
	}
	public void setRentDur(int rentDur) {
		this.rentDur = rentDur;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public double getRepCost() {
		return repCost;
	}
	public void setRepCost(double repCost) {
		this.repCost = repCost;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getFeatures() {
		return features;
	}
	public void setFeatures(String features) {
		this.features = features;
	}

	public List<Actor> getCast() {
		return cast;
	}

	public void setCast(List<Actor> cast) {
		this.cast = cast;
	}

	@Override
	public String toString() {
		return "Film [filmId=" + filmId + ", title=" + title + ", desc=" + desc + ", releaseYear=" + releaseYear
				+ ", langId=" + langId + ", rentDur=" + rentDur + ", rate=" + rate + ", length=" + length + ", repCost="
				+ repCost + ", rating=" + rating + ", features=" + features + ", cast=" + cast + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(desc, features, filmId, langId, length, rate, rating, releaseYear, rentDur, repCost, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Film other = (Film) obj;
		return Objects.equals(desc, other.desc) && Objects.equals(features, other.features) && filmId == other.filmId
				&& langId == other.langId && length == other.length
				&& Double.doubleToLongBits(rate) == Double.doubleToLongBits(other.rate)
				&& Objects.equals(rating, other.rating) && releaseYear == other.releaseYear && rentDur == other.rentDur
				&& Double.doubleToLongBits(repCost) == Double.doubleToLongBits(other.repCost)
				&& Objects.equals(title, other.title);
	}
	
}
