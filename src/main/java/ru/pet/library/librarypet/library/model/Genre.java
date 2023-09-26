package ru.pet.library.librarypet.library.model;
public enum Genre {

    FANTASY("Фантастика"),

    DRAMA("Драма"),
    SINCE_FICTION("Научная фантастика"),
    NOVEL("Роман");

    private final String genreTextDisplay;

    Genre(String text) {
        this.genreTextDisplay = text;
    }

    public String getGenreTextDisplay() {
        return this.genreTextDisplay;
    }
}
