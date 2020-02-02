package com.bookstore;

import com.bookstore.entity.Author;
import com.bookstore.entity.Book;
import com.bookstore.service.BookstoreService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MainApplication {

    private final BookstoreService bookstoreService;

    public MainApplication(BookstoreService bookstoreService) {
        this.bookstoreService = bookstoreService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {

            Author author = bookstoreService.fetchAuthorWithBooksByName();
            System.out.println("\nAuthor name: " + author.getName() + " Books: " + author.getBooks() + "\n");

            Book book = bookstoreService.fetchBookWithAuthorByIsbn();
            System.out.println("\nTitle: " + book.getTitle() + " author: " + book.getAuthor() + "\n");

        };
    }
}
/*
 * How To Avoid LazyInitializationException Via JOIN FETCH

See also:

LEFT JOIN FETCH
JOIN VS. JOIN FETCH
Description: Typically, when we get a LazyInitializationException we tend to modify the association fetching type from LAZY to EAGER. That is very bad! This is a code smell. Best way to avoid this exception is to rely on JOIN FETCH (if you plan to modify the fetched entities) or JOIN + DTO (if the fetched data is only read). JOIN FETCH allows associations to be initialized along with their parent objects using a single SELECT. This is particularly useful for fetching associated collections.

This application is a JOIN FETCH example for avoiding LazyInitializationException.

Key points:

define two related entities (e.g., Author and Book in a @OneToMany lazy-bidirectional association)
write a JPQL JOIN FETCH to fetch an author including his books
write a JPQL JOIN FETCH (or JOIN) to fetch a book including its author
 * 
 */
