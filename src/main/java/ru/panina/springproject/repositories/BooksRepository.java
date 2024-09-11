package ru.panina.springproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.panina.springproject.models.Book;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
}
