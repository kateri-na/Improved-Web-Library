package ru.panina.springproject.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.panina.springproject.models.Person;
import ru.panina.springproject.repositories.PeopleRepository;
import ru.panina.springproject.models.Book;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    @Transactional
    public void save(Person person){
        peopleRepository.save(person);
    }

    public Person findById(int id){
        return peopleRepository.findById(id).orElse(null);
    }

    public Person findByName(String name){ return peopleRepository.findByName(name);}

    @Transactional
    public void update(int id, Person person){
        person.setId(id);
        peopleRepository.save(person);
    }

    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }

    public List<Book> findAllBooks(int id){
        Person person = peopleRepository.findById(id).orElse(null);
        if(person != null){
            Hibernate.initialize(person.getBooks());
            return person.getBooks();
        }
        else return new ArrayList<>();
    }
}
