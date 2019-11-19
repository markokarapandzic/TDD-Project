package com.examples.isbntools;

public class StockManager {

    private ExternalISBNService webService;
    private ExternalISBNService databaseService;

    public void setDatabaseService(ExternalISBNService databaseService) {
        this.databaseService = databaseService;
    }

    public void setWebService(ExternalISBNService webService) {
        this.webService = webService;
    }

    public String getLocatorCode(String isbn) {
        Book book = databaseService.lookup(isbn);
        if (book == null) {
            book = webService.lookup(isbn);
        }

        StringBuilder locator = new StringBuilder();
        locator.append(isbn.substring(isbn.length() - 4));
        locator.append(book.getAuthor().substring(0, 1));
        locator.append(book.getTitle().split(" ").length);
        return locator.toString();
    }
}
