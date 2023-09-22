package ru.pet.library.librarypet.library.model;
public enum Genre {

    FANTASY("Фантастика"),

    DRAMA("Драма"),
    SINCE_FICTION("Научная фантастика"),
    NOVEL("Роман");

    private final String genreDisplayValue;
    Genre(String text) {
        this.genreDisplayValue = text;
    }

    public String getGenreDisplayValue(){
        return this.genreDisplayValue;
    }
}
