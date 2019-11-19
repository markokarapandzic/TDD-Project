package com.examples.isbntools;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class StockManagementTest {

    @Test
    public void testCanGetValidLocatorCode() {

        ExternalISBNService testWebService = new ExternalISBNService() {
            @Override
            public Book lookup(String isbn) {
                return new Book(isbn, "Of Mice And Men", "J. Steinbeck");
            }
        };

        ExternalISBNService testDatabaseService = new ExternalISBNService() {
            @Override
            public Book lookup(String isbn) {
                return null;
            }
        };

        StockManager stockManager = new StockManager();
        stockManager.setWebService(testWebService);
        stockManager.setDatabaseService(testDatabaseService);

        String isbn = "0140177396";
        String locatorCode = stockManager.getLocatorCode(isbn);
        assertEquals("7396J4", locatorCode);
    }

    @Test
    public void databaseIsUsedIfDataIsPresent() {
        ExternalISBNService webService = mock(ExternalISBNService.class);
        ExternalISBNService databaseService = mock(ExternalISBNService.class);

        String isbn = "0140177396";
        when(databaseService.lookup(isbn)).thenReturn(new Book("0140177396", "abc", "abc"));

        StockManager stockManager = new StockManager();
        stockManager.setWebService(webService);
        stockManager.setDatabaseService(databaseService);

        stockManager.getLocatorCode(isbn);

        verify(databaseService).lookup(isbn);
        verify(webService, never()).lookup(anyString());
    }

    @Test
    public void webServiceIsUsedIfDataIsUsedInDatabase() {
        ExternalISBNService webService = mock(ExternalISBNService.class);
        ExternalISBNService databaseService = mock(ExternalISBNService.class);

        String isbn = "0140177396";
        when(databaseService.lookup(isbn)).thenReturn(null);
        when(webService.lookup(isbn)).thenReturn(new Book(isbn, "abc", "abc"));

        StockManager stockManager = new StockManager();
        stockManager.setWebService(webService);
        stockManager.setDatabaseService(databaseService);

        stockManager.getLocatorCode(isbn);

        verify(databaseService).lookup(isbn);
        verify(webService).lookup(isbn);
    }
}
