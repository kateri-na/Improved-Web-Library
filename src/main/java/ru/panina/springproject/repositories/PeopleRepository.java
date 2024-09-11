package ru.panina.springproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.panina.springproject.models.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    public Person findByName(String name);
}
