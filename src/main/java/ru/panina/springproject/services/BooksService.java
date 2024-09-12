package ru.panina.springproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.panina.springproject.models.Book;
import ru.panina.springproject.models.Person;
import ru.panina.springproject.repositories.BooksRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(){
        return booksRepository.findAll();
    }

    public List<Book> findAll(String sort_by_year){
        if(sort_by_year.equals("true"))
            return booksRepository.findAll(Sort.by("year"));
        else
            return findAll();
    }

    public List<Book> findAll(int page, int books_per_page){
        return booksRepository.findAll(PageRequest.of(page, books_per_page)).getContent();
    }

    public List<Book> findAll(int page, int books_per_page, String sort_by_year){
        if(sort_by_year.equals("true"))
            return booksRepository.findAll(PageRequest.of(page, books_per_page, Sort.by("year"))).getContent();
        else
            return findAll(page, books_per_page);
    }

    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }

    public Book findById(int id){
        return booksRepository.findById(id).orElse(null);
    }

    public Person findOwner(int id){
        Book book = booksRepository.findById(id).orElse(null);
        if(book != null){
            return book.getOwner();
        }
        else return null;
    }

    @Transactional
    public void update(int id, Book book){
        book.setId(id);
        booksRepository.save(book);
    }

    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }

    @Transactional
    public void appoint(Person person, int id){
        Book appointedBook = booksRepository.findById(id).orElse(null);
        if(appointedBook != null) {
            appointedBook.setOwner(person);
            booksRepository.save(appointedBook);
        }
    }

    @Transactional
    public void free(int id){
        Book freeBook = booksRepository.findById(id).orElse(null);
        if(freeBook != null) {
            freeBook.setOwner(null);
            booksRepository.save(freeBook);
        }
    }

    public List<Book> findAllByTitle(String title){
        return booksRepository.findByTitleStartingWith(title);
    }
}
